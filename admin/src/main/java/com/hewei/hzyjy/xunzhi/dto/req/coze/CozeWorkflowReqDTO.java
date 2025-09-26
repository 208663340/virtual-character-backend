package com.hewei.hzyjy.xunzhi.dto.req.coze;

import lombok.Data;

import java.util.Map;

/**
 * Coze工作流执行请求DTO
 * @author nageoffer
 */
@Data
public class CozeWorkflowReqDTO {
    
    /**
     * 工作流ID
     */
    private String workflowId;
    
    /**
     * 输入参数
     */
    private Map<String, Object> parameters;
    
    /**
     * 是否异步执行
     */
    private Boolean async = false;
    
    /**
     * Bot ID（可选）
     */
    private String botId;
    
    /**
     * 是否启用流式返回
     */
    private Boolean stream = false;
}

