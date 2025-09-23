package com.hewei.hzyjy.xunzhi.toolkit.doubao;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hewei.hzyjy.xunzhi.config.doubao.DoubaoProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 豆包流式AI客户端
 * 兼容SparkAIClient接口，支持流式响应
 * 
 * @author hewei
 */
@Slf4j
@Component
public class DoubaoStreamClient {
    
    @Autowired
    private DoubaoProperties doubaoProperties;
    
    private OkHttpClient httpClient;
    
    @PostConstruct
    public void init() {
        try {
            log.info("正在初始化豆包流式AI客户端...");
            this.httpClient = new OkHttpClient.Builder()
                    .connectTimeout(doubaoProperties.getConnectTimeout(), TimeUnit.SECONDS)
                    .readTimeout(doubaoProperties.getTimeout(), TimeUnit.SECONDS)
                    .writeTimeout(doubaoProperties.getTimeout(), TimeUnit.SECONDS)
                    .build();
            log.info("豆包流式AI客户端初始化成功");
        } catch (Exception e) {
            log.error("豆包流式AI客户端初始化失败", e);
            throw new RuntimeException("豆包流式AI客户端初始化失败", e);
        }
    }
    
    @PreDestroy
    public void destroy() {
        if (httpClient != null) {
            try {
                httpClient.dispatcher().executorService().shutdown();
                httpClient.connectionPool().evictAll();
                log.info("豆包流式AI客户端已关闭");
            } catch (Exception e) {
                log.error("关闭豆包流式AI客户端时发生错误", e);
            }
        }
    }
    
    /**
     * 发送对话请求（兼容SparkAIClient接口）
     * @param input 用户输入
     * @param history 历史对话记录 (JSON字符串格式)
     * @param stream 是否启用流式响应
     * @param outputStream 输出流，用于写入AI响应
     * @param callback 回调函数，处理每个接收到的数据块
     * @param customApiKey API密钥（这里将使用配置中的豆包密钥）
     * @param model 使用的AI模型（这里将使用配置中的豆包模型）
     * @throws Exception 请求或处理过程中发生异常
     */
    public void chatStream(String input, String history, boolean stream, OutputStream outputStream,
                     Consumer<String> callback, String customApiKey, String model) throws Exception {
        try {
            log.info("开始调用豆包流式AI，用户输入: {}, 流式模式: {}", input, stream);
            
            // 构建请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", doubaoProperties.getModelId());
            requestBody.put("max_tokens", 2048);
            requestBody.put("temperature", 0.7);
            requestBody.put("stream", stream);
            
            // 构建消息数组
            JSONArray messages = new JSONArray();
            
            // 处理历史消息
            if (history != null && !history.trim().isEmpty()) {
                try {
                    JSONArray historyJsonArray = JSON.parseArray(history);
                    for (Object msg : historyJsonArray) {
                        if (msg instanceof JSONObject) {
                            messages.add(msg);
                        }
                    }
                } catch (Exception e) {
                    log.warn("解析历史消息JSON失败: {}", e.getMessage());
                }
            }
            
            // 添加当前用户消息
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", input);
            messages.add(userMessage);
            
            requestBody.put("messages", messages);
            
            // 创建请求
            RequestBody body = RequestBody.create(
                    requestBody.toJSONString(),
                    MediaType.parse("application/json; charset=utf-8")
            );
            
            Request.Builder requestBuilder = new Request.Builder()
                    .url(doubaoProperties.getBaseUrl() + "/chat/completions")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + doubaoProperties.getApiKey());
            
            if (stream) {
                requestBuilder.addHeader("Accept", "text/event-stream");
            }
            
            Request request = requestBuilder.build();
            
            // 发送请求
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                    log.error("豆包API调用失败，HTTP状态码: {}, 错误信息: {}", response.code(), errorBody);
                    throw new RuntimeException("HTTP请求失败，响应码: " + response.code() + ", 响应体: " + errorBody);
                }
                
