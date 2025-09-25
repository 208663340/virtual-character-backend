package com.hewei.hzyjy.xunzhi.dto.req.ai.roleplay;

import lombok.Data;

/**
 * TTS语音合成请求DTO
 * @author nageoffer
 */
@Data
public class TtsSynthesisReqDTO {
    
    /**
     * 要合成的文本内容
     */
    private String text;
    
    /**
     * 语音类型：xiaoyun、xiaogang、xiaomei等
     */
    private String voiceType;
    
    /**
     * 语速：0.5-2.0，默认1.0
     */
    private Double speed;
    
    /**
     * 音调：0.5-2.0，默认1.0
     */
    private Double pitch;
    
    /**
     * 音量：0.0-1.0，默认1.0
     */
    private Double volume;
    
    /**
     * 音频格式：mp3、wav、pcm等
     */
    private String audioFormat;
    
    /**
     * 采样率：8000、16000、44100等
     */
    private Integer sampleRate;
    
    /**
     * 会话ID
     */
    private String sessionId;
    
    /**
     * 用户ID
     */
    private Long userId;
}
