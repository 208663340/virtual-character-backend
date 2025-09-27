package com.hewei.hzyjy.xunzhi.service.impl;

import com.hewei.hzyjy.xunzhi.dto.req.coze.CozeWorkflowStreamReqDTO;
import com.hewei.hzyjy.xunzhi.service.CozeWorkflowService;
import com.hewei.hzyjy.xunzhi.toolkit.coze.CozeClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
        
        // 将DTO转换为工作流参数
        Map<String, Object> parameters = requestParam.toWorkflowParameters();
        log.info("[Service层] 转换后的参数: {}", parameters);
        
        // 使用新的响应式流式方法
        return cozeClient.runWorkflowStreamReactive(workflowId, parameters)
                .doOnNext(data -> {
                    log.info("[Service SSE数据] workflowId: {}, data: {}", workflowId, data);
                })
                .doOnComplete(() -> {
                    log.info("[Service SSE完成] workflowId: {}", workflowId);
                })
                .doOnError(error -> {
                    log.error("[Service SSE错误] workflowId: {}, error: {}", workflowId, error.getMessage(), error);
                });
    }

    @Override
    public Flux<String> runWorkflowStreamSimple(String workflowId, String message) {
        log.info("开始执行简单工作流流式处理，workflowId: {}, message: {}", workflowId, message);
        
        // 构造简单参数
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", message);
        log.info("[Service层Simple] 构造的参数: {}", parameters);
        
        // 使用新的响应式流式方法
        return cozeClient.runWorkflowStreamReactive(workflowId, parameters)
                .doOnNext(data -> {
                    log.info("[Service Simple SSE数据] workflowId: {}, data: {}", workflowId, data);
                })
                .doOnComplete(() -> {
                    log.info("[Service Simple SSE完成] workflowId: {}", workflowId);
                })
                .doOnError(error -> {
                    log.error("[Service Simple SSE错误] workflowId: {}, error: {}", workflowId, error.getMessage(), error);
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