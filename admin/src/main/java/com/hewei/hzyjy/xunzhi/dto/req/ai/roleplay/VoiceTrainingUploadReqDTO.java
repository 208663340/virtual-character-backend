package com.hewei.hzyjy.xunzhi.dto.req.ai.roleplay;

import lombok.Data;

/**
 * 音色训练上传请求DTO
 * @author nageoffer
 */
@Data
public class VoiceTrainingUploadReqDTO {
    
    /**
     * 音色名称
     */
    private String voiceName;
    
    /**
     * 音色描述
     */
    private String voiceDescription;
    
    /**
     * 训练类型：standard（标准）、premium（高级）
     */
    private String trainingType;
    
    /**
     * 语言类型：zh-CN、en-US等
     */
    private String language;
    
    /**
     * 会话ID
     */
    private String sessionId;
    
    /**
     * 用户ID
     */
    private Long userId;
}
