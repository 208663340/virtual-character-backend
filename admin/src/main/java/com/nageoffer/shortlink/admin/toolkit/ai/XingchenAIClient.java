package com.nageoffer.shortlink.admin.toolkit.ai;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class XingchenAIClient {

    private final String apiKey;
    private final String apiSecret;
    private final String flowId;
    
    public XingchenAIClient(String apiKey, String apiSecret, String flowId) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.flowId = flowId;
    }
    
    public void chat(String input, boolean stream, Consumer<String> callback) throws Exception {
        String urlString = "https://xingchen-api.xf-yun.com/workflow/v1/chat/completions";
        URL url = new URL(urlString);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setSSLSocketFactory((HttpsURLConnection.getDefaultSSLSocketFactory()));
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(120_000);
        conn.setReadTimeout(120_000);

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "text/event-stream");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey + ":" + apiSecret);
        conn.setDoOutput(true);

        String payload = String.format("""
                {
                    "flow_id": "%s",
                    "uid": "123",
                    "parameters": {"AGENT_USER_INPUT": "%s"},
                    "ext": {"bot_id": "adjfidjf", "caller": "workflow"},
                    "stream": %b
                }
                """, flowId, input, stream);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] inputBytes = payload.getBytes(StandardCharsets.UTF_8);
            os.write(inputBytes);
        }

        if (stream) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    callback.accept(line);
                }
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String response = br.readLine();
                callback.accept(response);
            }
        }

        conn.disconnect();
    }
}