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
import reactor.core.publisher.Flux;

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
     * 流式执行工作流（返回Flux用于真正的流式处理）
     * @param workflowId 工作流ID
     * @param parameters 输入参数
     * @return Flux<String> 流式数据
     */
    public Flux<String> runWorkflowStreamReactive(String workflowId, Map<String, Object> parameters) {
        log.info("开始响应式流式执行Coze工作流，workflowId: {}, parameters: {}", workflowId, parameters);
        
        return Flux.create(sink -> {
            try {
                // 构建工作流执行请求
                RunWorkflowReq request = RunWorkflowReq.builder()
                        .workflowID(workflowId)
                        .parameters(parameters != null ? parameters : new HashMap<>())
                        .build();
                
                log.info("[CozeClient] 构建的请求: {}", request);
                
                // 获取流式事件
                Flowable<WorkflowEvent> flowable = cozeAPI.workflows().runs().stream(request);
                log.info("[CozeClient] 获取到Flowable流式事件");
                
                // 使用非阻塞方式处理事件流
                flowable.subscribe(
                    event -> {
                        log.info("[CozeClient] 收到事件: {}", event);
                        // 处理每个事件
                        WorkflowEventType eventType = event.getEvent();
                        String jsonData = "";
                        
                        if (eventType == WorkflowEventType.MESSAGE) {
                            if (event.getMessage() != null) {
                                jsonData = "{\"type\":\"content\",\"content\":\"" + escapeJsonString(String.valueOf(event.getMessage())) + "\"}";
                            }
                        } else if (eventType == WorkflowEventType.ERROR) {
                            if (event.getError() != null) {
                                jsonData = "{\"type\":\"error\",\"error\":\"" + escapeJsonString(String.valueOf(event.getError())) + "\"}";
                            }
                        } else if (eventType == WorkflowEventType.DONE) {
                            jsonData = "{\"type\":\"done\",\"content\":\"\"}";
                        }
                        
                        if (!jsonData.isEmpty()) {
                            log.info("[CozeClient] 发送数据: {}", jsonData);
                            // 立即发送数据到前端（Spring WebFlux会自动添加SSE格式）
                            sink.next(jsonData);
                        }
                    },
                    error -> {
                        // 处理错误
                        log.error("[CozeClient] Coze工作流流式执行出错", error);
                        sink.next("{\"type\":\"error\",\"error\":\"" + escapeJsonString(error.getMessage()) + "\"}");
                        sink.error(error);
                    },
                    () -> {
                        // 完成处理
                        log.info("[CozeClient] Coze工作流流式执行完成，workflowId: {}", workflowId);
                        sink.next("{\"type\":\"done\",\"content\":\"\"}");
                        sink.complete();
                    }
                );
                
            } catch (Exception e) {
                log.error("[CozeClient] Coze工作流流式执行初始化失败，workflowId: {}", workflowId, e);
                sink.next("{\"type\":\"error\",\"error\":\"" + escapeJsonString(e.getMessage()) + "\"}");
                sink.error(e);
            }
        });
    }
    
    /**
     * 转义JSON字符串中的特殊字符
     * @param str 原始字符串
     * @return 转义后的字符串
     */
    private String escapeJsonString(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
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
