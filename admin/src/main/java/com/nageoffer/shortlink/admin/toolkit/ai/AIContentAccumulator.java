package com.nageoffer.shortlink.admin.toolkit.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;

public class AIContentAccumulator {
    private final StringBuilder contentBuilder = new StringBuilder();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 解析 JSON 并累积内容
     */
    public void appendChunk(byte[] chunk) {
        try {
            JsonNode root = objectMapper.readTree(new String(chunk));
            JsonNode choices = root.path("choices");
            if (choices.isArray()) {
                for (JsonNode choice : choices) {
                    JsonNode delta = choice.path("delta");
                    JsonNode content = delta.path("content");
                    if (content.isTextual()) {
                        contentBuilder.append(content.asText());
                    }
                }
            }
        } catch (IOException e) {
            // 忽略解析错误（或记录日志）
        }
    }

    /**
     * 获取完整内容
     */
    public String getFullContent() {
        return contentBuilder.toString();
    }

    /**
     * 重置累积器
     */
    public void reset() {
        contentBuilder.setLength(0);
    }
}