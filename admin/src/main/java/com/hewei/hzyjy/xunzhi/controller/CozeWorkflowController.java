package com.hewei.hzyjy.xunzhi.controller;

import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.toolkit.coze.CozeClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
public class CozeWorkflowController {

    private final CozeClient cozeClient;

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
            // 检查Coze客户端是否正常
            boolean isHealthy = cozeClient.healthCheck();
            healthData.put("cozeClient", isHealthy ? "healthy" : "unhealthy");
            
            return Results.success(healthData);
        } catch (Exception e) {
            log.error("Coze健康检查失败", e);
            healthData.put("cozeClient", "error");
            healthData.put("error", e.getMessage());
            return Results.success(healthData);
        }
    }

    /**
     * 获取工作流列表
     */
    @GetMapping("/workflows")
    public Result<Map<String, Object>> getWorkflows() {
        log.info("收到获取工作流列表请求");
        
        try {
            Map<String, Object> workflows = cozeClient.getWorkflowList();
            return Results.success(workflows);
        } catch (Exception e) {
            log.error("获取工作流列表失败", e);
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("error", "获取工作流列表失败: " + e.getMessage());
            return Results.success(errorData);
        }
    }

    /**
     * 执行工作流（最简单版本）
     * @param workflowId 工作流ID
     * @param parameters 执行参数（可选）
     */
    @PostMapping("/workflow/{workflowId}/run")
    public Result<Map<String, Object>> runWorkflow(
            @PathVariable String workflowId,
            @RequestBody(required = false) Map<String, Object> parameters) {
        
        log.info("收到工作流执行请求，workflowId: {}", workflowId);
        
        try {
            // 如果没有传参数，使用空Map
            if (parameters == null) {
                parameters = new HashMap<>();
            }
            
            // 调用CozeClient执行工作流
            Object result = cozeClient.runWorkflow(workflowId, parameters);
            
            // 构造返回结果
            Map<String, Object> response = new HashMap<>();
            response.put("workflowId", workflowId);
            response.put("status", "completed");
            response.put("result", result);
            response.put("timestamp", System.currentTimeMillis());
            
            return Results.success(response);
            
        } catch (Exception e) {
            log.error("工作流执行失败，workflowId: {}", workflowId, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("workflowId", workflowId);
            errorResponse.put("status", "failed");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return Results.success(errorResponse);
        }
    }

    /**
     * 执行工作流（GET方式，更简单）
     * @param workflowId 工作流ID
     * @param message 消息参数（可选）
     */
    @GetMapping("/workflow/{workflowId}/run")
    public Result<Map<String, Object>> runWorkflowSimple(
            @PathVariable String workflowId,
            @RequestParam(required = false, defaultValue = "Hello") String message) {
        
        log.info("收到简单工作流执行请求，workflowId: {}, message: {}", workflowId, message);
        
        try {
            // 构造简单参数
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("message", message);
            
            // 调用CozeClient执行工作流
            Object result = cozeClient.runWorkflow(workflowId, parameters);
            
            // 构造返回结果
            Map<String, Object> response = new HashMap<>();
            response.put("workflowId", workflowId);
            response.put("input", message);
            response.put("output", result);
            response.put("status", "success");
            response.put("timestamp", System.currentTimeMillis());
            
            return Results.success(response);
            
        } catch (Exception e) {
            log.error("简单工作流执行失败，workflowId: {}", workflowId, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("workflowId", workflowId);
            errorResponse.put("input", message);
            errorResponse.put("status", "error");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return Results.success(errorResponse);
        }
    }
}