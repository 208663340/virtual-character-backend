package com.hewei.hzyjy.xunzhi.controller;

import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.dto.req.ai.roleplay.TtsSynthesisReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.roleplay.VoiceTrainingUploadReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.roleplay.TtsSynthesisRespDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.roleplay.VoiceTrainingUploadRespDTO;
import com.hewei.hzyjy.xunzhi.toolkit.volcengine.VolcengineClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * AI角色扮演Controller
 * @author nageoffer
 */
@Slf4j
@RestController
@RequestMapping("/api/xunzhi/v1/ai/roleplay")
@RequiredArgsConstructor
public class AiRoleplayController {
    
    private final VolcengineClient volcengineClient;

    /**
     * 上传音色训练音频文件
     */
    @PostMapping("/voice-training/upload")
    public Result<VoiceTrainingUploadRespDTO> uploadVoiceTrainingAudio(
            @RequestParam("audioFiles") MultipartFile[] audioFiles,
            @RequestParam("voiceName") String voiceName,
            @RequestParam(value = "voiceDescription", required = false) String voiceDescription,
            @RequestParam(value = "trainingType", defaultValue = "standard") String trainingType,
            @RequestParam(value = "language", defaultValue = "zh-CN") String language,
            @RequestParam(value = "sessionId", required = false) String sessionId,
            @RequestParam(value = "userId", required = false) Long userId) {
        
        log.info("收到音色训练上传请求，音色名称: {}, 文件数量: {}, 训练类型: {}", 
                voiceName, audioFiles.length, trainingType);
        
        try {
            // 构建请求参数
            VoiceTrainingUploadReqDTO request = new VoiceTrainingUploadReqDTO();
            request.setVoiceName(voiceName);
            request.setVoiceDescription(voiceDescription);
            request.setTrainingType(trainingType);
            request.setLanguage(language);
            request.setSessionId(sessionId);
            request.setUserId(userId);
            
            // 调用火山引擎音色训练API
            VoiceTrainingUploadRespDTO response = volcengineClient.uploadVoiceTrainingAudio(audioFiles, request);
            
            return Results.success(response);
            
        } catch (Exception e) {
            log.error("音色训练上传失败", e);
            return new Result<VoiceTrainingUploadRespDTO>().setCode("500").setMessage("音色训练上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询音色训练状态
     */
    @GetMapping("/voice-training/status/{trainingTaskId}")
    public Result<VoiceTrainingUploadRespDTO> getVoiceTrainingStatus(@PathVariable String trainingTaskId) {
        log.info("查询音色训练状态，taskId: {}", trainingTaskId);
        
        try {
            VoiceTrainingUploadRespDTO response = volcengineClient.getVoiceTrainingStatus(trainingTaskId);
            return Results.success(response);
            
        } catch (Exception e) {
            log.error("查询音色训练状态失败", e);
            return new Result<VoiceTrainingUploadRespDTO>().setCode("500").setMessage("查询音色训练状态失败: " + e.getMessage());
        }
    }

    /**
     * TTS语音合成（直接返回MP3文件流）
     */
    @PostMapping("/tts/synthesis/stream")
    public ResponseEntity<byte[]> ttsSynthesisStream(@RequestBody TtsSynthesisReqDTO request) {
        
        log.info("收到TTS合成流式请求，文本长度: {}, 语音类型: {}, 语速: {}", 
                request.getText() != null ? request.getText().length() : 0, 
                request.getVoiceType(), request.getSpeed());
        
        try {
            // 1. 参数验证
            if (request.getText() == null || request.getText().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("文本内容不能为空".getBytes("UTF-8"));
            }
            
            if (request.getText().length() > 1000) {
                return ResponseEntity.badRequest()
                        .body("文本长度不能超过1000字符".getBytes("UTF-8"));
            }
            
            // 2. 调用火山引擎TTS服务，直接获取音频数据
            byte[] audioData = volcengineClient.ttsSynthesisRaw(request);
            
            // 3. 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
            headers.setContentLength(audioData.length);
            headers.set("Content-Disposition", "inline; filename=\"tts_audio.mp3\"");
            headers.set("Cache-Control", "no-cache");
            
            log.info("TTS合成流式响应成功，音频大小: {} bytes", audioData.length);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(audioData);
            
        } catch (Exception e) {
            log.error("TTS语音合成流式响应失败", e);
            try {
                return ResponseEntity.internalServerError()
                        .body(("TTS语音合成失败: " + e.getMessage()).getBytes("UTF-8"));
            } catch (Exception ex) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    /**
     * TTS语音合成
     */
    @PostMapping("/tts/synthesis")
    public Result<TtsSynthesisRespDTO> ttsSynthesis(@RequestBody TtsSynthesisReqDTO request) {
        
        log.info("收到TTS合成请求，文本长度: {}, 语音类型: {}, 语速: {}", 
                request.getText() != null ? request.getText().length() : 0, 
                request.getVoiceType(), request.getSpeed());
        
        try {
            // 1. 参数验证
            if (request.getText() == null || request.getText().trim().isEmpty()) {
                return new Result<TtsSynthesisRespDTO>().setCode("400").setMessage("文本内容不能为空");
            }
            
            if (request.getText().length() > 1000) {
                return new Result<TtsSynthesisRespDTO>().setCode("400").setMessage("文本长度不能超过1000字符");
            }
            
            // 2. 调用火山引擎TTS服务
            TtsSynthesisRespDTO response = volcengineClient.ttsSynthesis(request);
            
            return Results.success(response);
            
        } catch (Exception e) {
            log.error("TTS语音合成失败", e);
            return new Result<TtsSynthesisRespDTO>().setCode("500").setMessage("TTS语音合成失败: " + e.getMessage());
        }
    }


    /**
     * 获取支持的语音类型
     */
    @GetMapping("/tts/voices")
    public Result<String[]> getSupportedVoices() {
        
        log.info("获取支持的语音类型");
        
        // 从配置中读取支持的语音类型（基于官方示例）
        String[] voices = {"BV001", "BV002", "BV003", "BV004"};
        
        return Results.success(voices);
    }
    
    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public Result<String> health() {
        return Results.success("AI角色扮演服务运行正常");
    }
    
}
