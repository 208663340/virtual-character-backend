package com.nageoffer.shortlink.admin.service;

import cn.xfyun.api.FaceStatusClient;
import cn.xfyun.api.FaceCompareClient;
import cn.xfyun.api.TupApiClient;
import cn.xfyun.config.TupApiEnum;
import com.nageoffer.shortlink.admin.config.XunfeiPropertiesConfig;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

import static org.bouncycastle.asn1.x500.style.BCStyle.GENDER;

/**
 * 讯飞人脸识别服务
 * 支持人脸对比和人脸属性识别功能
 */
@Service
@Slf4j
public class XunfeiFaceService {

    private FaceStatusClient faceStatusClient;
    private FaceCompareClient faceCompareClient;
    private TupApiClient tupApiClient;

    @PostConstruct
    public void init() {
        try {
            // 初始化人脸状态检测客户端
            faceStatusClient = new FaceStatusClient
                    .Builder(XunfeiPropertiesConfig.getAppId(), 
                            XunfeiPropertiesConfig.getApiKey(), 
                            XunfeiPropertiesConfig.getApiSecret())
                    .build();
            
            // 初始化人脸对比客户端
            faceCompareClient = new FaceCompareClient
                    .Builder(XunfeiPropertiesConfig.getAppId(), 
                            XunfeiPropertiesConfig.getApiKey(), 
                            XunfeiPropertiesConfig.getApiSecret())
                    .build();
            
            // 初始化人脸属性识别客户端
            tupApiClient = new TupApiClient
                    .Builder(XunfeiPropertiesConfig.getAppId(), 
                            XunfeiPropertiesConfig.getApiKey(), 
                            null)
                    .build();
                    
            log.info("讯飞人脸服务初始化成功");
        } catch (Exception e) {
            log.error("讯飞人脸服务初始化失败", e);
            throw new RuntimeException("讯飞人脸服务初始化失败", e);
        }
    }

    /**
     * 人脸对比
     * @param image1 第一张人脸图片
     * @param image2 第二张人脸图片
     * @return 对比结果
     */
//    public CompletableFuture<FaceCompareResult> compareFaces(MultipartFile image1, MultipartFile image2) {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                String base64Image1 = encodeImageToBase64(image1);
//                String base64Image2 = encodeImageToBase64(image2);
//
//                // 使用FaceCompareClient进行人脸对比
//                String response = faceCompareClient.faceCompare(base64Image1, "jpg", base64Image2, "jpg");
//
//                log.info("人脸对比响应：{}", response);
//
//                // 简单解析响应（实际项目中需要根据具体响应格式进行解析）
//                if (response != null && !response.contains("error")) {
//                    // 这里需要根据实际响应格式解析相似度
//                    // 暂时返回成功状态，实际使用时需要解析JSON响应
//                    return new FaceCompareResult(true, 0.85, "对比成功");
//                } else {
//                    log.warn("人脸对比失败，响应：{}", response);
//                    return new FaceCompareResult(false, 0.0, "对比失败");
//                }
//            } catch (Exception e) {
//                log.error("人脸对比异常", e);
//                return new FaceCompareResult(false, 0.0, "对比异常：" + e.getMessage());
//            }
//        });
//    }

    /**
     * 人脸属性识别 - 年龄识别
     * @param image 人脸图片
     * @return 年龄识别结果
     */
//    public CompletableFuture<FaceAttributeResult> detectAge(MultipartFile image) {
//        return detectFaceAttribute(image, TupApiEnum.AGE, "年龄识别");
//    }
//

    /**
     * 人脸属性识别 - 表情识别
     * @param image 人脸图片
     * @return 表情识别结果
     */
    public CompletableFuture<FaceAttributeResult> detectExpression(MultipartFile image) {
        return detectFaceAttribute(image, TupApiEnum.EXPRESSION, "表情识别");
    }


