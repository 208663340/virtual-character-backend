package com.nageoffer.shortlink.xunzhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.xunzhi.common.convention.result.Result;
import com.nageoffer.shortlink.xunzhi.common.convention.result.Results;
import com.nageoffer.shortlink.xunzhi.dto.req.agent.AgentConversationPageReqDTO;
import com.nageoffer.shortlink.xunzhi.dto.req.agent.AgentSessionCreateReqDTO;
import com.nageoffer.shortlink.xunzhi.dto.req.user.UserMessageReqDTO;
import com.nageoffer.shortlink.xunzhi.dto.resp.agent.AgentConversationRespDTO;
import com.nageoffer.shortlink.xunzhi.dto.resp.agent.AgentMessageHistoryRespDTO;
import com.nageoffer.shortlink.xunzhi.dto.resp.agent.AgentSessionCreateRespDTO;

import com.nageoffer.shortlink.xunzhi.service.AgentConversationService;
import com.nageoffer.shortlink.xunzhi.service.AgentMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 *Agent文字聊天接口
 * @author nageoffer
 * @date 2023/9/27
 */
@RestController
@RequiredArgsConstructor
public class AgentController {

    private final AgentMessageService agentMessageService;
    private final AgentConversationService agentConversationService;


    /**
     * 创建Agent会话
     * @param requestParam 会话创建请求参数
     * @return 会话ID和标题
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/agent/sessions")
    public Result<AgentSessionCreateRespDTO> createSession(@RequestBody AgentSessionCreateReqDTO requestParam) {
        AgentSessionCreateRespDTO result = agentConversationService.createConversationWithTitle(
                requestParam.getUserName(), 
                requestParam.getAgentId(), 
                requestParam.getFirstMessage()
        );
        
        return Results.success(result);
    }

    /**
     * Agent文字聊天SSE接口
     * @return SSE流
     */
    @GetMapping("/api/xunzhi-agent/admin/v1/agent/chat")
    public SseEmitter chat(UserMessageReqDTO requestParam) {
        return agentMessageService.agentChatSse(requestParam);
    }


    /**
     * 分页查询用户会话列表
     */
    @GetMapping("/api/xunzhi-agent/admin/v1/agent/conversations")
    public Result<IPage<AgentConversationRespDTO>> pageConversations(
            @RequestHeader("username") String username,
            AgentConversationPageReqDTO requestParam) {
        return Results.success(agentConversationService.pageConversations(username, requestParam));
    }

    /**
     * 查询会话历史消息
     */
    @GetMapping("/api/xunzhi-agent/admin/v1/agent/conversations/{sessionId}/messages")
    public Result<List<AgentMessageHistoryRespDTO>> getConversationHistory(@PathVariable String sessionId) {
        return Results.success(agentMessageService.getConversationHistory(sessionId));
    }

    /**
     * 分页查询历史消息
     */
    @GetMapping("/api/xunzhi-agent/admin/v1/agent/messages/history")
    public Result<IPage<AgentMessageHistoryRespDTO>> pageHistoryMessages(
            @RequestHeader("username") String username,
            @RequestParam(required = false) String sessionId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        return Results.success(agentMessageService.pageHistoryMessages(username, sessionId, current, size));
    }

    /**
     * 结束会话
     */
    @PutMapping("/api/xunzhi-agent/admin/v1/agent/conversations/{sessionId}/end")
    public Result<Void> endConversation(@PathVariable String sessionId) {
        agentConversationService.endConversation(sessionId);
        return Results.success();
    }


}
