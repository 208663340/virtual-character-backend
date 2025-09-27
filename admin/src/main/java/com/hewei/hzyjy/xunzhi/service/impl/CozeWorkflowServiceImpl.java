package com.hewei.hzyjy.xunzhi.service.impl;

import com.hewei.hzyjy.xunzhi.dto.req.coze.CozeWorkflowStreamReqDTO;
import com.hewei.hzyjy.xunzhi.service.CozeWorkflowService;
import com.hewei.hzyjy.xunzhi.toolkit.coze.CozeClient;
import com.hewei.hzyjy.xunzhi.toolkit.xunfei.AIContentAccumulator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Coze工作流服务实现类
 * @author nageoffer
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CozeWorkflowServiceImpl implements CozeWorkflowService {

    private final CozeClient cozeClient;

    @Override
    public Flux<String> runWorkflowStream(String workflowId, CozeWorkflowStreamReqDTO requestParam) {
        log.info("开始执行工作流流式处理，workflowId: {}, userInput: {}", workflowId, requestParam.getUserInput());
        
        return Flux.create(sink -> {
            try {
                // 将DTO转换为工作流参数
                Map<String, Object> parameters = requestParam.toWorkflowParameters();
                
                // 内容累积器
                AIContentAccumulator accumulator = new AIContentAccumulator();
                
                // 调用CozeClient的SSE流式执行
                cozeClient.runWorkflowSSE(
                    workflowId,
                    parameters,
                    new OutputStream() {
                        @Override
                        public void write(int b) throws IOException {
                            // 单字节写入
                            String data = String.valueOf((char) b);
                            accumulator.append(data);
                            sink.next(data);
                        }
                        
                        @Override
                        public void write(byte[] b, int off, int len) throws IOException {
                            // 字节数组写入
                            String data = new String(b, off, len, java.nio.charset.StandardCharsets.UTF_8);
                            accumulator.append(data);
                            sink.next(data);
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
                log.error("Coze工作流SSE执行失败，workflowId: {}, userInput: {}", workflowId, requestParam.getUserInput(), e);
                sink.next("错误: " + e.getMessage());
                sink.error(e);
            }
        });
    }

    @Override
    public Flux<String> runWorkflowStreamSimple(String workflowId, String message) {
        log.info("开始执行简单工作流流式处理，workflowId: {}, message: {}", workflowId, message);
        
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
                            // 单字节写入
                            String data = String.valueOf((char) b);
                            accumulator.append(data);
                            sink.next(data);
                        }
                        
                        @Override
                        public void write(byte[] b, int off, int len) throws IOException {
                            // 字节数组写入
                            String data = new String(b, off, len, java.nio.charset.StandardCharsets.UTF_8);
                            accumulator.append(data);
                            sink.next(data);
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

    @Override
    public boolean healthCheck() {
        try {
            return cozeClient.healthCheck();
        } catch (Exception e) {
            log.error("Coze工作流服务健康检查失败", e);
            return false;
        }
    }
}