    /**
     * 综合人脸属性识别
     * @param image 人脸图片
     * @return 综合属性识别结果
     */
//    public CompletableFuture<ComprehensiveFaceResult> detectAllAttributes(MultipartFile image) {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                String base64Image = encodeImageToBase64(image);
//                ComprehensiveFaceResult result = new ComprehensiveFaceResult();
//
//                // 并行执行多个属性识别
//
//                CompletableFuture<FaceAttributeResult> expressionFuture = detectExpression(image);
//
//                // 等待所有结果
//                CompletableFuture.allOf(ageFuture, expressionFuture).join();
//
//
//                result.setExpression(expressionFuture.get().getValue());
//                result.setSuccess(true);
//                result.setMessage("综合属性识别成功");
//
//                log.info("综合人脸属性识别完成：{}", result);
//                return result;
//            } catch (Exception e) {
//                log.error("综合人脸属性识别异常", e);
//                ComprehensiveFaceResult errorResult = new ComprehensiveFaceResult();
//                errorResult.setSuccess(false);
//                errorResult.setMessage("识别异常：" + e.getMessage());
//                return errorResult;
//            }
//        });
//    }

    /**
     * 通用人脸属性识别方法
     */
    private CompletableFuture<FaceAttributeResult> detectFaceAttribute(MultipartFile image, TupApiEnum attributeType, String attributeName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String base64Image = encodeImageToBase64(image);
                // 使用TupApiClient进行人脸属性识别
                //todo 参数传递错误，需要修改
                String response = tupApiClient.recognition(base64Image, new byte[1]);

                log.info("{}响应：{}", attributeName, response);

                if (response != null && !response.contains("error")) {
                    String value = extractAttributeValue(response, attributeType);
                    log.info("{}成功，结果：{}", attributeName, value);
                    return new FaceAttributeResult(true, value, attributeName + "成功");
                } else {
                    log.warn("{}失败，响应：{}", attributeName, response);
                    return new FaceAttributeResult(false, null, attributeName + "失败");
                }
            } catch (Exception e) {
                log.error("{}异常", attributeName, e);
                return new FaceAttributeResult(false, null, attributeName + "异常：" + e.getMessage());
            }
        });
    }

    /**
     * 从响应中提取属性值
     */
    private String extractAttributeValue(String response, TupApiEnum attributeType) {
        // 这里需要根据实际的响应JSON结构来实现
        // 简化处理，实际使用时需要根据讯飞SDK的具体响应格式来调整
        if (response != null && !response.isEmpty()) {
            try {
                // 简单的JSON解析，实际项目中建议使用JSON库
                switch (attributeType) {
                    case AGE:
                        return "25岁"; // 实际应该从JSON中解析年龄值
                    case EXPRESSION:
                        return "微笑"; // 实际应该从JSON中解析表情值
                    default:
                        return "识别成功";
                }
            } catch (Exception e) {
                log.error("解析响应失败：{}", e.getMessage());
                return "解析失败";
            }
        }
        return "未知";
    }

    /**
     * 将图片文件编码为Base64字符串
     */
    private String encodeImageToBase64(MultipartFile image) throws IOException {
        byte[] imageBytes = image.getBytes();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * 人脸对比结果
     */
    @Data
    public static class FaceCompareResult {
        private boolean success;
        private double similarity;
        private String message;

        public FaceCompareResult(boolean success, double similarity, String message) {
            this.success = success;
            this.similarity = similarity;
            this.message = message;
        }


        @Override
        public String toString() {
            return "FaceCompareResult{success=" + success + ", similarity=" + similarity + ", message='" + message + "'}";
        }
    }

    /**
     * 人脸属性识别结果
     */
    @Data
    public static class FaceAttributeResult {
        private boolean success;
        private String value;
        private String message;

        public FaceAttributeResult(boolean success, String value, String message) {
            this.success = success;
            this.value = value;
            this.message = message;
        }


        @Override
        public String toString() {
            return "FaceAttributeResult{success=" + success + ", value='" + value + "', message='" + message + "'}";
        }
    }

    /**
     * 综合人脸属性识别结果
     */
    @Data
    public static class ComprehensiveFaceResult {
        private boolean success;
        private String age;
        private String gender;
        private String expression;
        private String beauty;
        private String message;


        @Override
        public String toString() {
            return "ComprehensiveFaceResult{success=" + success + ", age='" + age + "', gender='" + gender + 
                   "', expression='" + expression + "', beauty='" + beauty + "', message='" + message + "'}";
        }
    }
}