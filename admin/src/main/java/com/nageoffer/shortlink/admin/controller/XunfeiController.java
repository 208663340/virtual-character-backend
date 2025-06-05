package com.nageoffer.shortlink.admin.controller;

import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.common.util.FileUploadUtil;
import com.nageoffer.shortlink.admin.dto.resp.file.FileUploadResult;
import com.nageoffer.shortlink.admin.dto.resp.xunfei.ExpressionRecognitionResult;
import com.nageoffer.shortlink.admin.service.tool.XunfeiAudioService;
import com.nageoffer.shortlink.admin.service.tool.XunfeiFaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

/**
 * 讯飞AI功能控制器
 * 提供音频转文字和表情识别的REST API接口
 */
@RestController
@RequiredArgsConstructor
public class XunfeiController {

    private final XunfeiAudioService xunfeiAudioService;

    private final XunfeiFaceService xunfeiFaceService;

    /**
     * 实时音频文件转文字
     * @param audioFile 音频文件
     * @return 转换结果
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/audio/convert")
    public Result<CompletableFuture<String>> convertAudioToText(
            @RequestParam("file") MultipartFile audioFile) {
        return Results.success(xunfeiAudioService.convertAudioToText(audioFile));
    }


    /**
     * 表情识别
     * @param image 人脸图片
     * @return 表情识别结果
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/face/expression")
    public Result<CompletableFuture<ExpressionRecognitionResult>> detectExpression(
            @RequestParam("image") MultipartFile image) {
        return Results.success(xunfeiFaceService.detectExpression(image));
    }

    /**
     * PDF文件上传
     * @param file PDF文件（限制：只能是PDF格式，大小不超过20MB）
     * @return 文件上传结果
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/file/upload/pdf")
    public Result<FileUploadResult> uploadPdfFile(
            @RequestParam("file") MultipartFile file) {
        // 使用工具类验证PDF文件
        FileUploadResult result = FileUploadUtil.validateAndProcessFile(file, FileUploadUtil.FileType.PDF);
        return Results.success(result);
    }

}