package com.hewei.hzyjy.xunzhi.controller;

import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.common.util.FileUploadUtil;
import com.hewei.hzyjy.xunzhi.dto.resp.file.FileUploadResult;
import com.hewei.hzyjy.xunzhi.dto.resp.xunfei.AudioTranscriptionRespDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.xunfei.ExpressionRecognitionResult;
import com.hewei.hzyjy.xunzhi.service.AudioTranscriptionService;
import com.hewei.hzyjy.xunzhi.toolkit.xunfei.XingChenAIClient;
import com.hewei.hzyjy.xunzhi.service.tool.XunfeiAudioService;
import com.hewei.hzyjy.xunzhi.service.tool.XunfeiFaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 讯飞AI功能控制器
 * 提供音频转文字和表情识别的REST API接口
 */
@Slf4j
@RestController
@RequestMapping("/api/xunzhi/v1/xunfei")
@RequiredArgsConstructor
public class XunfeiController {

    private final XunfeiAudioService xunfeiAudioService;

    private final XunfeiFaceService xunfeiFaceService;
    
    private final AudioTranscriptionService audioTranscriptionService;
    
    private final XingChenAIClient xingChenAIClient;

    /**
     * 音频转文字接口（已弃用）
     * @deprecated 请使用WebSocket接口进行实时语音转写
     * WebSocket地址: ws://localhost:8080/api/xunzhi/v1/xunfei/audio-to-text
     */
    @Deprecated
    @PostMapping("/audio-to-text")
    public Result<CompletableFuture<String>> convertAudioToText(
            @RequestParam("file") MultipartFile audioFile) {
        return Results.success(xunfeiAudioService.convertAudioToText(audioFile));
    }


    /**
     * 表情识别
     * @param image 人脸图片
     * @return 表情识别结果
     */
    @PostMapping("/expression-recognition")
    public Result<CompletableFuture<ExpressionRecognitionResult>> detectExpression(
            @RequestParam("image") MultipartFile image) {
        return Results.success(xunfeiFaceService.detectExpression(image));
    }

    /**
     * 音频转文字（同步）
     * @param audioFile 音频文件
     * @return 转换结果
     */
    @PostMapping("/audio-transcribe")
    public Result<AudioTranscriptionRespDTO> transcribeAudio(
            @RequestParam("file") MultipartFile audioFile) {
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 使用同步转写，不需要中间结果回调
            String transcriptionText = audioTranscriptionService.transcribeSync(audioFile, null);
            
            long endTime = System.currentTimeMillis();
            
            // 构建响应DTO
            AudioTranscriptionRespDTO respDTO = new AudioTranscriptionRespDTO();
            respDTO.setSuccess(true);
            respDTO.setTranscriptionText(transcriptionText);
            respDTO.setAudioFileSize(audioFile.getSize());
            respDTO.setAudioFormat(getAudioFormat(audioFile.getOriginalFilename()));
            respDTO.setTranscriptionStartTime(startTime);
            respDTO.setTranscriptionEndTime(endTime);
            respDTO.setTranscriptionDuration(endTime - startTime);
            respDTO.setLanguage("zh-CN");
            respDTO.setOriginalFileName(audioFile.getOriginalFilename());
            respDTO.setRequestId(generateRequestId());
            
            return Results.success(respDTO);
        } catch (Exception e) {
            log.error("音频转写失败", e);
            
            // 构建失败响应DTO
            AudioTranscriptionRespDTO errorRespDTO = new AudioTranscriptionRespDTO();
            errorRespDTO.setSuccess(false);
            errorRespDTO.setErrorMessage(e.getMessage());
            errorRespDTO.setOriginalFileName(audioFile.getOriginalFilename());
            errorRespDTO.setAudioFileSize(audioFile.getSize());
            errorRespDTO.setRequestId(generateRequestId());
            
            return Results.success(errorRespDTO);
        }
    }
    
    /**
     * 生成请求ID
     */
    private String generateRequestId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 根据文件名获取音频格式
     */
    private String getAudioFormat(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "unknown";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }


    /**
     * PDF文件上传
     * @param file PDF文件（限制：只能是PDF格式，大小不超过20MB）
     * @return 文件上传结果
     */
    @PostMapping("/upload-pdf")
    public Result<FileUploadResult> uploadPdfFile(
            @RequestParam("file") MultipartFile file) {
        // 使用工具类验证PDF文件
        FileUploadResult result = FileUploadUtil.validateAndProcessFile(file, FileUploadUtil.FileType.PDF);
        return Results.success(result);
    }

}