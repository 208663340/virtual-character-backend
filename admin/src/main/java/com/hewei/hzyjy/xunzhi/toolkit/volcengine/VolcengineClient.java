package com.hewei.hzyjy.xunzhi.toolkit.volcengine;

import com.alibaba.fastjson.JSON;
import com.hewei.hzyjy.xunzhi.config.volcengine.VolcengineProperties;
import com.hewei.hzyjy.xunzhi.dto.req.ai.roleplay.TtsSynthesisReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.roleplay.VoiceTrainingUploadReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.volcengine.VolcengineTtsRequest;
import com.hewei.hzyjy.xunzhi.dto.req.volcengine.VolcengineVoiceTrainingRequest;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.roleplay.TtsSynthesisRespDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.roleplay.VoiceTrainingUploadRespDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.UUID;

/**
 * 火山引擎API客户端
 * @author nageoffer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VolcengineClient {
    
    private final VolcengineProperties properties;
    private final OkHttpClient httpClient = new OkHttpClient();
    
    
    // 火山引擎API常量
    private static final String TTS_HOST = "openspeech.bytedance.com";
    private static final String TTS_API_URL = "https://" + TTS_HOST + "/api/v1/tts";
    private static final String VOICE_TRAINING_API_URL = "https://" + TTS_HOST + "/api/v1/voice_training";
    
    /**
     * 上传音色训练音频文件
     */
    public VoiceTrainingUploadRespDTO uploadVoiceTrainingAudio(
            MultipartFile[] audioFiles, 
            VoiceTrainingUploadReqDTO request) throws Exception {
        
        log.info("开始上传音色训练音频，音色名称: {}, 文件数量: {}", 
                request.getVoiceName(), audioFiles.length);
        
        
        // 1. 验证音频文件
        validateTrainingAudioFiles(audioFiles);
        
        // 2. 上传音频文件到火山引擎
        String[] audioUrls = uploadTrainingAudioFiles(audioFiles);
        
        // 3. 创建音色训练任务
        String trainingTaskId = createVoiceTrainingTask(request, audioUrls);
        
        // 4. 构建响应
        VoiceTrainingUploadRespDTO response = new VoiceTrainingUploadRespDTO();
        response.setTrainingTaskId(trainingTaskId);
        response.setVoiceName(request.getVoiceName());
        response.setTrainingStatus("uploading");
        response.setAudioFileCount(audioFiles.length);
        response.setTotalDuration(calculateTotalDuration(audioFiles));
        response.setTrainingStartTime(System.currentTimeMillis());
        response.setEstimatedCompletionTime(System.currentTimeMillis() + 30 * 60 * 1000L); // 30分钟后
        response.setProgress(0);
        
        log.info("音色训练任务创建成功，taskId: {}", trainingTaskId);
        return response;
    }
    
    /**
     * 获取音色训练状态
     */
    public VoiceTrainingUploadRespDTO getVoiceTrainingStatus(String trainingTaskId) throws Exception {
        log.info("查询音色训练状态，taskId: {}", trainingTaskId);
        
        
        // 调用火山引擎API查询训练状态
        String responseJson = queryTrainingStatus(trainingTaskId);
        
        // 解析响应
        VoiceTrainingUploadRespDTO response = parseTrainingStatusResponse(responseJson);
        
        log.info("音色训练状态查询完成，状态: {}, 进度: {}%", 
                response.getTrainingStatus(), response.getProgress());
        return response;
    }
    
    /**
     * TTS语音合成（基于火山引擎官方API格式）
     */
    public TtsSynthesisRespDTO ttsSynthesis(TtsSynthesisReqDTO request) throws Exception {
        log.info("开始TTS语音合成，文本长度: {}, 语音类型: {}", 
                request.getText().length(), request.getVoiceType());
        
        
        // 构建火山引擎TTS请求
        VolcengineTtsRequest ttsRequest = new VolcengineTtsRequest(request.getText());
        
        // 设置App信息
        ttsRequest.getApp().setAppid(properties.getApiKey());
        ttsRequest.getApp().setCluster(properties.getRegion());
        
        // 设置音频参数
        VolcengineTtsRequest.Audio audio = ttsRequest.getAudio();
        audio.setVoice_type(request.getVoiceType() != null ? request.getVoiceType() : "BV001");
        audio.setEncoding(request.getAudioFormat() != null ? request.getAudioFormat() : "mp3");
        audio.setSpeed_ratio(request.getSpeed() != null ? request.getSpeed().floatValue() : 1.0f);
        audio.setVolume_ratio(request.getVolume() != null ? request.getVolume().floatValue() * 10 : 10.0f);
        audio.setPitch_ratio(request.getPitch() != null ? request.getPitch().floatValue() * 10 : 10.0f);
        
        // 调用火山引擎TTS API
        String responseJson = callVolcengineTtsApi(ttsRequest);
        
        // 解析响应
        byte[] audioData = parseTtsResponse(responseJson);
        
        // 保存音频文件
        String audioId = UUID.randomUUID().toString();
        String audioUrl = saveAudioFile(audioId, audioData, audio.getEncoding());
        
        // 构建响应
        TtsSynthesisRespDTO response = new TtsSynthesisRespDTO();
        response.setAudioId(audioId);
        response.setAudioUrl(audioUrl);
        response.setFileSize((long) audioData.length);
        response.setAudioFormat(audio.getEncoding());
        response.setSynthesisTime(System.currentTimeMillis());
        response.setVoiceType(audio.getVoice_type());
        response.setActualSpeed((double) audio.getSpeed_ratio());
        response.setActualPitch((double) audio.getPitch_ratio());
        response.setActualVolume((double) audio.getVolume_ratio());
        
        // 估算音频时长
        response.setDuration(estimateAudioDuration(request.getText().length()));
        
        log.info("TTS语音合成成功，audioId: {}, 文件大小: {} bytes", audioId, audioData.length);
        return response;
    }
    
    
    /**
     * 调用火山引擎TTS API（基于官方示例）
     */
    private String callVolcengineTtsApi(VolcengineTtsRequest ttsRequest) throws Exception {
        String jsonBody = JSON.toJSONString(ttsRequest);
        log.debug("TTS请求体: {}", jsonBody);
        
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(TTS_API_URL)
                .post(body)
                .header("Authorization", "Bearer; " + properties.getAccessKey())
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("TTS API调用失败，响应码: " + response.code() + ", 响应: " + response.body().string());
            }
            
            String responseBody = response.body().string();
            log.debug("TTS响应: {}", responseBody);
            return responseBody;
        }
    }
    
    /**
     * 解析TTS响应，提取音频数据
     */
    private byte[] parseTtsResponse(String responseJson) throws Exception {
        // 解析JSON响应，提取音频数据
        com.alibaba.fastjson.JSONObject responseObj = JSON.parseObject(responseJson);
        
        // 检查是否有错误
        if (responseObj.containsKey("error")) {
            String errorMsg = responseObj.getString("error");
            throw new RuntimeException("TTS API返回错误: " + errorMsg);
        }
        
        // 提取音频数据（Base64编码）
        String audioDataBase64 = responseObj.getString("data");
        if (audioDataBase64 == null) {
            throw new RuntimeException("TTS API返回的音频数据为空");
        }
        
        // Base64解码
        return Base64.getDecoder().decode(audioDataBase64);
    }
    
    /**
     * 保存音频文件
     */
    private String saveAudioFile(String audioId, byte[] audioData, String format) {
        // 这里应该保存到文件系统或云存储
        // 暂时返回一个模拟的URL
        String extension = format != null ? format : "mp3";
        return "http://localhost:8002/audio/generated/" + audioId + "." + extension;
    }
    
    /**
     * 估算音频时长
     */
    private Long estimateAudioDuration(int textLength) {
        // 简单估算：每100个字符约5秒
        return Math.max(1000L, (textLength / 100) * 5000L);
    }
    
    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "mp3";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
    
    /**
     * 验证训练音频文件
     */
    private void validateTrainingAudioFiles(MultipartFile[] audioFiles) throws Exception {
        if (audioFiles == null || audioFiles.length == 0) {
            throw new RuntimeException("至少需要上传一个音频文件");
        }
        
        if (audioFiles.length < 10) {
            throw new RuntimeException("音色训练至少需要10个音频文件");
        }
        
        if (audioFiles.length > 100) {
            throw new RuntimeException("音色训练最多支持100个音频文件");
        }
        
        // 验证每个文件
        for (MultipartFile file : audioFiles) {
            if (file.isEmpty()) {
                throw new RuntimeException("音频文件不能为空");
            }
            
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB
                throw new RuntimeException("单个音频文件大小不能超过10MB");
            }
            
            String extension = getFileExtension(file.getOriginalFilename());
            if (!"wav".equals(extension) && !"mp3".equals(extension)) {
                throw new RuntimeException("音色训练只支持wav和mp3格式");
            }
        }
    }
    
    /**
     * 上传训练音频文件
     */
    private String[] uploadTrainingAudioFiles(MultipartFile[] audioFiles) throws Exception {
        String[] audioUrls = new String[audioFiles.length];
        
        for (int i = 0; i < audioFiles.length; i++) {
            MultipartFile file = audioFiles[i];
            // 这里应该调用火山引擎的文件上传API
            // 暂时返回模拟URL
            String fileId = UUID.randomUUID().toString();
            audioUrls[i] = "https://volcengine-storage.com/training/" + fileId + "." + getFileExtension(file.getOriginalFilename());
            
            log.debug("上传训练音频文件 {}: {}", i + 1, audioUrls[i]);
        }
        
        return audioUrls;
    }
    
    /**
     * 创建音色训练任务
     */
    private String createVoiceTrainingTask(VoiceTrainingUploadReqDTO request, String[] audioUrls) throws Exception {
        VolcengineVoiceTrainingRequest trainingRequest = new VolcengineVoiceTrainingRequest(
                request.getVoiceName(), 
                request.getTrainingType() != null ? request.getTrainingType() : "standard"
        );
        
        // 设置App信息
        trainingRequest.getApp().setAppid(properties.getApiKey());
        trainingRequest.getApp().setCluster(properties.getRegion());
        
        // 设置训练参数
        trainingRequest.getTraining().setDescription(request.getVoiceDescription());
        trainingRequest.getTraining().setLanguage(request.getLanguage() != null ? request.getLanguage() : "zh-CN");
        trainingRequest.getTraining().setAudio_urls(audioUrls);
        
        // 调用火山引擎API
        String responseJson = callVoiceTrainingApi(trainingRequest);
        
        // 解析响应获取任务ID
        com.alibaba.fastjson.JSONObject responseObj = JSON.parseObject(responseJson);
        if (responseObj.containsKey("error")) {
            String errorMsg = responseObj.getString("error");
            throw new RuntimeException("创建音色训练任务失败: " + errorMsg);
        }
        
        return responseObj.getString("task_id");
    }
    
    /**
     * 查询训练状态
     */
    private String queryTrainingStatus(String trainingTaskId) throws Exception {
        VolcengineVoiceTrainingRequest request = new VolcengineVoiceTrainingRequest();
        request.getApp().setAppid(properties.getApiKey());
        request.getApp().setCluster(properties.getRegion());
        request.getRequest().setOperation("query_training_status");
        request.getRequest().setReqid(trainingTaskId);
        
        return callVoiceTrainingApi(request);
    }
    
    /**
     * 调用音色训练API
     */
    private String callVoiceTrainingApi(VolcengineVoiceTrainingRequest request) throws Exception {
        String jsonBody = JSON.toJSONString(request);
        log.debug("音色训练请求体: {}", jsonBody);
        
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
        Request httpRequest = new Request.Builder()
                .url(VOICE_TRAINING_API_URL)
                .post(body)
                .header("Authorization", "Bearer; " + properties.getAccessKey())
                .build();
        
        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("音色训练API调用失败，响应码: " + response.code() + ", 响应: " + response.body().string());
            }
            
            String responseBody = response.body().string();
            log.debug("音色训练响应: {}", responseBody);
            return responseBody;
        }
    }
    
    /**
     * 解析训练状态响应
     */
    private VoiceTrainingUploadRespDTO parseTrainingStatusResponse(String responseJson) throws Exception {
        com.alibaba.fastjson.JSONObject responseObj = JSON.parseObject(responseJson);
        
        VoiceTrainingUploadRespDTO response = new VoiceTrainingUploadRespDTO();
        response.setTrainingTaskId(responseObj.getString("task_id"));
        response.setTrainingStatus(responseObj.getString("status"));
        response.setProgress(responseObj.getInteger("progress"));
        response.setVoiceId(responseObj.getString("voice_id"));
        response.setErrorMessage(responseObj.getString("error_message"));
        
        return response;
    }
    
    /**
     * 计算总音频时长
     */
    private Long calculateTotalDuration(MultipartFile[] audioFiles) {
        // 简单估算：每个文件平均30秒
        return (long) audioFiles.length * 30;
    }
    
    
}
