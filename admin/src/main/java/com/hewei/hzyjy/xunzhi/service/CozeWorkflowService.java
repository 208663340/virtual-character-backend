package com.hewei.hzyjy.xunzhi.service;

import com.hewei.hzyjy.xunzhi.dto.req.coze.CozeWorkflowStreamReqDTO;
import reactor.core.publisher.Flux;

/**
 * Coze工作流服务接口
 * @author nageoffer
 */
public interface CozeWorkflowService {
    
    /**
     * 流式执行工作流
     *
     * @param workflowId   工作流ID
     * @param requestParam 请求参数DTO
     * @return 流式响应
     */
    Flux<String> runWorkflowStream(String workflowId, CozeWorkflowStreamReqDTO requestParam);
    
    /**
     * 简单流式执行工作流
     *
     * @param workflowId 工作流ID
     * @param message    消息内容
     * @return 流式响应
     */
    Flux<String> runWorkflowStreamSimple(String workflowId, String message);
    
    /**
     * 健康检查
     * @return 是否健康
     */
    boolean healthCheck();
}