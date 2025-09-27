package com.hewei.hzyjy.xunzhi.dto.req.coze;

import lombok.Data;


import java.util.Map;

/**
 * Coze工作流流式执行请求DTO
 * @author nageoffer
 */
@Data
public class CozeWorkflowStreamReqDTO {
    
    /**
     * 用户输入内容
     */
    private String userInput;
    
    /**
     * 会话ID（可选，用于上下文关联）
     */
    private String conversationName;
    
    /**
     * 用户ID（可选，用于用户关联）
     */
    private String userId;
    
    /**
     * 额外参数（可选，用于传递其他工作流参数）
     */
    private Map<String, Object> extraParams;
    
    /**
     * 是否启用调试模式
     */
    private Boolean debug = false;
    
    /**
     * 超时时间（秒），默认30秒
     */
    private Integer timeout = 30;
    
    /**
     * 语言设置（可选）
     */
    private String language = "zh-CN";
    
    /**
     * 转换为工作流执行参数Map
     */
    public Map<String, Object> toWorkflowParameters() {
        Map<String, Object> parameters = new java.util.HashMap<>();
        
        // 主要的用户输入参数
        parameters.put("USER_INPUT", this.userInput);
        
        // 可选参数
        if (this.conversationName != null) {
            parameters.put("Conversation_name", this.conversationName);
        }
        
        if (this.userId != null) {
            parameters.put("USER_ID", this.userId);
        }
        
        if (this.language != null) {
            parameters.put("LANGUAGE", this.language);
        }
        
        // 合并额外参数
        if (this.extraParams != null && !this.extraParams.isEmpty()) {
            parameters.putAll(this.extraParams);
        }
        
        return parameters;
    }
}