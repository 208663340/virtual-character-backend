package com.hewei.hzyjy.xunzhi.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hewei.hzyjy.xunzhi.common.convention.exception.ClientException;
import com.hewei.hzyjy.xunzhi.dao.entity.AgentMessage;
import com.hewei.hzyjy.xunzhi.dao.entity.AgentPropertiesDO;
import com.hewei.hzyjy.xunzhi.dao.repository.AgentMessageRepository;

import com.hewei.hzyjy.xunzhi.dto.req.agent.InterviewQuestionReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.user.UserMessageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.agent.AgentMessageHistoryRespDTO;
import com.hewei.hzyjy.xunzhi.toolkit.xunfei.XingChenAIClient;

import com.hewei.hzyjy.xunzhi.service.AgentConversationService;
import com.hewei.hzyjy.xunzhi.service.AgentMessageService;
import com.hewei.hzyjy.xunzhi.service.InterviewQuestionService;
import com.hewei.hzyjy.xunzhi.toolkit.xunfei.AIContentAccumulator;
import com.hewei.hzyjy.xunzhi.toolkit.xunfei.AgentPropertiesLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
* @author 20866
* @description 针对表【agent_message_0】的数据库操作Service实现
* @createDate 2025-05-27 13:33:37
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class AgentMessageServiceImpl implements AgentMessageService{

    private final AgentMessageRepository agentMessageRepository;
    private final XingChenAIClient xingChenAIClient;
    private final AgentPropertiesLoader agentPropertiesLoader;
    private final AgentConversationService agentConversationService;
    private final InterviewQuestionService interviewQuestionService;
    private final SimpleAsyncTaskExecutor asyncTask = new SimpleAsyncTaskExecutor();



    @Override
    public List<AgentMessageHistoryRespDTO> getConversationHistory(String sessionId) {
        // 直接从数据库查询历史消息
        List<AgentMessage> messages = agentMessageRepository
            .findBySessionIdAndDelFlagOrderByMessageSeqAsc(sessionId, 0);
        
        return messages.stream()
            .map(message -> {
                AgentMessageHistoryRespDTO dto = new AgentMessageHistoryRespDTO();
                BeanUtils.copyProperties(message, dto);
                return dto;
            })
            .collect(Collectors.toList());
    }

    @Override
    public IPage<AgentMessageHistoryRespDTO> pageHistoryMessages(String username, String sessionId, Integer current, Integer size) {
        Pageable pageable = PageRequest.of(current - 1, size);
        org.springframework.data.domain.Page<AgentMessage> messagePage;
        
        if (StrUtil.isNotBlank(sessionId)) {
            messagePage = agentMessageRepository
                .findBySessionIdAndDelFlagOrderByCreateTimeAsc(sessionId, 0, pageable);
        } else {
            messagePage = agentMessageRepository
                .findByDelFlagOrderByCreateTimeDesc(0, pageable);
        }
        
        // 转换为MyBatis-Plus的IPage格式以保持接口兼容性
        Page<AgentMessageHistoryRespDTO> resultPage = new Page<>(current, size);
        resultPage.setTotal(messagePage.getTotalElements());
        resultPage.setRecords(
            messagePage.getContent().stream()
                .map(message -> {
                    AgentMessageHistoryRespDTO respDTO = new AgentMessageHistoryRespDTO();
                    BeanUtils.copyProperties(message, respDTO);
                    return respDTO;
                })
                .collect(Collectors.toList())
        );
        
        return resultPage;
    }


    /**
     * 获取下一个消息序号
     */
    private Integer getNextMessageSeq(String sessionId) {
        AgentMessage lastMessage = agentMessageRepository
            .findTopBySessionIdAndDelFlagOrderByMessageSeqDesc(sessionId, 0);
        if (lastMessage == null) {
            return 1;
        }
        return lastMessage.getMessageSeq() + 1;
    }

    @Override
    public SseEmitter agentChatSse(UserMessageReqDTO requestParam) {
        // sessionId现在必须通过参数传入
        String sessionId = requestParam.getSessionId();
        if (StrUtil.isBlank(sessionId)) {
            throw new IllegalArgumentException("sessionId不能为空");
        }
        
        SseEmitter emitter = new SseEmitter(18000L);
        String userName = requestParam.getUserName() == null ? "默认" : requestParam.getUserName();
        Long agentId = requestParam.getAgentId() == null ? 1345345L : requestParam.getAgentId();
        String userMessage = requestParam.getInputMessage() == null ? "没有输入" : requestParam.getInputMessage();
        
        // 新增内容累积器
        AIContentAccumulator accumulator = new AIContentAccumulator();
        
        asyncTask.submit(() -> {
            long startTime = System.currentTimeMillis();
            
            try {
                // 1. 处理历史消息
                List<AgentMessageHistoryRespDTO> historyMessages = getConversationHistory(sessionId);
                // 将 historyMessages 转换为 XingChenAIClient 需要的 JSON 格式
                List<HashMap<String, String>> historyList = new ArrayList<>();
                if (CollUtil.isNotEmpty(historyMessages)) {
                    historyList = historyMessages.stream().map(h -> {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("role", h.getMessageType() == 1 ? "user" : "assistant");
                        map.put("content_type", "text");
                        map.put("content", h.getMessageContent());
                        return map;
                    }).collect(Collectors.toList());
                }
                // 统一转换为JSON字符串，确保不为null
                String historyJson = JSON.toJSONString(historyList);
                int nextMessageSeq = getNextMessageSeq(sessionId);

                // 2. 保存用户消息到数据库
                AgentMessage userMsg = new AgentMessage();
                userMsg.setSessionId(sessionId);
                userMsg.setMessageType(1); // 用户消息
                userMsg.setMessageContent(userMessage);
                userMsg.setMessageSeq(nextMessageSeq);
                userMsg.setCreateTime(new Date());
                userMsg.setDelFlag(0);
                // 直接保存到数据库
                agentMessageRepository.save(userMsg);
                
                // 3. 根据智能体ID获取对应的配置
                AgentPropertiesDO agentProperties = agentPropertiesLoader.getAgentPropertiesMap().get(agentId);
                
                if (agentProperties == null) {
                    throw new ClientException("智能体配置不存在");
                }
                
                // 4. 调用AI接口进行流式传输
                xingChenAIClient.chat(
                        userMessage,
                        sessionId,
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

                // 5. 流式传输完成后，保存AI回复消息到数据库
                String fullContent = accumulator.getFullContent();
                long responseTime = System.currentTimeMillis() - startTime;
                
                AgentMessage aiMsg = new AgentMessage();
                aiMsg.setSessionId(sessionId);
                aiMsg.setMessageType(2); // AI回复
                aiMsg.setMessageContent(fullContent);
                // 如果是新会话的第一次AI回复，序号应为2，否则通过 getNextMessageSeq 获取
                int aiMessageSeq = (nextMessageSeq == 1) ? 2 : getNextMessageSeq(sessionId);
                aiMsg.setMessageSeq(aiMessageSeq);
                aiMsg.setResponseTime((int) responseTime);
                aiMsg.setCreateTime(new Date());
                aiMsg.setDelFlag(0);
                // 直接保存到数据库
                agentMessageRepository.save(aiMsg);
                
                // 6. 更新会话信息
                agentConversationService.updateConversation(sessionId, aiMessageSeq, null); // 更新消息总数

                // 7. 发送完成信号
                emitter.send(SseEmitter.event().name("end").data("[DONE]"));
                emitter.complete();

            } catch (Exception e) {
                long responseTime = System.currentTimeMillis() - startTime;
                
                // 保存错误消息到数据库
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
                    // 直接保存到数据库
                    agentMessageRepository.save(errorMsg);
                }
                
                emitter.completeWithError(e);
            }
        });

        emitter.onTimeout(() -> {
            emitter.complete();
        });

        return emitter;
    }
    
    @Override
    public SseEmitter extractInterviewQuestions(InterviewQuestionReqDTO reqDTO) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        Long agentId = reqDTO.getAgentId() == null ? 1345345L : reqDTO.getAgentId();
        
        // 新增内容累积器用于收集AI响应
        AIContentAccumulator accumulator = new AIContentAccumulator();
        
        // 异步处理面试题抽取
        CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            try {
                // 1. 上传文件到讯飞服务器获取URL
                String fileUrl = null;
                if (reqDTO.getResumePdf() != null && !reqDTO.getResumePdf().isEmpty()) {
                    try {
                        // 获取智能体配置中的API密钥
                        AgentPropertiesDO agentProperties = agentPropertiesLoader.getAgentPropertiesMap().get(agentId);
                        if (agentProperties == null) {
                            throw new RuntimeException("智能体配置不存在");
                        }
                        
                        // 上传文件到讯飞服务器获取URL
                        fileUrl = xingChenAIClient.uploadFile(
                            reqDTO.getResumePdf(), 
                            agentProperties.getApiKey(),
                            agentProperties.getApiSecret()
                        );
                        log.info("文件上传成功，URL: {}", fileUrl);
                    } catch (Exception e) {
                        log.error("文件上传失败: {}", e.getMessage());
                        sseEmitter.send(SseEmitter.event().data("文件上传失败，请重试"));
                        sseEmitter.complete();
                        return;
                    }
                }
                
                // 2. 构建面试题抽取的提示词
                String promptBuilder = "帮我抽取一些面试题";
                // 检查文件URL
                if (fileUrl == null) {
                    throw new RuntimeException("简历不存在");
                }

                // 3. 调用AI接口进行流式处理
                AgentPropertiesDO agentProperties = agentPropertiesLoader.getAgentPropertiesMap().get(agentId);
                if (agentProperties == null) {
                    throw new RuntimeException("智能体配置不存在");
                }
                
                // 4. 使用现有的XingChenAIClient进行流式传输
                xingChenAIClient.chat(
                        promptBuilder,
                    reqDTO.getSessionId(),
                    "{}", // 空的历史记录
                    true,
                    new OutputStream() {
                        @Override
                        public void write(int b) { /* 不需要实现 */ }

                        @Override
                        public void write(byte[] b, int off, int len) throws IOException {
                            // 发送数据到前端
                            String jsonChunk = new String(b, off, len);
                            sseEmitter.send(SseEmitter.event().data(jsonChunk));
                            
                            // 累积内容到字符串
                            accumulator.appendChunk(b);
                        }

                        @Override
                        public void flush() { /* 确保数据发送 */ }
                    },
                    data -> {},
                    agentProperties.getApiKey(),
                    agentProperties.getApiSecret(),
                    agentProperties.getApiFlowId(),
                    fileUrl // 传递文件URL
                );
                
                // 5. 流式传输完成后，保存面试题数据到MongoDB
                String fullContent = accumulator.getFullContent();
                long responseTime = System.currentTimeMillis() - startTime;
                
                // 设置文件URL到请求DTO中
                reqDTO.setResumeFileUrl(fileUrl);
                
                // 保存面试题数据
                try {
                    interviewQuestionService.createFromAIResponse(
                        reqDTO, 
                        fullContent, 
                        (int) responseTime, 
                        null // tokenCount暂时为null，可以后续从AI响应中解析
                    );
                    log.info("面试题数据保存成功，会话ID: {}", reqDTO.getSessionId());
                } catch (Exception e) {
                    log.error("面试题数据保存失败，会话ID: {}, 错误: {}", reqDTO.getSessionId(), e.getMessage());
                }
                
                // 6. 发送完成信号
                sseEmitter.send(SseEmitter.event().name("end").data("[DONE]"));
                sseEmitter.complete();
                        
            } catch (Exception e) {
                long responseTime = System.currentTimeMillis() - startTime;
                log.error("面试题抽取处理异常: {}", e.getMessage(), e);
                
                // 保存错误记录到MongoDB
                try {
                    reqDTO.setResumeFileUrl(null); // 错误情况下文件URL可能为空
                    interviewQuestionService.createFromAIResponse(
                        reqDTO, 
                        "{\"error\":\"" + e.getMessage() + "\"}", 
                        (int) responseTime, 
                        null
                    );
                } catch (Exception saveException) {
                    log.error("保存错误记录失败: {}", saveException.getMessage());
                }
                
                try {
                    sseEmitter.send(SseEmitter.event().data("系统异常，请稍后重试。"));
                    sseEmitter.complete();
                } catch (IOException ioException) {
                    log.error("发送异常信息失败: {}", ioException.getMessage());
                }
            }
        });
        
        // 设置SSE连接的超时和错误处理
        sseEmitter.onTimeout(() -> {
            log.warn("面试题抽取SSE连接超时，用户: {}", reqDTO.getUserName());
            sseEmitter.complete();
        });
        
        sseEmitter.onError(throwable -> {
            log.error("面试题抽取SSE连接错误: {}", throwable.getMessage());
            sseEmitter.complete();
        });
        
        return sseEmitter;
    }
}





