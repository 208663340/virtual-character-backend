package com.nageoffer.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 智能体会话表
 * @TableName agent_conversation
 */
@Data
@TableName("agent_conversation")
public class AgentConversation {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会话ID，UUID格式
     */
    private String sessionId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 智能体ID
     */
    private Long agentId;

    /**
     * 会话标题，可从首条消息自动生成
     */
    private String conversationTitle;

    /**
     * 消息总数
     */
    private Integer messageCount;

    /**
     * 总Token消耗
     */
    private Integer totalTokens;

    /**
     * 会话状态：1-进行中，2-已结束，3-已删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    private Integer delFlag;
}