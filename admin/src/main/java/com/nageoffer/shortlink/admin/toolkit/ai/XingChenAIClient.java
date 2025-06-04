package com.nageoffer.shortlink.admin.toolkit.ai;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

@Component
public class XingChenAIClient {


//    public void chat(String input, boolean stream, OutputStream outputStream, Consumer<String> callback) throws Exception {
//        chat(input, stream, outputStream, callback, apiKey, apiSecret, flowId);
//    }

    public void chat(String input,String chatId,String history, boolean stream, OutputStream outputStream, Consumer<String> callback,
                     String customApiKey, String customApiSecret, String customFlowId) throws Exception {
        String urlString = "https://xingchen-api.xf-yun.com/workflow/v1/chat/completions";
        URL url = new URL(urlString);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

//        conn.setSSLSocketFactory((HttpsURLConnection.getDefaultSSLSocketFactory()));
//        conn.setRequestMethod("POST");
//        conn.setConnectTimeout(120_000);
//        conn.setReadTimeout(120_000);

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "text/event-stream");
        conn.setRequestProperty("Authorization", "Bearer " + customApiKey + ":" + customApiSecret);
        conn.setDoOutput(true);

        String payload = String.format("""
        {
            "flow_id": "%s",
            "uid": "123",
            "parameters": {"AGENT_USER_INPUT": "%s"},
            "ext": {"bot_id": "adjfidjf", "caller": "workflow"},
            "stream": %b,
            "chat_id": "%s",
            "history": %s
        }
        """, customFlowId, input, stream,chatId,history);
        System.out.println(payload);
        try (OutputStream os = conn.getOutputStream()) {
            byte[] inputBytes = payload.getBytes(StandardCharsets.UTF_8);
            os.write(inputBytes);
        }

        if (stream) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // 过滤空行和无效数据
                    if (line.trim().isEmpty()) {
                        continue;
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
                        System.out.println("[AI数据接收] " + processedLine);
                        
                        // 发送处理后的数据
                        outputStream.write(processedLine.getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                    } else {
                        System.out.println("[数据过滤] 跳过无效行: " + line);
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
}