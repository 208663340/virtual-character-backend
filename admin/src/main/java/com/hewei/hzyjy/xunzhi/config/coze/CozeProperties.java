package com.hewei.hzyjy.xunzhi.config.coze;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Coze配置属性
 * @author nageoffer
 */
@Data
@Component
@ConfigurationProperties(prefix = "coze")
public class CozeProperties {
    
    /**
     * API密钥
     */
    private String apiKey;
    
    /**
     * API基础URL
     */
    private String baseUrl = "https://api.coze.cn";
    
    /**
     * 超时时间（毫秒）
     */
    private Long timeout = 30000L;
    
    /**
     * 连接超时时间（毫秒）
     */
    private Long connectTimeout = 10000L;
    
    /**
     * 读取超时时间（毫秒）
     */
    private Long readTimeout = 30000L;
    
    /**
     * 重试次数
     */
    private Integer retryTimes = 2;
    
    /**
     * 工作流配置
     */
    private Workflow workflow = new Workflow();
    
    @Data
    public static class Workflow {
        /**
         * 默认工作流ID
         */
        private String defaultWorkflowId;
        
        /**
         * 是否启用异步执行
         */
        private Boolean enableAsync = true;
        
        /**
         * 最大并发数
         */
        private Integer maxConcurrency = 10;
    }
}

