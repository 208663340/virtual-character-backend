package com.hewei.hzyjy.xunzhi.toolkit.coze;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.client.workflows.run.RunWorkflowReq;
import com.coze.openapi.client.workflows.run.model.WorkflowEvent;
import com.coze.openapi.client.workflows.run.model.WorkflowEventType;
import com.coze.openapi.service.service.CozeAPI;
import com.hewei.hzyjy.xunzhi.config.coze.CozeProperties;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Coze工作流客户端
 * @author nageoffer
 */
@Slf4j
@Component
public class CozeClient {
    
    private final CozeProperties properties;
    private final CozeAPI cozeAPI;
    
    /**
     * 初始化Coze客户端
     */
    public CozeClient(CozeProperties properties) {
        this.properties = properties;
        
        // 创建认证客户端
        TokenAuth authCli = new TokenAuth(properties.getApiKey());
        
        // 初始化Coze API客户端
        this.cozeAPI = new CozeAPI.Builder()
                .baseURL(properties.getBaseUrl())
                .auth(authCli)
                .readTimeout(properties.getReadTimeout().intValue())
                .build();
        
        log.info("Coze客户端初始化成功，baseUrl: {}", properties.getBaseUrl());
    }
    
    /**
     * 同步执行工作流
     * @param workflowId 工作流ID
     * @param parameters 输入参数
     * @return 执行结果
     */
    public JSONObject runWorkflow(String workflowId, Map<String, Object> parameters) throws Exception {
        log.info("开始同步执行Coze工作流，workflowId: {}, parameters: {}", workflowId, parameters);
        
        // 构建工作流执行请求
        RunWorkflowReq request = RunWorkflowReq.builder()
                .workflowID(workflowId)
                .parameters(parameters != null ? parameters : new HashMap<>())
                .build();
        
        // 同步执行（收集所有流式事件）
        try {
            StringBuilder resultBuilder = new StringBuilder();
            JSONObject resultData = new JSONObject();
            
            Flowable<WorkflowEvent> flowable = cozeAPI.workflows().runs().stream(request);
            
            flowable.blockingForEach(event -> {
                WorkflowEventType eventType = event.getEvent();
                if (eventType == WorkflowEventType.MESSAGE) {
                    if (event.getMessage() != null) {
                        resultBuilder.append(event.getMessage());
                    }
                } else if (eventType == WorkflowEventType.ERROR) {
                    if (event.getError() != null) {
                        throw new RuntimeException("工作流执行错误: " + event.getError());
                    }
                } else if (eventType == WorkflowEventType.DONE) {
                    log.info("工作流执行完成");
                } else {
                    log.debug("收到工作流事件: {}", event.getEvent());
                }
            });
            
            resultData.put("status", "completed");
            resultData.put("result", resultBuilder.toString());
            resultData.put("workflowId", workflowId);
            
            log.info("Coze工作流同步执行成功");
            return resultData;
            
        } catch (Exception e) {
            log.error("Coze工作流执行失败", e);
            throw e;
        }
    }
    
    /**
     * 流式执行工作流
     * @param workflowId 工作流ID
     * @param parameters 输入参数
     * @return 事件流
     */
    public Flowable<WorkflowEvent> runWorkflowStream(String workflowId, Map<String, Object> parameters) throws Exception {
        log.info("开始流式执行Coze工作流，workflowId: {}, parameters: {}", workflowId, parameters);
        
        // 构建工作流执行请求
        RunWorkflowReq request = RunWorkflowReq.builder()
                .workflowID(workflowId)
                .parameters(parameters != null ? parameters : new HashMap<>())
                .build();
        
        // 返回流式执行结果
        return cozeAPI.workflows().runs().stream(request);
    }
    
    /**
     * 异步执行工作流（模拟）
     * @param workflowId 工作流ID
     * @param parameters 输入参数
     * @return 执行ID，用于后续查询状态
     */
    public String runWorkflowAsync(String workflowId, Map<String, Object> parameters) throws Exception {
        log.info("开始异步执行Coze工作流，workflowId: {}, parameters: {}", workflowId, parameters);
        
        // 注意：Coze官方SDK主要支持流式执行
        // 这里我们返回一个模拟的执行ID，实际应用中需要根据业务需求处理
        String executeId = "async-" + System.currentTimeMillis();
        log.info("Coze异步工作流提交成功，executeId: {}", executeId);
        return executeId;
    }
    
    /**
     * 查询工作流执行状态（模拟）
     * @param executeId 执行ID
     * @return 执行状态和结果
     */
    public JSONObject getWorkflowStatus(String executeId) throws Exception {
        log.info("查询Coze工作流执行状态，executeId: {}", executeId);
        
        // 模拟状态查询结果
        JSONObject result = new JSONObject();
        result.put("execute_id", executeId);
        result.put("status", "completed");
        result.put("result", "工作流执行完成");
        
        log.info("查询Coze工作流状态成功，结果: {}", result);
        return result;
    }
    
    /**
     * 获取工作流列表（通过Bot API模拟）
     * @return 工作流列表
     */
    public JSONObject getWorkflowList() throws Exception {
        log.info("获取Coze工作流列表");
        
        // 注意：官方SDK主要提供Bot和Chat功能，工作流列表API可能需要通过其他方式获取
        // 这里返回一个模拟的结果
        JSONObject result = new JSONObject();
        result.put("workflows", new String[]{"workflow-1", "workflow-2"});
        result.put("total", 2);
        
        log.info("获取Coze工作流列表成功");
        return result;
    }
    
    /**
     * 健康检查
     * @return 是否正常
     */
    public boolean healthCheck() {
        try {
            // 简单的健康检查，确认客户端初始化正常
            log.info("执行Coze健康检查");
            return cozeAPI != null;
        } catch (Exception e) {
            log.error("Coze健康检查失败", e);
            return false;
        }
    }
    
    /**
     * 获取CozeAPI实例（用于高级操作）
     * @return CozeAPI实例
     */
    public CozeAPI getCozeAPI() {
        return cozeAPI;
    }
}
