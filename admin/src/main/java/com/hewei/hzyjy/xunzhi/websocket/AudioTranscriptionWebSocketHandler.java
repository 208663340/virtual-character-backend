package com.hewei.hzyjy.xunzhi.websocket;

import com.alibaba.fastjson2.JSON;
import com.hewei.hzyjy.xunzhi.service.tool.XunfeiAudioService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 实时语音转写WebSocket处理器 - 支持双向通信
 * 
 * @author hewei
 */
@Slf4j
@Component
@ServerEndpoint("/api/xunzhi/v1/xunfei/audio-to-text/{userId}")
@RequiredArgsConstructor
public class AudioTranscriptionWebSocketHandler {

    private static XunfeiAudioService xunfeiAudioService;
    
    // 存储会话信息，key为userId，value为Session
    private static final ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();
    
    // 存储用户会话映射，key为sessionId，value为userId
    private static final ConcurrentMap<String, String> sessionUserMap = new ConcurrentHashMap<>();
    
    // 心跳检测定时器
    private static final ScheduledExecutorService heartbeatExecutor = Executors.newScheduledThreadPool(1);
    
    // 注入服务（静态注入）
    public AudioTranscriptionWebSocketHandler(XunfeiAudioService xunfeiAudioService) {
        AudioTranscriptionWebSocketHandler.xunfeiAudioService = xunfeiAudioService;
    }

    /**
     * WebSocket连接建立
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        String sessionId = session.getId();
        
        // 存储会话信息
        sessions.put(userId, session);
        sessionUserMap.put(sessionId, userId);
        
        log.info("WebSocket连接建立，userId: {}, sessionId: {}", userId, sessionId);
        
        // 发送连接成功消息
        sendMessage(session, createResponse("connected", "WebSocket连接成功", userId));
        
        // 启动心跳检测
        startHeartbeat(session, userId);
    }

    /**
     * 接收到文本消息（支持JSON格式的控制指令）
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        String sessionId = session.getId();
        String userId = sessionUserMap.get(sessionId);
        
        log.info("收到文本消息，userId: {}, message: {}", userId, message);
        
        try {
            // 尝试解析JSON消息
            WebSocketMessage wsMessage = JSON.parseObject(message, WebSocketMessage.class);
            handleControlMessage(session, userId, wsMessage);
        } catch (Exception e) {
            // 如果不是JSON格式，当作普通文本处理
            log.info("收到普通文本消息: {}", message);
            sendMessage(session, createResponse("info", "收到文本消息: " + message, null));
        }
    }

    /**
     * 处理控制消息
     */
    private void handleControlMessage(Session session, String userId, WebSocketMessage message) {
        String type = message.getType();
        
        switch (type) {
            case "ping":
                // 心跳响应
                sendMessage(session, createResponse("pong", "心跳响应", String.valueOf(System.currentTimeMillis())));
                break;
            case "start_transcription":
                // 开始转写
                sendMessage(session, createResponse("transcription_started", "开始语音转写", null));
                break;
            case "stop_transcription":
                // 停止转写
                sendMessage(session, createResponse("transcription_stopped", "停止语音转写", null));
                break;
            case "get_status":
                // 获取状态
                sendMessage(session, createResponse("status", "连接正常", userId));
                break;
            default:
                sendMessage(session, createResponse("unknown_command", "未知命令: " + type, null));
                break;
        }
    }

    /**
     * 接收到二进制消息（音频数据）
     */
    @OnMessage
    public void onMessage(Session session, ByteBuffer byteBuffer) {
        String sessionId = session.getId();
        String userId = sessionUserMap.get(sessionId);
        
        log.debug("收到音频数据，userId: {}, sessionId: {}, 数据大小: {} bytes", 
                userId, sessionId, byteBuffer.remaining());
        
        try {
            // 将ByteBuffer转换为字节数组
            byte[] audioData = new byte[byteBuffer.remaining()];
            byteBuffer.get(audioData);
            
            // 创建输入流
            ByteArrayInputStream audioInputStream = new ByteArrayInputStream(audioData);
            
            // 调用讯飞实时转写服务
            xunfeiAudioService.realTimeAudioToText(audioInputStream, new XunfeiAudioService.AudioResultCallback() {
                @Override
                public void onResult(String result) {
                    // 实时发送转写结果给前端
                    sendMessage(session, createResponse("transcription", "实时转写结果", result));
                }
            }).whenComplete((finalResult, throwable) -> {
                if (throwable != null) {
                    log.error("语音转写失败，userId: {}, sessionId: {}", userId, sessionId, throwable);
                    sendMessage(session, createResponse("error", "语音转写失败: " + throwable.getMessage(), null));
                } else {
                    log.info("语音转写完成，userId: {}, sessionId: {}, 最终结果: {}", userId, sessionId, finalResult);
                    sendMessage(session, createResponse("final", "转写完成", finalResult));
                }
            });
            
        } catch (Exception e) {
            log.error("处理音频数据失败，userId: {}, sessionId: {}", userId, sessionId, e);
            sendMessage(session, createResponse("error", "处理音频数据失败: " + e.getMessage(), null));
        }
    }

