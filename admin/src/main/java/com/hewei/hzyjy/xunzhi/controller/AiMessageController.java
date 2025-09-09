package com.hewei.hzyjy.xunzhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hewei.hzyjy.xunzhi.annotation.PreventDuplicateSubmit;
import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiMessageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.AiMessageHistoryRespDTO;
import com.hewei.hzyjy.xunzhi.service.AiMessageService;
import com.hewei.hzyjy.xunzhi.common.util.SaTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * AI消息控制器
 * @author nageoffer
 */
@RestController
@RequestMapping("/api/xunzhi/v1/ai")
@RequiredArgsConstructor
public class AiMessageController {
    
    private final AiMessageService aiMessageService;
    private final SaTokenUtil saTokenUtil;
    
    /**
     * AI聊天Flux接口
     */
    @PreventDuplicateSubmit(
        prefix = "ai_chat",
        expireTime = 30,
        waitTime = 0,
        message = "请勿重复发送消息，请等待当前消息处理完成",
        userLevel = true,
        sessionLevel = true,
        messageSeqLevel = true
    )
    @PostMapping(value = "/sessions/{sessionId}/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@PathVariable String sessionId, @RequestBody AiMessageReqDTO requestParam, HttpServletRequest request) {
        // 从token中获取用户名
        String username = saTokenUtil.getUsernameFromRequest(request);
        if (username != null) {
            requestParam.setUserName(username);
        }
        requestParam.setSessionId(sessionId);
        return aiMessageService.aiChatFlux(requestParam);
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
            @RequestParam(required = false) String sessionId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        // 从token中获取用户名
        String username = saTokenUtil.getUsernameFromRequest(request);
        IPage<AiMessageHistoryRespDTO> result = aiMessageService.pageHistoryMessages(sessionId, current, size);
        return Results.success(result);
    }
}