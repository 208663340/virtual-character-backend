package com.hewei.hzyjy.xunzhi.config.volcengine;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 火山引擎配置属性
 * @author nageoffer
 */
@Data
@Component
@ConfigurationProperties(prefix = "volcengine")
public class VolcengineProperties {
    
    /**
     * App ID（对应官方示例中的APP_ID）
     */
    private String apiKey;
    
    /**
     * Access Token（对应官方示例中的ACCESS_TOKEN）
     */
    private String accessKey;
    
    /**
     * Cluster（对应官方示例中的CLUSTER）
     */
    private String region = "cn-north-1";
    
    /**
     * TTS服务配置
     */
    private Tts tts = new Tts();
    
    
    /**
     * 音色训练服务配置
     */
    private VoiceTraining voiceTraining = new VoiceTraining();
    
    @Data
    public static class Tts {
        /**
         * 默认语音类型（基于官方示例）
         */
        private String defaultVoice = "BV001";
        
        /**
         * 默认音频格式
         */
        private String defaultFormat = "mp3";
        
        /**
         * 默认语速
         */
        private Double defaultSpeed = 1.0;
        
        /**
         * 默认音调
         */
        private Double defaultPitch = 1.0;
        
        /**
         * 默认音量
         */
        private Double defaultVolume = 1.0;
        
        /**
         * 默认情感
         */
        private String defaultEmotion = "happy";
    }
    
    
    @Data
    public static class VoiceTraining {
        /**
         * 音色训练API地址
         */
        private String baseUrl = "https://openspeech.bytedance.com/api/v1/voice_training";
        
        /**
         * 最小音频文件数量
         */
        private Integer minAudioFiles = 10;
        
        /**
         * 最大音频文件数量
         */
        private Integer maxAudioFiles = 100;
        
        /**
         * 单个音频文件最大大小（字节）
         */
        private Long maxFileSize = 10 * 1024 * 1024L; // 10MB
        
        /**
         * 支持的音频格式
         */
        private String[] supportedFormats = {"wav", "mp3"};
        
        /**
         * 支持的训练类型
         */
        private String[] supportedTrainingTypes = {"standard", "premium"};
        
        /**
         * 支持的语言
         */
        private String[] supportedLanguages = {"zh-CN", "en-US"};
        
        /**
         * 默认训练时长（分钟）
         */
        private Integer defaultTrainingDuration = 30;
    }
}
