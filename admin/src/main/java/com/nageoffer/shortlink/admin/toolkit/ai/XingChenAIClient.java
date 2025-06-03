package com.nageoffer.shortlink.admin.toolkit.ai;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Component
public class XingChenAIClient {

    @Value("${xunfei.appkey}")
    private String apiKey;

    @Value("${xunfei.appsecret}")
    private String apiSecret;

    @Value("${xunfei.appflow}")
    private String flowId;

    public void chat(String input, boolean stream, OutputStream outputStream, Consumer<String> callback) throws Exception {
        chat(input, stream, outputStream, callback, apiKey, apiSecret, flowId);
    }

    public void chat(String input, boolean stream, OutputStream outputStream, Consumer<String> callback, 
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
            "stream": %b
        }
        """, customFlowId, input, stream);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] inputBytes = payload.getBytes(StandardCharsets.UTF_8);
            os.write(inputBytes);
        }

        if (stream) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    callback.accept(line);

                    // 打印到控制台
                    System.out.println("[SSE 消息发送] " + line);

                    // SSE 格式输出
                    outputStream.write("data: ".getBytes(StandardCharsets.UTF_8));
                    outputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    outputStream.write("\n\n".getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
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