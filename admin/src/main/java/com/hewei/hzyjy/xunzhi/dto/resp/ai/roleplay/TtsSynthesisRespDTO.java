package com.hewei.hzyjy.xunzhi.dto.resp.ai.roleplay;

import lombok.Data;

/**
 * TTS语音合成响应DTO
 * @author nageoffer
 */
@Data
public class TtsSynthesisRespDTO {
    
    /**
     * 音频文件ID
     */
    private String audioId;
    
    /**
     * 音频文件URL
     */
    private String audioUrl;
    
    /**
     * 音频时长（毫秒）
     */
    private Long duration;
    
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    
    /**
     * 音频格式
     */
    private String audioFormat;
    
    /**
     * 采样率
     */
    private Integer sampleRate;
    
    /**
     * 合成时间戳
     */
    private Long synthesisTime;
    
    /**
     * 使用的语音类型
     */
    private String voiceType;
    
    /**
     * 实际使用的语速
     */
    private Double actualSpeed;
    
    /**
     * 实际使用的音调
     */
    private Double actualPitch;
    
    /**
     * 实际使用的音量
     */
    private Double actualVolume;
}
