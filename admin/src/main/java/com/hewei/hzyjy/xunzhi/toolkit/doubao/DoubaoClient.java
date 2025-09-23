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
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 豆包大模型客户端
 * 
 * @author hewei
 */
@Slf4j
@Component
public class DoubaoClient {
    
    @Autowired
    private DoubaoProperties doubaoProperties;
    
    private OkHttpClient httpClient;
    
    @PostConstruct
    public void init() {
        try {
            log.info("正在初始化豆包大模型客户端...");
            this.httpClient = new OkHttpClient.Builder()
                    .connectTimeout(doubaoProperties.getConnectTimeout(), TimeUnit.SECONDS)
                    .readTimeout(doubaoProperties.getTimeout(), TimeUnit.SECONDS)
                    .writeTimeout(doubaoProperties.getTimeout(), TimeUnit.SECONDS)
                    .build();
            log.info("豆包大模型客户端初始化成功");
        } catch (Exception e) {
            log.error("豆包大模型客户端初始化失败", e);
            throw new RuntimeException("豆包大模型客户端初始化失败", e);
        }
    }
    
    @PreDestroy
    public void destroy() {
        if (httpClient != null) {
            try {
                httpClient.dispatcher().executorService().shutdown();
                httpClient.connectionPool().evictAll();
                log.info("豆包大模型客户端已关闭");
            } catch (Exception e) {
                log.error("关闭豆包大模型客户端时发生错误", e);
            }
        }
    }
    
    /**
     * 调用豆包大模型生成回复
     *
     * @param userPrompt 用户输入的提示
     * @return 模型生成的回复
     */
    public String chat(String userPrompt) {
        return chat(userPrompt, null);
    }
    
    /**
     * 调用豆包大模型生成回复
     *
     * @param userPrompt 用户输入的提示
     * @param systemPrompt 系统提示（可选）
     * @return 模型生成的回复
     */
    public String chat(String userPrompt, String systemPrompt) {
        try {
            log.info("开始调用豆包大模型，用户输入: {}", userPrompt);
            
            // 构建请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", doubaoProperties.getModelId());
            requestBody.put("max_tokens", 2048);
            requestBody.put("temperature", 0.7);
            requestBody.put("stream", false);
            
            // 构建消息数组
            JSONArray messages = new JSONArray();
            
            // 添加系统提示（如果有）
            if (systemPrompt != null && !systemPrompt.trim().isEmpty()) {
                JSONObject systemMessage = new JSONObject();
                systemMessage.put("role", "system");
                systemMessage.put("content", systemPrompt);
                messages.add(systemMessage);
            }
            
            // 添加用户消息
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", userPrompt);
            messages.add(userMessage);
            
            requestBody.put("messages", messages);
            
            // 创建请求
            RequestBody body = RequestBody.create(
                    requestBody.toJSONString(),
                    MediaType.parse("application/json; charset=utf-8")
            );
            
            Request request = new Request.Builder()
                    .url(doubaoProperties.getBaseUrl() + "/chat/completions")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + doubaoProperties.getApiKey())
                    .build();
            
            // 发送请求
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                    log.error("豆包API调用失败，HTTP状态码: {}, 错误信息: {}", response.code(), errorBody);
                    return "抱歉，调用失败：HTTP " + response.code() + " - " + errorBody;
                }
                
                String responseBody = response.body().string();
                log.debug("豆包API响应: {}", responseBody);
                
                // 解析响应
                JSONObject responseJson = JSON.parseObject(responseBody);
                JSONArray choices = responseJson.getJSONArray("choices");
                
                if (choices != null && !choices.isEmpty()) {
                    JSONObject choice = choices.getJSONObject(0);
                    JSONObject message = choice.getJSONObject("message");
                    String result = message.getString("content");
                    
                    log.info("豆包大模型回复成功，回复内容长度: {}", result.length());
                    return result;
                } else {
                    log.warn("豆包大模型未返回有效结果");
                    return "抱歉，模型未返回有效结果";
                }
            }
        } catch (IOException e) {
            log.error("调用豆包大模型网络请求失败", e);
            return "抱歉，网络请求失败：" + e.getMessage();
        } catch (Exception e) {
            log.error("调用豆包大模型失败", e);
            return "抱歉，调用失败：" + e.getMessage();
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
