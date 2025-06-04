package com.nageoffer.shortlink.admin.dto.req.user;

import lombok.Data;

@Data
public class UserMessageReqDTO {

    /**
     * 用户名
     */
    private String userName;

    /**
     * AgentID
     */
    private Long agentId;

    /**
     * 用户输入信息
     */
    private String inputMessage;

    /**
     * 消息序号
     */
    private int messageSeq;

    /**
     * 会话ID（可选，首次对话时为空，后续对话需要传入）
     */
    private String sessionId;
}
