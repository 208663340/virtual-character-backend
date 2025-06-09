package com.nageoffer.shortlink.xunzhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.xunzhi.common.convention.result.Result;
import com.nageoffer.shortlink.xunzhi.common.convention.result.Results;
import com.nageoffer.shortlink.xunzhi.dto.req.ai.AiMessageReqDTO;
import com.nageoffer.shortlink.xunzhi.dto.resp.ai.AiMessageHistoryRespDTO;
import com.nageoffer.shortlink.xunzhi.service.AiMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.List;

/**
 * AI消息控制器
 * @author nageoffer
 */
@RestController
@RequestMapping("/api/xunzhi-agent/ai/message")
@RequiredArgsConstructor
public class AiMessageController {
    
    private final AiMessageService aiMessageService;
    
    /**
     * AI聊天SSE接口
     */
    @GetMapping(value = "/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter aiChatSse(AiMessageReqDTO requestParam) {
        return aiMessageService.aiChatSse(requestParam);
    }
    
    /**
     * 查询会话历史消息
     */
    @GetMapping("/history/{sessionId}")
    public Result<List<AiMessageHistoryRespDTO>> getConversationHistory(@PathVariable String sessionId) {
        List<AiMessageHistoryRespDTO> result = aiMessageService.getConversationHistory(sessionId);
        return Results.success(result);
    }
    
    /**
     * 分页查询历史消息
     */
    @GetMapping("/history/page")
    public Result<IPage<AiMessageHistoryRespDTO>> pageHistoryMessages(
            @RequestParam String username,
            @RequestParam(required = false) String sessionId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        IPage<AiMessageHistoryRespDTO> result = aiMessageService.pageHistoryMessages(username, sessionId, current, size);
        return Results.success(result);
    }
}