package com.nageoffer.shortlink.xunzhi.config.xunfei;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 讯飞AI服务配置类
 * 用于管理讯飞SDK相关的配置参数
 */
@Configuration
@ConfigurationProperties(prefix = "xunfei")
public class XunfeiConfig {

    /**
     * 讯飞应用ID
     */
    private String appId;

    /**
     * 讯飞API Key
     */
    private String apiKey;

    /**
     * 讯飞API Secret
     */
    private String apiSecret;

    /**
     * 实时语音转写API Key
     */
    private String rtaApiKey;

    /**
     * 语音听写配置
     */
    private IatConfig iat = new IatConfig();

    /**
     * 实时语音转写配置
     */
    private RtasrConfig rtasr = new RtasrConfig();

    /**
     * 人脸识别配置
     */
    private FaceConfig face = new FaceConfig();

    // Getters and Setters
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getRtaApiKey() {
        return rtaApiKey;
    }

    public void setRtaApiKey(String rtaApiKey) {
        this.rtaApiKey = rtaApiKey;
    }

    public IatConfig getIat() {
        return iat;
    }

    public void setIat(IatConfig iat) {
        this.iat = iat;
    }

    public RtasrConfig getRtasr() {
        return rtasr;
    }

    public void setRtasr(RtasrConfig rtasr) {
        this.rtasr = rtasr;
    }

    public FaceConfig getFace() {
        return face;
    }

    public void setFace(FaceConfig face) {
        this.face = face;
    }

    /**
     * 语音听写配置
     */
    public static class IatConfig {
        /**
         * 动态修正功能
         */
        private String dwa = "wpgs";

        /**
         * 语言类型
         */
        private String language = "zh_cn";

        /**
         * 应用领域
         */
        private String domain = "iat";

        /**
         * 音频编码
         */
        private String audioEncoding = "raw";

        /**
         * 音频采样率
         */
        private String sampleRate = "16000";

        // Getters and Setters
        public String getDwa() {
            return dwa;
        }

        public void setDwa(String dwa) {
            this.dwa = dwa;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getAudioEncoding() {
            return audioEncoding;
        }

        public void setAudioEncoding(String audioEncoding) {
            this.audioEncoding = audioEncoding;
        }

        public String getSampleRate() {
            return sampleRate;
        }

        public void setSampleRate(String sampleRate) {
            this.sampleRate = sampleRate;
        }
    }

    /**
     * 实时语音转写配置
     */
    public static class RtasrConfig {
        /**
         * 语言类型
         */
        private String language = "zh_cn";

        /**
         * 应用领域
         */
        private String domain = "iat";

        /**
         * 音频编码
         */
        private String audioEncoding = "raw";

        /**
         * 音频采样率
         */
        private String sampleRate = "16000";

        /**
         * 是否开启标点符号添加
         */
        private boolean punctuation = true;

        // Getters and Setters
        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getAudioEncoding() {
            return audioEncoding;
        }

        public void setAudioEncoding(String audioEncoding) {
            this.audioEncoding = audioEncoding;
        }

        public String getSampleRate() {
            return sampleRate;
        }

        public void setSampleRate(String sampleRate) {
            this.sampleRate = sampleRate;
        }

        public boolean isPunctuation() {
            return punctuation;
        }

        public void setPunctuation(boolean punctuation) {
            this.punctuation = punctuation;
        }
    }

    /**
     * 人脸识别配置
     */
    public static class FaceConfig {
        /**
         * 人脸对比阈值
         */
        private double compareThreshold = 0.7;

        /**
         * 图片最大尺寸（字节）
         */
        private long maxImageSize = 5 * 1024 * 1024; // 5MB

        /**
         * 支持的图片格式
         */
        private String[] supportedFormats = {"jpg", "jpeg", "png", "bmp"};

        // Getters and Setters
        public double getCompareThreshold() {
            return compareThreshold;
        }

        public void setCompareThreshold(double compareThreshold) {
            this.compareThreshold = compareThreshold;
        }

        public long getMaxImageSize() {
            return maxImageSize;
        }

        public void setMaxImageSize(long maxImageSize) {
            this.maxImageSize = maxImageSize;
        }

        public String[] getSupportedFormats() {
            return supportedFormats;
        }

        public void setSupportedFormats(String[] supportedFormats) {
            this.supportedFormats = supportedFormats;
        }
    }

    @Override
    public String toString() {
        return "XunfeiConfig{" +
                "appId='" + appId + '\'' +
                ", apiKey='" + (apiKey != null ? "***" : "null") + '\'' +
                ", apiSecret='" + (apiSecret != null ? "***" : "null") + '\'' +
                ", rtaApiKey='" + (rtaApiKey != null ? "***" : "null") + '\'' +
                ", iat=" + iat +
                ", rtasr=" + rtasr +
                ", face=" + face +
                '}';
    }
}