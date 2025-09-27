package com.hewei.hzyjy.xunzhi.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * WebSocket消息管理服务
 * 提供WebSocket会话管理和消息发送的高级接口
 */
@Slf4j
@Service
public class WebSocketMessageService {

    /**
     * 发送消息给指定用户
     */
    public boolean sendMessageToUser(String userId, String type, String message, String data) {
        try {
            AudioTranscriptionWebSocketHandler.sendMessageToUser(userId, type, message, data);
            return true;
        } catch (Exception e) {
            log.error("发送消息给用户失败，userId: {}", userId, e);
            return false;
        }
    }

    /**
     * 异步发送消息给指定用户
     */
    public CompletableFuture<Boolean> sendMessageToUserAsync(String userId, String type, String message, String data) {
        return CompletableFuture.supplyAsync(() -> sendMessageToUser(userId, type, message, data));
    }

//    /**
//     * 广播消息给所有在线用户
//     */
//    public void broadcastMessage(String type, String message, String data) {
//        try {
//            AudioTranscriptionWebSocketHandler.broadcastMessage(type, message, data);
//        } catch (Exception e) {
//            log.error("广播消息失败", e);
//        }
//    }

//    /**
//     * 异步广播消息给所有在线用户
//     */
//    public CompletableFuture<Void> broadcastMessageAsync(String type, String message, String data) {
//        return CompletableFuture.runAsync(() -> broadcastMessage(type, message, data));
//    }

//    /**
//     * 获取在线用户列表
//     */
//    public Set<String> getOnlineUsers() {
//        return AudioTranscriptionWebSocketHandler.getOnlineUsers();
//    }

    /**
     * 检查用户是否在线
     */
    public boolean isUserOnline(String userId) {
        return AudioTranscriptionWebSocketHandler.isUserOnline(userId);
    }

//    /**
//     * 获取在线用户数量
//     */
//    public int getOnlineUserCount() {
//        return getOnlineUsers().size();
//    }

    /**
     * 发送系统通知给指定用户
     */
    public boolean sendSystemNotification(String userId, String message) {
        return sendMessageToUser(userId, "system_notification", message, null);
    }

//    /**
//     * 发送系统通知给所有在线用户
//     */
//    public void broadcastSystemNotification(String message) {
//        broadcastMessage("system_notification", message, null);
//    }

    /**
     * 发送语音转写结果给指定用户
     */
    public boolean sendTranscriptionResult(String userId, String result, boolean isFinal) {
        String type = isFinal ? "final" : "transcription";
        return sendMessageToUser(userId, type, "语音转写结果", result);
    }

    /**
     * 发送错误消息给指定用户
     */
    public boolean sendErrorMessage(String userId, String errorMessage) {
        return sendMessageToUser(userId, "error", errorMessage, null);
    }

    /**
     * 发送状态更新给指定用户
     */
    public boolean sendStatusUpdate(String userId, String status, String details) {
        return sendMessageToUser(userId, "status_update", status, details);
    }

    /**
     * 批量发送消息给多个用户
     */
    public void sendMessageToUsers(Set<String> userIds, String type, String message, String data) {
        userIds.forEach(userId -> {
            if (isUserOnline(userId)) {
                sendMessageToUser(userId, type, message, data);
            }
        });
    }

    /**
     * 异步批量发送消息给多个用户
     */
    public CompletableFuture<Void> sendMessageToUsersAsync(Set<String> userIds, String type, String message, String data) {
        return CompletableFuture.runAsync(() -> sendMessageToUsers(userIds, type, message, data));
    }
}