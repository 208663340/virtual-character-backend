package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.dao.entity.AgentMessage;
import com.nageoffer.shortlink.admin.dao.entity.AgentPropertiesDO;
import com.nageoffer.shortlink.admin.dto.req.user.UserMessageReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.agent.AgentMessageHistoryRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.agent.AgentMessageRespDTO;

import com.nageoffer.shortlink.admin.service.AgentConversationService;
import com.nageoffer.shortlink.admin.service.AgentMessageService;
import com.nageoffer.shortlink.admin.dao.mapper.AgentMessageMapper;

import com.nageoffer.shortlink.admin.service.tool.RedisSessionCacheService;
import com.nageoffer.shortlink.admin.toolkit.ai.AIContentAccumulator;
import com.nageoffer.shortlink.admin.toolkit.ai.AgentPropertiesLoader;
import com.nageoffer.shortlink.admin.toolkit.ai.XingChenAIClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.nageoffer.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
* @author 20866
* @description 针对表【agent_message_0】的数据库操作Service实现
* @createDate 2025-05-27 13:33:37
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class AgentMessageServiceImpl extends ServiceImpl<AgentMessageMapper, AgentMessage>
    implements AgentMessageService{

    private final StringRedisTemplate stringRedisTemplate;
    private final XingChenAIClient xingChenAIClient;
    private final AgentPropertiesLoader agentPropertiesLoader;
    private final AgentConversationService agentConversationService;
    private final RedisSessionCacheService redisSessionCacheService;
    private final SimpleAsyncTaskExecutor asyncTask = new SimpleAsyncTaskExecutor();



    /**
     * 通过用户名获取用户ID的通用方法
     */
    @Override
    public Long getUserIdByUsername(String username) {
        // 根据用户名获取登录信息
        Map<Object, Object> hasLoginMap = stringRedisTemplate.opsForHash().entries(USER_LOGIN_KEY + username);
        if (CollUtil.isEmpty(hasLoginMap)) {
            throw new ClientException("用户未登录或登录已过期");
        }
        
        // 续期登录状态
        stringRedisTemplate.expire(USER_LOGIN_KEY + username, 30L, TimeUnit.MINUTES);
        
        // 获取token对应的用户信息JSON字符串
        String token = hasLoginMap.keySet().stream()
                .findFirst()
                .map(Object::toString)
                .orElseThrow(() -> new ClientException("用户登录错误"));
        
        String userInfoJson = (String) hasLoginMap.get(token);
        if (userInfoJson == null) {
            throw new ClientException("用户信息不存在");
        }
        
        // 解析用户信息，获取userId
        com.alibaba.fastjson2.JSONObject userInfo = com.alibaba.fastjson2.JSON.parseObject(userInfoJson);
        return userInfo.getLong("id");
    }

    @Override
    public void saveMessage(AgentMessageRespDTO agentMessageResp) {
        AgentMessage agentMessage = new AgentMessage();
        BeanUtils.copyProperties(agentMessageResp, agentMessage);

        baseMapper.insert(agentMessage);
    }

    @Override
    public List<AgentMessageHistoryRespDTO> getConversationHistory(String sessionId) {
        // 从Redis缓存中获取会话历史记录
        return redisSessionCacheService.getMessagesFromCache(sessionId);
    }

    @Override
    public IPage<AgentMessageHistoryRespDTO> pageHistoryMessages(String username, String sessionId, Integer current, Integer size) {
        // 通过用户名获取用户ID
        Long userId = getUserIdByUsername(username);
        
        Page<AgentMessage> page = new Page<>(current, size);
        
        LambdaQueryWrapper<AgentMessage> queryWrapper = new LambdaQueryWrapper<AgentMessage>()
                .eq(AgentMessage::getDelFlag, 0)
                .orderByAsc(AgentMessage::getCreateTime);
        
        if (StrUtil.isNotBlank(sessionId)) {
            queryWrapper.eq(AgentMessage::getSessionId, sessionId);
        }
        
        IPage<AgentMessage> messagePage = baseMapper.selectPage(page, queryWrapper);
        
        return messagePage.convert(message -> {
            AgentMessageHistoryRespDTO respDTO = new AgentMessageHistoryRespDTO();
            BeanUtils.copyProperties(message, respDTO);
            return respDTO;
        });
    }

    /**
     * 保存用户消息
     */
    private void saveUserMessage(String sessionId, Long userId, Long agentId, String userMessage) {
        AgentMessage message = new AgentMessage();
        message.setSessionId(sessionId);
        message.setMessageType(1); // 用户消息
        message.setMessageContent(userMessage);
        message.setMessageSeq(getNextMessageSeq(sessionId));
        message.setCreateTime(new Date());
        message.setDelFlag(0);
        
        baseMapper.insert(message);
    }

    /**
     * 保存AI回复消息
     */
    private void saveAiMessage(String sessionId, Long userId, Long agentId, String aiMessage, Integer responseTime) {
        AgentMessage message = new AgentMessage();
        message.setSessionId(sessionId);
        message.setMessageType(2); // AI回复
        message.setMessageContent(aiMessage);
        message.setMessageSeq(getNextMessageSeq(sessionId));
        message.setResponseTime(responseTime);
        message.setCreateTime(new Date());
        message.setDelFlag(0);
        
        baseMapper.insert(message);
    }

    /**
     * 获取下一个消息序号
     */
    private Integer getNextMessageSeq(String sessionId) {
        return redisSessionCacheService.getNextMessageSeq(sessionId);
    }

    @Override
    public SseEmitter agentChatSse(UserMessageReqDTO requestParam) {
        // 从请求参数中获取 sessionId，如果不存在则为 null
        String existingSessionId = requestParam.getSessionId();
        SseEmitter emitter = new SseEmitter(18000L);
        String userName = requestParam.getUserName() == null ? "默认" : requestParam.getUserName();
        Long agentId = requestParam.getAgentId() == null ? 1345345L : requestParam.getAgentId();
        String userMessage = requestParam.getInputMessage() == null ? "没有输入" : requestParam.getInputMessage();
        
        // 新增内容累积器
        AIContentAccumulator accumulator = new AIContentAccumulator();
        
        asyncTask.submit(() -> {
            String sessionId = null;
            long startTime = System.currentTimeMillis();
            
            try {
                // 1. 处理会话ID和历史消息
                String currentSessionId;
                String historyJson = null;
                int nextMessageSeq;

                if (StrUtil.isNotBlank(existingSessionId)) {
                    currentSessionId = existingSessionId;
                    List<AgentMessageHistoryRespDTO> historyMessages = getConversationHistory(currentSessionId);
                    // 将 historyMessages 转换为 XingChenAIClient 需要的 JSON 格式
                    // 注意：这里需要一个将 List<AgentMessageHistoryRespDTO> 转换为特定JSON字符串的方法
                    // 例如：historyJson = convertHistoryToJson(historyMessages);
                    // 暂时用一个占位符，您需要实现具体的转换逻辑
                    if (CollUtil.isNotEmpty(historyMessages)) {
                        historyJson = JSON.toJSONString(
                            historyMessages.stream().map(h -> {
                                java.util.HashMap<String, String> map = new java.util.HashMap<>();
                                map.put("role", h.getMessageType() == 1 ? "user" : "assistant");
                                map.put("content_type", "text");
                                map.put("content", h.getMessageContent());
                                return map;
                            }).collect(Collectors.toList())
                        );
                    }
                    nextMessageSeq = getNextMessageSeq(currentSessionId);
                } else {
                    currentSessionId = agentConversationService.createConversation(userName, agentId, userMessage);
                    nextMessageSeq = 1;
                }
                sessionId = currentSessionId; // 确保后续错误处理能获取到sessionId

                // 2. 保存用户消息到Redis缓存
                AgentMessage userMsg = new AgentMessage();
                userMsg.setSessionId(currentSessionId);
                userMsg.setMessageType(1); // 用户消息
                userMsg.setMessageContent(userMessage);
                userMsg.setMessageSeq(nextMessageSeq);
                userMsg.setCreateTime(new Date());
                userMsg.setDelFlag(0);
                // 添加到Redis缓存，会自动异步同步到数据库
                redisSessionCacheService.addMessageToCache(currentSessionId, userMsg);
                
                // 3. 根据智能体ID获取对应的配置
                AgentPropertiesDO agentProperties = agentPropertiesLoader.getAgentPropertiesMap().get(agentId);
                
                if (agentProperties == null) {
                    throw new ClientException("智能体配置不存在");
                }
                
                // 4. 调用AI接口进行流式传输
                xingChenAIClient.chat(
                        userMessage,
                        historyJson,    // 传递 history
                        true,
                        new OutputStream() {
                            @Override
                            public void write(int b) { /* 不需要实现 */ }

                            @Override
                            public void write(byte[] b, int off, int len) throws IOException {
                                // 发送数据到前端
                                String jsonChunk = new String(b, off, len);
                                emitter.send(SseEmitter.event().data(jsonChunk));

                                // 累积内容到字符串
                                accumulator.appendChunk(b);
                            }

                            @Override
                            public void flush() { /* 确保数据发送 */ }
                        },data -> {
                        },
                        agentProperties.getApiKey(),
                        agentProperties.getApiSecret(),
                        agentProperties.getApiFlowId()
                );

                // 5. 流式传输完成后，保存AI回复消息到Redis缓存
                String fullContent = accumulator.getFullContent();
                long responseTime = System.currentTimeMillis() - startTime;
                
                AgentMessage aiMsg = new AgentMessage();
                aiMsg.setSessionId(sessionId); // sessionId 已经在前面被赋值为 currentSessionId
                aiMsg.setMessageType(2); // AI回复
                aiMsg.setMessageContent(fullContent);
                // 如果是新会话的第一次AI回复，序号应为2，否则通过 getNextMessageSeq 获取
                int aiMessageSeq = (nextMessageSeq == 1) ? 2 : getNextMessageSeq(sessionId);
                aiMsg.setMessageSeq(aiMessageSeq);
                aiMsg.setResponseTime((int) responseTime);
                aiMsg.setCreateTime(new Date());
                aiMsg.setDelFlag(0);
                // 添加到Redis缓存，会自动异步同步到数据库
                redisSessionCacheService.addMessageToCache(sessionId, aiMsg);
                
                // 6. 更新会话信息
                agentConversationService.updateConversation(sessionId, aiMessageSeq, null); // 更新消息总数

                // 7. 发送完成信号
                emitter.send(SseEmitter.event().name("end").data("[DONE]"));
                // 显式发送一个名为end的事件，不携带额外数据，作为流结束的明确信号
                emitter.send(SseEmitter.event().name("end")); 
                emitter.complete();

            } catch (Exception e) {
                long responseTime = System.currentTimeMillis() - startTime;
                
                // 保存错误消息到Redis缓存
                if (sessionId != null) {
                    AgentMessage errorMsg = new AgentMessage();
                    errorMsg.setSessionId(sessionId);
                    errorMsg.setMessageType(2); // AI回复
                    errorMsg.setMessageContent("抱歉，处理您的请求时出现了错误");
                    errorMsg.setMessageSeq(getNextMessageSeq(sessionId));
                    errorMsg.setResponseTime((int) responseTime);
                    errorMsg.setErrorMessage(e.getMessage());
                    errorMsg.setCreateTime(new Date());
                    errorMsg.setDelFlag(0);
                    // 添加到Redis缓存，会自动异步同步到数据库
                    redisSessionCacheService.addMessageToCache(sessionId, errorMsg);
                }
                
                emitter.completeWithError(e);
            }
        });

        emitter.onTimeout(() -> {
            emitter.complete();
        });

        return emitter;
    }
}





