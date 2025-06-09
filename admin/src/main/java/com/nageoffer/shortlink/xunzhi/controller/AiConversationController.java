package com.nageoffer.shortlink.xunzhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.xunzhi.common.convention.result.Result;
import com.nageoffer.shortlink.xunzhi.common.convention.result.Results;
import com.nageoffer.shortlink.xunzhi.dto.req.ai.AiConversationPageReqDTO;
import com.nageoffer.shortlink.xunzhi.dto.req.ai.AiSessionCreateReqDTO;
import com.nageoffer.shortlink.xunzhi.dto.resp.ai.AiConversationRespDTO;
import com.nageoffer.shortlink.xunzhi.dto.resp.ai.AiSessionCreateRespDTO;
import com.nageoffer.shortlink.xunzhi.service.AiConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



/**
 * AI会话控制器
 * @author nageoffer
 */
@RestController
@RequestMapping("/api/xunzhi-agent/ai/conversation")
@RequiredArgsConstructor
public class AiConversationController {
    
    private final AiConversationService aiConversationService;
    
    /**
     * 创建AI会话
     */
    @PostMapping
    public Result<AiSessionCreateRespDTO> createConversation( @RequestBody AiSessionCreateReqDTO requestParam) {
        AiSessionCreateRespDTO result = aiConversationService.createConversationWithTitle(
                requestParam.getUserName(), 
                requestParam.getAiId(), 
                requestParam.getFirstMessage()
        );
        return Results.success(result);
    }
    
    /**
     * 分页查询会话列表
     */
    @GetMapping("/page")
    public Result<IPage<AiConversationRespDTO>> pageConversations(
            @RequestHeader("username") String username,
            AiConversationPageReqDTO requestParam) {
        IPage<AiConversationRespDTO> result = aiConversationService.pageConversations(username, requestParam);
        return Results.success(result);
    }
    
    /**
     * 更新会话信息
     */
    @PutMapping("/{sessionId}")
    public Result<Void> updateConversation(@PathVariable String sessionId, 
                                          @RequestParam(required = false) Integer messageCount,
                                          @RequestParam(required = false) String title) {
        aiConversationService.updateConversation(sessionId, messageCount, title);
        return Results.success();
    }
    
    /**
     * 结束会话
     */
    @PutMapping("/{sessionId}/end")
    public Result<Void> endConversation(@PathVariable String sessionId) {
        aiConversationService.endConversation(sessionId);
        return Results.success();
    }
    
    /**
     * 删除会话
     */
    @DeleteMapping("/{sessionId}")
    public Result<Void> deleteConversation(@PathVariable String sessionId) {
        aiConversationService.deleteConversation(sessionId);
        return Results.success();
    }
    
    /**
     * 根据会话ID获取会话信息
     */
    @GetMapping("/{sessionId}")
    public Result<AiConversationRespDTO> getConversationById(@PathVariable String sessionId) {
        AiConversationRespDTO result = aiConversationService.getConversationBySessionId(sessionId);
        return Results.success(result);
    }
}