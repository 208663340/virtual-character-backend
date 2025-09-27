package com.hewei.hzyjy.xunzhi.controller;

import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.dto.req.coze.CozeWorkflowStreamReqDTO;
import com.hewei.hzyjy.xunzhi.service.CozeWorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

/**
 * Coze工作流控制器
 * @author nageoffer
 */
@Slf4j
@RestController
@RequestMapping("/api/xunzhi/v1/coze")
@RequiredArgsConstructor
@Validated
public class CozeWorkflowController {

    private final CozeWorkflowService cozeWorkflowService;

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        log.info("收到Coze健康检查请求");
        
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("status", "ok");
        healthData.put("timestamp", System.currentTimeMillis());
        
        try {
            // 检查Coze工作流服务是否正常
            boolean isHealthy = cozeWorkflowService.healthCheck();
            healthData.put("cozeWorkflowService", isHealthy ? "healthy" : "unhealthy");
            
            return Results.success(healthData);
        } catch (Exception e) {
            log.error("Coze健康检查失败", e);
            healthData.put("cozeWorkflowService", "error");
            healthData.put("error", e.getMessage());
            return Results.success(healthData);
        }
    }


    /**
     * SSE流式执行工作流
     * @param workflowId 工作流ID
     * @param requestParam 执行参数DTO
     */
    @PostMapping(value = "/workflow/{workflowId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> runWorkflowStream(
            @PathVariable String workflowId,
            @RequestBody CozeWorkflowStreamReqDTO requestParam) {
        
        log.info("收到工作流SSE流式执行请求，workflowId: {}, userInput: {}", workflowId, requestParam.getUserInput());
        
        return cozeWorkflowService.runWorkflowStream(workflowId, requestParam);
    }

    /**
     * SSE流式执行工作流（GET方式，简单）
     * @param workflowId 工作流ID
     * @param message 消息参数（可选）
     */
    @GetMapping(value = "/workflow/{workflowId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> runWorkflowStreamSimple(
            @PathVariable String workflowId,
            @RequestParam(required = false, defaultValue = "Hello") String message) {
        
        log.info("收到简单工作流SSE流式执行请求，workflowId: {}, message: {}", workflowId, message);
        
        return cozeWorkflowService.runWorkflowStreamSimple(workflowId, message);
    }
}