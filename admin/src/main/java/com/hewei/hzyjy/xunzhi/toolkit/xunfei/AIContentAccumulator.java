package com.hewei.hzyjy.xunzhi.toolkit.xunfei;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AIContentAccumulator {
    private final StringBuilder contentBuilder = new StringBuilder();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 解析 JSON 并累积内容
     */
    public void appendChunk(byte[] chunk) {
        try {
            String chunkStr = new String(chunk);
            
            // 处理SSE格式：移除"data: "前缀
            String jsonStr = chunkStr;
            if (chunkStr.startsWith("data: ")) {
                jsonStr = chunkStr.substring(6); // 移除"data: "前缀
            }
            
            // 跳过空行或非JSON数据
            jsonStr = jsonStr.trim();
            if (jsonStr.isEmpty() || !jsonStr.startsWith("{")) {
                return;
            }
            
            JsonNode root = objectMapper.readTree(jsonStr);
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
            // 记录解析错误（用于调试）
            System.err.println("JSON解析错误: " + e.getMessage() + ", 数据: " + new String(chunk));
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

    /**
     * 处理Coze工作流消息并累积内容
     * 解析格式：WorkflowEventMessage(content=嘿, nodeTitle=结束, nodeSeqID=0, nodeIsFinish=false, token=null, ext=null, usage=null)
     */
    public void appendCozeWorkflowMessage(String message) {
        try {
            if (message == null || message.trim().isEmpty()) {
                return;
            }

            // 解析JSON格式的Coze工作流消息
            if (message.trim().startsWith("{") && message.trim().endsWith("}")) {
                JsonNode root = objectMapper.readTree(message);
                
                // 检查是否是content类型的消息
                JsonNode typeNode = root.path("type");
                if (typeNode.isTextual() && "content".equals(typeNode.asText())) {
                    JsonNode contentNode = root.path("content");
                    if (contentNode.isTextual()) {
                        String content = contentNode.asText();
                        
                        // 解析WorkflowEventMessage格式
                        if (content.startsWith("WorkflowEventMessage(") && content.endsWith(")")) {
                            String messageContent = extractContentFromWorkflowMessage(content);
                            if (messageContent != null && !messageContent.trim().isEmpty()) {
                                contentBuilder.append(messageContent);
                            }
                        } else {
                            // 直接添加content内容
                            contentBuilder.append(content);
                        }
                    }
                }
            } else {
                // 直接处理WorkflowEventMessage格式的字符串
                if (message.startsWith("WorkflowEventMessage(") && message.endsWith(")")) {
                    String messageContent = extractContentFromWorkflowMessage(message);
                    if (messageContent != null && !messageContent.trim().isEmpty()) {
                        contentBuilder.append(messageContent);
                    }
                } else {
                    // 其他格式直接添加
                    contentBuilder.append(message);
                }
            }
        } catch (IOException e) {
            // 记录解析错误（用于调试）
            System.err.println("Coze工作流消息解析错误: " + e.getMessage() + ", 数据: " + message);
        }
    }

    /**
     * 从WorkflowEventMessage中提取content内容
     * 格式：WorkflowEventMessage(content=嘿, nodeTitle=结束, nodeSeqID=0, nodeIsFinish=false, token=null, ext=null, usage=null)
     */
    private String extractContentFromWorkflowMessage(String workflowMessage) {
        try {
            // 移除前缀和后缀
            String params = workflowMessage.substring("WorkflowEventMessage(".length(), workflowMessage.length() - 1);
            
            // 解析参数
            String[] parts = params.split(", ");
            for (String part : parts) {
                if (part.startsWith("content=")) {
                    String content = part.substring("content=".length());
                    // 移除可能的引号
                    if (content.startsWith("\"") && content.endsWith("\"")) {
                        content = content.substring(1, content.length() - 1);
                    }
                    return content;
                }
            }
        } catch (Exception e) {
            System.err.println("提取WorkflowEventMessage内容失败: " + e.getMessage());
        }
        return null;
    }

    /**
     * 简单的字符串追加方法（兼容现有代码）
     */
    public void append(String data) {
        if (data != null) {
            appendCozeWorkflowMessage(data);
        }
    }
}