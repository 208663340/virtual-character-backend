package com.hewei.hzyjy.xunzhi.controller;

import com.hewei.hzyjy.xunzhi.websocket.WebSocketMessageService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * WebSocket管理控制器
 * 提供WebSocket连接管理和消息发送的REST API接口
 */
@RestController
@RequestMapping("/api/xunzhi/v1/websocket")
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketMessageService webSocketMessageService;


    @GetMapping("/user/{userId}/status")
    public ResponseEntity<Map<String, Object>> checkUserStatus(@PathVariable String userId) {
        boolean isOnline = webSocketMessageService.isUserOnline(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("isOnline", isOnline);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/send-message")
    public ResponseEntity<Map<String, Object>> sendMessage(
            @RequestParam String userId,
            @RequestParam String type,
            @RequestParam String message,
            @RequestParam(required = false) String data) {
        
        boolean success = webSocketMessageService.sendMessageToUser(userId, type, message, data);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "消息发送成功" : "消息发送失败");
        return ResponseEntity.ok(result);
    }



    @PostMapping("/notification/{userId}")
    public ResponseEntity<Map<String, Object>> sendNotification(
            @PathVariable String userId,
            @RequestParam String message) {
        
        boolean success = webSocketMessageService.sendSystemNotification(userId, message);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "通知发送成功" : "通知发送失败");
        return ResponseEntity.ok(result);
    }


    @PostMapping("/transcription/{userId}")
    public ResponseEntity<Map<String, Object>> sendTranscriptionResult(
            @PathVariable String userId,
            @RequestParam String result,
            @RequestParam(defaultValue = "false") boolean isFinal) {
        
        boolean success = webSocketMessageService.sendTranscriptionResult(userId, result, isFinal);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "转写结果发送成功" : "转写结果发送失败");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/error/{userId}")
    public ResponseEntity<Map<String, Object>> sendErrorMessage(
            @PathVariable String userId,
            @RequestParam String errorMessage) {
        
        boolean success = webSocketMessageService.sendErrorMessage(userId, errorMessage);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "错误消息发送成功" : "错误消息发送失败");
        return ResponseEntity.ok(result);
    }
}