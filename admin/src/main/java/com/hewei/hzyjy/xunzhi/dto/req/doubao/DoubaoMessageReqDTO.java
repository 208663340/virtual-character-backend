package com.hewei.hzyjy.xunzhi.dto.req.doubao;

import lombok.Data;

/**
 * 豆包大模型对话请求DTO
 * 
 * @author hewei
 */
@Data
public class DoubaoMessageReqDTO {
    
    /**
     * 用户消息内容
     */
    private String message;
    
    /**
     * 系统提示（可选）
     */
    private String systemPrompt;
}
