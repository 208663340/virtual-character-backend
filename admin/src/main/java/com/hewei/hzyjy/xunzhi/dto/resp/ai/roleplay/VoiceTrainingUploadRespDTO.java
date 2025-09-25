package com.hewei.hzyjy.xunzhi.dto.resp.ai.roleplay;

import lombok.Data;

/**
 * 音色训练上传响应DTO
 * @author nageoffer
 */
@Data
public class VoiceTrainingUploadRespDTO {
    
    /**
     * 训练任务ID
     */
    private String trainingTaskId;
    
    /**
     * 音色名称
     */
    private String voiceName;
    
    /**
     * 训练状态：uploading（上传中）、training（训练中）、completed（完成）、failed（失败）
     */
    private String trainingStatus;
    
    /**
     * 上传的音频文件数量
     */
    private Integer audioFileCount;
    
    /**
     * 总音频时长（秒）
     */
    private Long totalDuration;
    
    /**
     * 训练开始时间
     */
    private Long trainingStartTime;
    
    /**
     * 预计完成时间
     */
    private Long estimatedCompletionTime;
    
    /**
     * 训练进度（0-100）
     */
    private Integer progress;
    
    /**
     * 错误信息（如果训练失败）
     */
    private String errorMessage;
    
    /**
     * 生成的音色ID（训练完成后）
     */
    private String voiceId;
}
