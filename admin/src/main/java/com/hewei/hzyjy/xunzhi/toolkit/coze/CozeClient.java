package com.hewei.hzyjy.xunzhi.toolkit.coze;

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
import java.util.function.Consumer;

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
     * SSE流式执行工作流（与前端SSE兼容）
     * @param workflowId 工作流ID
     * @param parameters 输入参数
     * @param outputStream 输出流，用于SSE数据推送
     * @param callback 回调函数
     */
    public void runWorkflowSSE(String workflowId, Map<String, Object> parameters, 
                              java.io.OutputStream outputStream, Consumer<String> callback) throws Exception {
        log.info("开始SSE流式执行Coze工作流，workflowId: {}, parameters: {}", workflowId, parameters);
        
        // 构建工作流执行请求
        RunWorkflowReq request = RunWorkflowReq.builder()
                .workflowID(workflowId)
                .parameters(parameters != null ? parameters : new HashMap<>())
                .build();
        
        // 获取流式事件
        Flowable<WorkflowEvent> flowable = cozeAPI.workflows().runs().stream(request);
        
        // 处理SSE事件流
        flowable.blockingForEach(event -> {
            WorkflowEventType eventType = event.getEvent();
            String jsonData = "";
            
            if (eventType == WorkflowEventType.MESSAGE) {
                if (event.getMessage() != null) {
                    jsonData = "{\"type\":\"content\",\"content\":\"" + event.getMessage() + "\"}";
                }
            } else if (eventType == WorkflowEventType.ERROR) {
                if (event.getError() != null) {
                    jsonData = "{\"type\":\"error\",\"error\":\"" + event.getError() + "\"}";
                }
            } else if (eventType == WorkflowEventType.DONE) {
                jsonData = "{\"type\":\"done\",\"content\":\"\"}";
            }
            
            if (!jsonData.isEmpty()) {
                try {
                    // 发送SSE格式数据
                    outputStream.write(jsonData.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                    outputStream.flush();
                    
                    // 回调处理
                    callback.accept(jsonData);
                    
                    log.debug("[Coze SSE数据] {}", jsonData);
                } catch (Exception e) {
                    log.error("Coze SSE数据发送失败", e);
                    throw new RuntimeException(e);
                }
            }
        });
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
