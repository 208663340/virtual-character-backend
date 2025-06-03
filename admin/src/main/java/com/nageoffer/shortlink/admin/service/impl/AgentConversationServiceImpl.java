package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.dao.entity.AgentConversation;
import com.nageoffer.shortlink.admin.dao.mapper.AgentConversationMapper;
import com.nageoffer.shortlink.admin.dto.req.agent.AgentConversationPageReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.agent.AgentConversationRespDTO;
import com.nageoffer.shortlink.admin.service.AgentConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.nageoffer.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
 * 智能体会话服务实现类
 */
@Service
@RequiredArgsConstructor
public class AgentConversationServiceImpl extends ServiceImpl<AgentConversationMapper, AgentConversation>
        implements AgentConversationService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public String createConversation(String username, Long agentId, String firstMessage) {
        // 通过用户名获取用户ID
        Long userId = getUserIdByUsername(username);
        
        String sessionId = IdUtil.getSnowflakeNextIdStr();
        
        AgentConversation conversation = new AgentConversation();
        conversation.setSessionId(sessionId);
        conversation.setUserId(userId);
        conversation.setAgentId(agentId);
        conversation.setConversationTitle(generateTitle(firstMessage));
        conversation.setMessageCount(0);
        conversation.setTotalTokens(0);
        conversation.setStatus(1); // 进行中
        conversation.setDelFlag(0);
        
        baseMapper.insert(conversation);
        return sessionId;
    }

    @Override
    public IPage<AgentConversationRespDTO> pageConversations(String username, AgentConversationPageReqDTO reqDTO) {
        // 通过用户名获取用户ID
        Long userId = getUserIdByUsername(username);
        Page<AgentConversation> page = new Page<>(reqDTO.getCurrent(), reqDTO.getSize());
        
        IPage<AgentConversation> conversationPage = baseMapper.selectConversationPage(
                page, userId, reqDTO.getAgentId(), reqDTO.getStatus(), reqDTO.getKeyword());
        
        return conversationPage.convert(conversation -> {
            AgentConversationRespDTO respDTO = new AgentConversationRespDTO();
            BeanUtils.copyProperties(conversation, respDTO);
            return respDTO;
        });
    }

    @Override
    public void updateConversation(String sessionId, Integer messageCount, Integer totalTokens) {
        LambdaUpdateWrapper<AgentConversation> updateWrapper = new LambdaUpdateWrapper<AgentConversation>()
                .eq(AgentConversation::getSessionId, sessionId)
                .set(AgentConversation::getMessageCount, messageCount)
                .set(AgentConversation::getTotalTokens, totalTokens)
                .set(AgentConversation::getUpdateTime, new Date());
        
        baseMapper.update(null, updateWrapper);
    }

    @Override
    public void endConversation(String sessionId) {
        LambdaUpdateWrapper<AgentConversation> updateWrapper = new LambdaUpdateWrapper<AgentConversation>()
                .eq(AgentConversation::getSessionId, sessionId)
                .set(AgentConversation::getStatus, 2) // 已结束
                .set(AgentConversation::getUpdateTime, new Date());
        
        baseMapper.update(null, updateWrapper);
    }

    /**
     * 根据首条消息生成会话标题
     */
    private String generateTitle(String firstMessage) {
        if (firstMessage == null || firstMessage.trim().isEmpty()) {
            return "新对话";
        }
        
        String title = firstMessage.trim();
        if (title.length() > 20) {
            title = title.substring(0, 20) + "...";
        }
        
        return title;
    }

    /**
     * 通过用户名获取用户ID的通用方法
     */
    private Long getUserIdByUsername(String username) {
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
}