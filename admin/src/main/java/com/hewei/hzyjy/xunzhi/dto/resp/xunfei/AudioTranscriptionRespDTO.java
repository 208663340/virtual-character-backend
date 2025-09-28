package com.hewei.hzyjy.xunzhi.dto.resp.xunfei;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 音频转文字响应DTO
 * @author nageoffer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioTranscriptionRespDTO {
    
    /**
     * 转写是否成功
     */
    private Boolean success;
    
    /**
     * 转写结果文本
     */
    private String transcriptionText;
    
    /**
     * 音频时长（毫秒）
     */
    private Long audioDuration;
    
    /**
     * 音频文件大小（字节）
     */
    private Long audioFileSize;
    
    /**
     * 音频格式
     */
    private String audioFormat;
    
    /**
     * 转写开始时间戳
     */
    private Long transcriptionStartTime;
    
    /**
     * 转写结束时间戳
     */
    private Long transcriptionEndTime;
    
    /**
     * 转写耗时（毫秒）
     */
    private Long transcriptionDuration;
    
    /**
     * 置信度（0.0-1.0）
     */
    private Double confidence;
    
    /**
     * 语言类型
     */
    private String language;
    
    /**
     * 错误信息（转写失败时）
     */
    private String errorMessage;
    
    /**
     * 请求ID
     */
    private String requestId;
    
    /**
     * 原始音频文件名
     */
    private String originalFileName;

    /**
     * 判断转写是否成功
     */
    public boolean isSuccess() {
        return success != null && success;
    }
}