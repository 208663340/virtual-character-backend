package com.hewei.hzyjy.xunzhi.config.doubao;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 豆包大模型配置属性
 * 
 * @author hewei
 */
@Data
@Component
@ConfigurationProperties(prefix = "doubao")
public class DoubaoProperties {
    
    /**
     * API密钥
     */
    private String apiKey;
    
    /**
     * 密钥
     */
    private String secretKey;
    
    /**
     * 地域
     */
    private String region = "cn-beijing";
    
    /**
     * 模型ID
     */
    private String modelId = "doubao-lite-32k-240828";
    
    /**
     * API基础URL
     */
    private String baseUrl = "https://ark.cn-beijing.volces.com/api/v3";
    
    /**
     * 超时时间（秒）
     */
    private Integer timeout = 1800;
    
    /**
     * 连接超时时间（秒）
     */
    private Integer connectTimeout = 20;
    
    /**
     * 重试次数
     */
    private Integer retryTimes = 2;
}
