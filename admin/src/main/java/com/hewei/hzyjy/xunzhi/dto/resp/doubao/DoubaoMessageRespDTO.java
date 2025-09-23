package com.hewei.hzyjy.xunzhi.dto.resp.doubao;

import lombok.Builder;
import lombok.Data;

/**
 * 豆包大模型对话响应DTO
 * 
 * @author hewei
 */
@Data
@Builder
public class DoubaoMessageRespDTO {
    
    /**
     * 模型回复内容
     */
    private String message;
    
    /**
     * 使用的模型ID
     */
    private String modelId;
    
    /**
     * 响应时间戳
     */
    private Long timestamp;
}
