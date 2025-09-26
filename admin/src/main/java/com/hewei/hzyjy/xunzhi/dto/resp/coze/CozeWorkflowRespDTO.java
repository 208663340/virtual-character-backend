package com.hewei.hzyjy.xunzhi.dto.resp.coze;

import lombok.Data;

import java.util.Map;

/**
 * Coze工作流执行响应DTO
 * @author nageoffer
 */
@Data
public class CozeWorkflowRespDTO {
    
    /**
     * 执行ID（异步执行时返回）
     */
    private String executeId;
    
    /**
     * 执行状态
     */
    private String status;
    
    /**
     * 工作流ID
     */
    private String workflowId;
    
    /**
     * 执行结果
     */
    private Map<String, Object> result;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 执行开始时间
     */
    private Long startTime;
    
    /**
     * 执行结束时间
     */
    private Long endTime;
    
    /**
     * 执行耗时（毫秒）
     */
    private Long duration;
    
    /**
     * 是否执行成功
     */
    private Boolean success;
}

