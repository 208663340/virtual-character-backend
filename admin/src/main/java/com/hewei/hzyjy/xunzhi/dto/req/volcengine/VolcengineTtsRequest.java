package com.hewei.hzyjy.xunzhi.dto.req.volcengine;

import lombok.Data;

import java.util.UUID;

/**
 * 火山引擎TTS请求结构（基于官方示例）
 * @author nageoffer
 */
@Data
public class VolcengineTtsRequest {
    
    private App app = new App();
    private User user = new User();
    private Audio audio = new Audio();
    private Request request = new Request();
    
    public VolcengineTtsRequest() {
    }
    
    public VolcengineTtsRequest(String text) {
        this.request.text = text;
    }
    
    @Data
    public static class App {
        private String appid;
        private String token = "access_token"; // 目前未生效，填写默认值
        private String cluster;
    }
    
    @Data
    public static class User {
        private String uid = "388808087185088"; // 目前未生效，填写一个默认值就可以
    }
    
    @Data
    public static class Audio {
        private String voice_type = "BV001";
        private String encoding = "mp3";
        private float speed_ratio = 1.0f;
        private float volume_ratio = 10.0f;
        private float pitch_ratio = 10.0f;
        private String emotion = "happy";
    }
    
    @Data
    public static class Request {
        private String reqid = UUID.randomUUID().toString();
        private String text;
        private String text_type = "plain";
        private String operation = "query";
    }
}