    /**
     * WebSocket连接关闭
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        String sessionId = session.getId();
        String userId = sessionUserMap.get(sessionId);
        
        // 清理会话信息
        if (userId != null) {
            sessions.remove(userId);
            sessionUserMap.remove(sessionId);
        }
        
        log.info("WebSocket连接关闭，userId: {}, sessionId: {}, 关闭原因: {}", 
                userId, sessionId, closeReason.getReasonPhrase());
    }

    /**
     * WebSocket连接错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        String sessionId = session.getId();
        String userId = sessionUserMap.get(sessionId);
        
        log.error("WebSocket连接错误，userId: {}, sessionId: {}", userId, sessionId, error);
        sendMessage(session, createResponse("error", "WebSocket连接错误: " + error.getMessage(), null));
    }

    /**
     * 发送消息给客户端
     */
    private void sendMessage(Session session, String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("发送消息失败，sessionId: {}", session.getId(), e);
            }
        }
    }

    /**
     * 主动向指定用户发送消息
     */
    public static void sendMessageToUser(String userId, String type, String message, String data) {
        Session session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String jsonMessage = createStaticResponse(type, message, data);
                session.getBasicRemote().sendText(jsonMessage);
                log.info("主动发送消息给用户，userId: {}, type: {}, message: {}", userId, type, message);
            } catch (IOException e) {
                log.error("主动发送消息失败，userId: {}", userId, e);
            }
        } else {
            log.warn("用户不在线或会话已关闭，userId: {}", userId);
        }
    }

    /**
     * 广播消息给所有在线用户
     */
    public static void broadcastMessage(String type, String message, String data) {
        String jsonMessage = createStaticResponse(type, message, data);
        sessions.forEach((userId, session) -> {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(jsonMessage);
                    log.debug("广播消息给用户，userId: {}, type: {}", userId, type);
                } catch (IOException e) {
                    log.error("广播消息失败，userId: {}", userId, e);
                }
            }
        });
        log.info("广播消息完成，type: {}, message: {}, 在线用户数: {}", type, message, sessions.size());
    }

    /**
     * 获取在线用户列表
     */
    public static java.util.Set<String> getOnlineUsers() {
        return sessions.keySet();
    }

    /**
     * 检查用户是否在线
     */
    public static boolean isUserOnline(String userId) {
        Session session = sessions.get(userId);
        return session != null && session.isOpen();
    }

    /**
     * 启动心跳检测
     */
    private void startHeartbeat(Session session, String userId) {
        heartbeatExecutor.scheduleAtFixedRate(() -> {
            if (session.isOpen()) {
                sendMessage(session, createResponse("heartbeat", "心跳检测", String.valueOf(System.currentTimeMillis())));
            }
        }, 30, 30, TimeUnit.SECONDS); // 每30秒发送一次心跳
    }

    /**
     * 创建响应消息
     */
    private String createResponse(String type, String message, String data) {
        WebSocketResponse response = new WebSocketResponse();
        response.setType(type);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(System.currentTimeMillis());
        return JSON.toJSONString(response);
    }

    /**
     * 创建静态响应消息（用于静态方法）
     */
    private static String createStaticResponse(String type, String message, String data) {
        WebSocketResponse response = new WebSocketResponse();
        response.setType(type);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(System.currentTimeMillis());
        return JSON.toJSONString(response);
    }

    /**
     * WebSocket响应消息结构
     */
    @Data
    public static class WebSocketResponse {
        private String type;
        private String message;
        private String data;
        private Long timestamp;
    }
}