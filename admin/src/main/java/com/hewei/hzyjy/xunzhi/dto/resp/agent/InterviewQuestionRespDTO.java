package com.hewei.hzyjy.xunzhi.dto.resp.agent;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 面试题响应DTO
 */
@Data
public class InterviewQuestionRespDTO {

    /**
     * ID
     */
    private String id;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 智能体ID
     */
    private Long agentId;

    /**
     * 面试题列表
     */
    private List<String> questions;

    /**
     * 建议列表
     */
    private List<String> suggestions;

    /**
     * 面试类型（如：backend、frontend等）
     */
    private String interviewType;

    /**
     * 简历文件URL
     */
    private String resumeFileUrl;

    /**
     * 响应时间(毫秒)
     */
    private Integer responseTime;

    /**
     * Token消耗数量
     */
    private Integer tokenCount;

    /**
     * 是否成功
     */
    private Integer isSuccess = 1;

    /**
     * 错误信息（如果处理失败）
     */
    private String errorMessage;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}