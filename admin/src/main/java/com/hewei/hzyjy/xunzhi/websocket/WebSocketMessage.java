package com.hewei.hzyjy.xunzhi.websocket;

import lombok.Data;

/**
 * WebSocket消息结构
 * 用于处理前端发送的JSON格式控制消息
 */
@Data
public class WebSocketMessage {
    
    /**
     * 消息类型
     * ping - 心跳检测
     * start_transcription - 开始转写
     * stop_transcription - 停止转写
     * get_status - 获取状态
     */
    private String type;
    
    /**
     * 消息内容
     */
    private String message;
    
    /**
     * 消息数据
     */
    private String data;
    
    /**
     * 时间戳
     */
    private Long timestamp;
}