                if (stream) {
                    handleStreamResponse(response, outputStream, callback);
                } else {
                    handleNonStreamResponse(response, outputStream, callback);
                }
            }
            
        } catch (IOException e) {
            log.error("调用豆包流式AI网络请求失败", e);
            throw new Exception("网络请求失败：" + e.getMessage(), e);
        } catch (Exception e) {
            log.error("调用豆包流式AI失败", e);
            throw new Exception("调用失败：" + e.getMessage(), e);
        }
    }
    
    /**
     * 处理流式响应
     */
    private void handleStreamResponse(Response response, OutputStream outputStream, Consumer<String> callback) 
            throws IOException {
        try (BufferedReader reader = new BufferedReader(response.body().charStream())) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 过滤空行
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // 检查线程中断
                if (Thread.currentThread().isInterrupted()) {
                    log.info("检测到线程中断，停止数据读取");
                    break;
                }
                
                String processedLine = null;
                
                if (line.startsWith("data: ")) {
                    // 提取SSE数据部分
                    String dataContent = line.substring(6).trim();
                    if (dataContent.equals("[DONE]")) {
                        processedLine = "[DONE]";
                    } else if (dataContent.startsWith("{")) {
                        try {
                            // 解析豆包返回的JSON，转换为兼容格式
                            JSONObject jsonData = JSON.parseObject(dataContent);
                            JSONArray choices = jsonData.getJSONArray("choices");
                            if (choices != null && !choices.isEmpty()) {
                                JSONObject choice = choices.getJSONObject(0);
                                JSONObject delta = choice.getJSONObject("delta");
                                if (delta != null && delta.containsKey("content")) {
                                    // 构建兼容SparkAI格式的响应
                                    JSONObject compatibleResponse = new JSONObject();
                                    JSONArray compatibleChoices = new JSONArray();
                                    JSONObject compatibleChoice = new JSONObject();
                                    JSONObject compatibleDelta = new JSONObject();
                                    
                                    compatibleDelta.put("content", delta.getString("content"));
                                    compatibleChoice.put("delta", compatibleDelta);
                                    compatibleChoices.add(compatibleChoice);
                                    compatibleResponse.put("choices", compatibleChoices);
                                    
                                    processedLine = compatibleResponse.toJSONString();
                                }
                            }
                        } catch (Exception e) {
                            log.warn("解析豆包响应JSON失败: {}", e.getMessage());
                        }
                    }
                } else if (line.startsWith("{") || line.equals("[DONE]")) {
                    // 直接的JSON数据或结束标记
                    processedLine = line;
                }
                
                if (processedLine != null) {
                    callback.accept(processedLine);
                    log.debug("AI数据接收: {}", processedLine);
                    
                    // 发送处理后的数据
                    outputStream.write(processedLine.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                } else {
                    log.debug("数据过滤: 跳过无效行: {}", line);
                }
            }
        }
    }
    
    /**
     * 处理非流式响应
     */
    private void handleNonStreamResponse(Response response, OutputStream outputStream, Consumer<String> callback) 
            throws IOException {
        String responseBody = response.body().string();
        log.debug("豆包API非流式响应: {}", responseBody);
        
        try {
            // 解析豆包返回的JSON，转换为兼容格式
            JSONObject responseJson = JSON.parseObject(responseBody);
            JSONArray choices = responseJson.getJSONArray("choices");
            
            if (choices != null && !choices.isEmpty()) {
                JSONObject choice = choices.getJSONObject(0);
                JSONObject message = choice.getJSONObject("message");
                
                if (message != null) {
                    // 构建兼容SparkAI格式的响应
                    JSONObject compatibleResponse = new JSONObject();
                    JSONArray compatibleChoices = new JSONArray();
                    JSONObject compatibleChoice = new JSONObject();
                    
                    compatibleChoice.put("message", message);
                    compatibleChoices.add(compatibleChoice);
                    compatibleResponse.put("choices", compatibleChoices);
                    
                    String result = compatibleResponse.toJSONString();
                    callback.accept(result);
                    outputStream.write(result.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            log.error("解析豆包非流式响应失败", e);
            // 如果解析失败，直接返回原始响应
            callback.accept(responseBody);
            outputStream.write(responseBody.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }
    
    /**
     * 检查客户端是否已初始化
     *
     * @return true if initialized, false otherwise
     */
    public boolean isInitialized() {
        return httpClient != null;
    }
}
