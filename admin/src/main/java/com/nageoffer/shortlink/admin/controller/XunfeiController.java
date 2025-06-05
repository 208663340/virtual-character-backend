package com.nageoffer.shortlink.admin.controller;

import com.nageoffer.shortlink.admin.service.tool.XunfeiAudioService;
import com.nageoffer.shortlink.admin.service.tool.XunfeiFaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 讯飞AI功能控制器
 * 提供音频转文字和人脸识别的REST API接口
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class XunfeiController {

    private final XunfeiAudioService xunfeiAudioService;

    private final XunfeiFaceService xunfeiFaceService;

    /**
     * 音频文件转文字
     * @param audioFile 音频文件
     * @return 转换结果
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/audio/convert")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> convertAudioToText(
            @RequestParam("file") MultipartFile audioFile) {
        
        log.info("收到音频转文字请求，文件名：{}, 大小：{} bytes", audioFile.getOriginalFilename(), audioFile.getSize());
        
        if (audioFile.isEmpty()) {
            return CompletableFuture.completedFuture(createErrorResponse("音频文件不能为空"));
        }

        try {
            return xunfeiAudioService.audioToText((MultipartFile) audioFile.getInputStream())
                    .thenApply(result -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("text", result);
                        response.put("message", "音频转文字成功");
                        return ResponseEntity.ok(response);
                    })
                    .exceptionally(throwable -> {
                        log.error("音频转文字失败", throwable);
                        return createErrorResponse("音频转文字失败：" + throwable.getMessage());
                    });
        } catch (IOException e) {
            log.error("读取音频文件失败", e);
            return CompletableFuture.completedFuture(createErrorResponse("读取音频文件失败"));
        }
    }

    /**
     * 实时音频流转文字
     * @param audioFile 音频文件
     * @return 转换结果
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/audio/realtime")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> realtimeAudioToText(
            @RequestParam("file") MultipartFile audioFile) {
        
        log.info("收到实时音频转文字请求，文件名：{}, 大小：{} bytes", audioFile.getOriginalFilename(), audioFile.getSize());
        
        if (audioFile.isEmpty()) {
            return CompletableFuture.completedFuture(createErrorResponse("音频文件不能为空"));
        }

        try {
            return xunfeiAudioService.realTimeAudioToText(audioFile.getInputStream(), result -> {
                // 这里可以实现WebSocket推送实时结果给前端
                log.info("实时转写结果：{}", result);
            })
                    .thenApply(result -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("success", true);
                        response.put("text", result);
                        response.put("message", "实时音频转文字成功");
                        return ResponseEntity.ok(response);
                    })
                    .exceptionally(throwable -> {
                        log.error("实时音频转文字失败", throwable);
                        return createErrorResponse("实时音频转文字失败：" + throwable.getMessage());
                    });
        } catch (IOException e) {
            log.error("读取音频文件失败", e);
            return CompletableFuture.completedFuture(createErrorResponse("读取音频文件失败"));
        }
    }


    /**
     * 人脸属性识别 - 表情
     * @param image 人脸图片
     * @return 表情识别结果
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/face/expression")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> detectExpression(
            @RequestParam("image") MultipartFile image) {
        return detectSingleAttribute(image, "expression", () -> xunfeiFaceService.detectExpression(image));
    }


//    /**
//     * 综合人脸属性识别
//     * @param image 人脸图片
//     * @return 综合属性识别结果
//     */
//    @PostMapping("/face/attributes")
//    public CompletableFuture<ResponseEntity<Map<String, Object>>> detectAllAttributes(
//            @RequestParam("image") MultipartFile image) {
//
//        log.info("收到综合人脸属性识别请求，图片：{}", image.getOriginalFilename());
//
//        if (image.isEmpty()) {
//            return CompletableFuture.completedFuture(createErrorResponse("图片文件不能为空"));
//        }
//
//        return xunfeiFaceService.detectAllAttributes(image)
//                .thenApply(result -> {
//                    Map<String, Object> response = new HashMap<>();
//                    response.put("success", result.isSuccess());
//                    response.put("age", result.getAge());
//                    response.put("gender", result.getGender());
//                    response.put("expression", result.getExpression());
//                    response.put("beauty", result.getBeauty());
//                    response.put("message", result.getMessage());
//                    return ResponseEntity.ok(response);
//                })
//                .exceptionally(throwable -> {
//                    log.error("综合人脸属性识别失败", throwable);
//                    return createErrorResponse("综合人脸属性识别失败：" + throwable.getMessage());
//                });
//    }

    /**
     * 通用单个属性识别方法
     */
    private CompletableFuture<ResponseEntity<Map<String, Object>>> detectSingleAttribute(
            MultipartFile image, String attributeName, 
            java.util.function.Supplier<CompletableFuture<XunfeiFaceService.FaceAttributeResult>> detector) {
        
        log.info("收到{}识别请求，图片：{}", attributeName, image.getOriginalFilename());
        
        if (image.isEmpty()) {
            return CompletableFuture.completedFuture(createErrorResponse("图片文件不能为空"));
        }

        return detector.get()
                .thenApply(result -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", result.isSuccess());
                    response.put(attributeName, result.getValue());
                    response.put("message", result.getMessage());
                    return ResponseEntity.ok(response);
                })
                .exceptionally(throwable -> {
                    log.error("{}识别失败", attributeName, throwable);
                    return createErrorResponse(attributeName + "识别失败：" + throwable.getMessage());
                });
    }

    /**
     * 创建错误响应
     */
    private ResponseEntity<Map<String, Object>> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
    }

}