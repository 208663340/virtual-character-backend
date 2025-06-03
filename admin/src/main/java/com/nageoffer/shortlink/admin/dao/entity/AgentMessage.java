package com.nageoffer.shortlink.admin.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 智能体消息详情表
 * @TableName agent_message_0
 */
@Data
@TableName("agent_message")
public class AgentMessage{
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
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
     * 消息序号，同一会话内递增
     */
    private Integer messageSeq;

    /**
     * 父消息ID，用于消息关联
     */
    private Long parentMsgId;

    /**
     * Token消耗数量
     */
    private Integer tokenCount;

    /**
     * 响应时间(毫秒)
     */
    private Integer responseTime;

    /**
     * 错误信息（如果处理失败）
     */
    private String errorMessage;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    private Integer delFlag;

}