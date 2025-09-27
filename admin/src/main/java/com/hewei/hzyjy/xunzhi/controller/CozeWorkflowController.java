package com.hewei.hzyjy.xunzhi.controller;

import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.toolkit.coze.CozeClient;
import com.hewei.hzyjy.xunzhi.toolkit.xunfei.AIContentAccumulator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.OutputStream;
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
     * SSE流式执行工作流
     * @param workflowId 工作流ID
     * @param parameters 执行参数（可选）
     */
    @PostMapping(value = "/workflow/{workflowId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> runWorkflowStream(
            @PathVariable String workflowId,
            @RequestBody(required = false) Map<String, Object> parameters) {
        
        log.info("收到工作流SSE流式执行请求，workflowId: {}", workflowId);
        
        return Flux.create(sink -> {
            try {
                // 如果没有传参数，使用空Map
                if (parameters == null) {
                    parameters = new HashMap<>();
                }
                
                // 内容累积器
                AIContentAccumulator accumulator = new AIContentAccumulator();
                
                // 调用CozeClient的SSE流式执行
                cozeClient.runWorkflowSSE(
                    workflowId,
                    parameters,
                    new OutputStream() {
                        @Override
                        public void write(int b) throws IOException {
                            // 不需要实现
                        }

                        @Override
                        public void write(byte[] b, int off, int len) throws IOException {
                            try {
                                // 发送数据到前端
                                String jsonChunk = new String(b, off, len);
                                sink.next(jsonChunk);
                                
                                // 累积内容到字符串
                                accumulator.appendChunk(b);
                                
                            } catch (Exception e) {
                                log.error("Coze SSE数据发送失败", e);
                                sink.error(e);
                            }
                        }

                        @Override
                        public void flush() throws IOException {
                            // 确保数据发送
                        }
                    },
                    data -> {
                        // 回调函数，处理接收到的数据
                        if (data != null && !data.trim().isEmpty()) {
                            log.debug("[Coze SSE数据接收] {}", data);
                        }
                    }
                );
                
                sink.next("[DONE]");
                sink.complete();
                
            } catch (Exception e) {
                log.error("Coze工作流SSE执行失败，workflowId: {}", workflowId, e);
                sink.next("错误: " + e.getMessage());
                sink.error(e);
            }
        });
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
        
        return Flux.create(sink -> {
            try {
                // 构造简单参数
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("message", message);
                
                // 内容累积器
                AIContentAccumulator accumulator = new AIContentAccumulator();
                
                // 调用CozeClient的SSE流式执行
                cozeClient.runWorkflowSSE(
                    workflowId,
                    parameters,
                    new OutputStream() {
                        @Override
                        public void write(int b) throws IOException {
                            // 不需要实现
                        }

                        @Override
                        public void write(byte[] b, int off, int len) throws IOException {
                            try {
                                // 发送数据到前端
                                String jsonChunk = new String(b, off, len);
                                sink.next(jsonChunk);
                                
                                // 累积内容到字符串
                                accumulator.appendChunk(b);
                                
                            } catch (Exception e) {
                                log.error("Coze SSE数据发送失败", e);
                                sink.error(e);
                            }
                        }

                        @Override
                        public void flush() throws IOException {
                            // 确保数据发送
                        }
                    },
                    data -> {
                        // 回调函数，处理接收到的数据
                        if (data != null && !data.trim().isEmpty()) {
                            log.debug("[Coze SSE数据接收] {}", data);
                        }
                    }
                );
                
                sink.next("[DONE]");
                sink.complete();
                
            } catch (Exception e) {
                log.error("简单Coze工作流SSE执行失败，workflowId: {}", workflowId, e);
                sink.next("错误: " + e.getMessage());
                sink.error(e);
            }
        });
    }
}