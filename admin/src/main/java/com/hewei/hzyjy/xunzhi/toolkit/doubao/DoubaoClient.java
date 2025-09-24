package com.hewei.hzyjy.xunzhi.toolkit.doubao;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hewei.hzyjy.xunzhi.config.doubao.DoubaoProperties;
import com.hewei.hzyjy.xunzhi.toolkit.xunfei.RoleContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;
import javax.net.ssl.HttpsURLConnection;

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

    @PostConstruct
    public void init() {
        log.info("豆包大模型客户端初始化成功");
    }

    @PreDestroy
    public void destroy() {
        log.info("豆包大模型客户端已关闭");
    }

    /**
     * 发送对话请求（支持流式和非流式响应）
     *
     * @param input        用户输入
     * @param historyJson  历史对话记录 (JSON字符串格式)
     * @param stream       是否启用流式响应
     * @param outputStream 输出流，用于写入AI响应
     * @param callback     回调函数，处理每个接收到的数据块
     * @param customApiKey 豆包API密钥 (实际使用doubaoProperties中的)
     * @param model        使用的AI模型 (实际使用doubaoProperties中的)
     * @throws Exception 请求或处理过程中发生异常
     */
    public void chatStream(String input, String historyJson, boolean stream, OutputStream outputStream,
                           Consumer<String> callback, String customApiKey, String model) throws Exception {
        
        String API_URL = doubaoProperties.getBaseUrl() + "/chat/completions";
        URL url = new URL(API_URL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(30_000); // 连接超时30秒
        conn.setReadTimeout(300_000); // 读取超时5分钟，适应长时间输出

        // 请求头
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", stream ? "text/event-stream" : "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + doubaoProperties.getApiKey());
        conn.setDoOutput(true);

        // 创建请求的JSON对象
        JSONObject payload = new JSONObject();
        payload.put("model", doubaoProperties.getModelId());
        payload.put("max_tokens", 2048);
        payload.put("temperature", 0.7);
        payload.put("stream", stream);

        JSONArray messagesArray = new JSONArray();
        // 处理历史消息
        if (historyJson != null && !historyJson.trim().isEmpty()) {
            try {
                List<RoleContent> historyList = JSON.parseArray(historyJson, RoleContent.class);
                for (RoleContent rc : historyList) {
                    JSONObject msg = new JSONObject();
                    msg.put("role", rc.getRole());
                    msg.put("content", rc.getContent());
                    messagesArray.add(msg);
                }
            } catch (Exception e) {
                log.warn("[DoubaoStreamClient] Error parsing history JSON: {}", e.getMessage());
            }
        }

        // 添加当前用户输入
        JSONObject currentUserMessage = new JSONObject();
        currentUserMessage.put("role", "user");
        currentUserMessage.put("content", input);
        messagesArray.add(currentUserMessage);

        payload.put("messages", messagesArray);

        // 发送请求
        try (OutputStream os = conn.getOutputStream()) {
            byte[] inputBytes = payload.toJSONString().getBytes(StandardCharsets.UTF_8);
            os.write(inputBytes);
            os.flush();
        }

        // 检查响应状态
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            String errorResponse = "";
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    errorResponse += line;
                }
            }
            throw new RuntimeException("豆包API调用失败，HTTP状态码: " + responseCode + ", 错误信息: " + errorResponse);
        }

        if (stream) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // 过滤空行和无效数据
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    
                    // 检查连接是否被中断
                    if (Thread.currentThread().isInterrupted()) {
                        log.warn("[DoubaoStreamClient] 检测到线程中断，停止数据读取");
                        break;
                    }
                    
                    // 处理SSE格式数据和结束标记
                    String processedLine = null;

                    if (line.startsWith("data: ")) {
                        // 提取SSE数据部分
                        String dataContent = line.substring(6).trim();
                        if (dataContent.startsWith("{") || dataContent.equals("[DONE]")) {
                            processedLine = dataContent;
                        }
                    } else if (line.startsWith("{") || line.equals("[DONE]")) {
                        // 直接的JSON数据或结束标记
                        processedLine = line;
                    }
                    
                    if (processedLine != null) {
                        callback.accept(processedLine);
                        // 打印到控制台
                        log.debug("[豆包数据接收] {}", processedLine);

                        // 发送处理后的数据
                        outputStream.write(processedLine.getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                    } else {
                        log.debug("[数据过滤] 跳过无效行: {}", line);
                    }
                }
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String response = br.readLine();
                callback.accept(response);
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        }

        conn.disconnect();
    }

    /**
     * 检查客户端是否已初始化
     *
     * @return true if initialized, false otherwise
     */
    public boolean isInitialized() {
        return doubaoProperties != null;
    }
}