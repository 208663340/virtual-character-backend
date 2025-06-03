package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.AgentMessage;
import com.nageoffer.shortlink.admin.dto.req.agent.AgentChatReqDTO;
import com.nageoffer.shortlink.admin.dto.req.user.UserMessageReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.agent.AgentMessageHistoryRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.agent.AgentMessageRespDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
* @author 20866
* @description 针对表【agent_message_0】的数据库操作Service
* @createDate 2025-05-27 13:33:37
*/
public interface AgentMessageService extends IService<AgentMessage> {

    /**
     * SSE流式聊天
     */
    SseEmitter chatWithSse(UserMessageReqDTO requestParam);
    /**
     * 统一userId获取器
     */
    Long getUserIdByUsername(String username);

    /**
     * 保存聊天消息
     */
    void saveMessage(AgentMessageRespDTO agentMessage);

    /**
     * 查询会话历史消息
     */
    List<AgentMessageHistoryRespDTO> getConversationHistory(String sessionId);

    /**
     * 分页查询历史消息
     */
    IPage<AgentMessageHistoryRespDTO> pageHistoryMessages(String username, String sessionId, Integer current, Integer size);


}
