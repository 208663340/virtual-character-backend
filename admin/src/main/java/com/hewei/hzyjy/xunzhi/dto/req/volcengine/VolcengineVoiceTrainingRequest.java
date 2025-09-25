package com.hewei.hzyjy.xunzhi.dto.req.volcengine;

import lombok.Data;

import java.util.UUID;

/**
 * 火山引擎音色训练请求结构
 * @author nageoffer
 */
@Data
public class VolcengineVoiceTrainingRequest {
    
    private App app = new App();
    private User user = new User();
    private Training training = new Training();
    private Request request = new Request();
    
    public VolcengineVoiceTrainingRequest() {
    }
    
    public VolcengineVoiceTrainingRequest(String voiceName, String trainingType) {
        this.training.voice_name = voiceName;
        this.training.training_type = trainingType;
    }
    
    @Data
    public static class App {
        private String appid;
        private String token = "access_token";
        private String cluster;
    }
    
    @Data
    public static class User {
        private String uid = "388808087185088";
    }
    
    @Data
    public static class Training {
        private String voice_name;
        private String training_type = "standard"; // standard, premium
        private String language = "zh-CN";
        private String description;
        private String[] audio_urls; // 音频文件URL列表
    }
    
    @Data
    public static class Request {
        private String reqid = UUID.randomUUID().toString();
        private String operation = "create_training_task";
    }
}
