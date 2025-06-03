package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.AgentConversation;
import com.nageoffer.shortlink.admin.dto.req.agent.AgentConversationPageReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.agent.AgentConversationRespDTO;

/**
 * 智能体会话服务接口
 */
public interface AgentConversationService extends IService<AgentConversation> {

    /**
     * 创建新会话
     */
    String createConversation(String username, Long agentId, String firstMessage);

    /**
     * 分页查询用户会话列表
     */
    IPage<AgentConversationRespDTO> pageConversations(String username, AgentConversationPageReqDTO reqDTO);

    /**
     * 更新会话信息
     */
    void updateConversation(String sessionId, Integer messageCount, Integer totalTokens);

    /**
     * 结束会话
     */
    void endConversation(String sessionId);
}