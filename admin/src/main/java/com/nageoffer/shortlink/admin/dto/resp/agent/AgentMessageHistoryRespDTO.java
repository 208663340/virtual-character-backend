package com.nageoffer.shortlink.admin.dto.resp.agent;

import lombok.Data;

import java.util.Date;

/**
 * 智能体消息历史响应DTO
 */
@Data
public class AgentMessageHistoryRespDTO {
    
    /**
     * 消息ID
     */
    private Long id;
    
    /**
     * 会话ID
     */
    private String sessionId;
    
    /**
     * 消息类型：1-用户消息，2-AI回复
     */
    private Integer messageType;
    
    /**
     * 消息内容
     */
    private String messageContent;
    
    /**
     * 消息序号
     */
    private Integer messageSeq;
    
    /**
     * Token消耗数量
     */
    private Integer tokenCount;
    
    /**
     * 响应时间(毫秒)
     */
    private Integer responseTime;
    
    /**
     * 创建时间
     */
    private Date createTime;
}