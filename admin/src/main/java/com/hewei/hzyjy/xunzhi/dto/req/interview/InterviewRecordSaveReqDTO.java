package com.hewei.hzyjy.xunzhi.dto.req.interview;

import lombok.Data;

/**
 * 保存面试记录请求DTO
 */
@Data
public class InterviewRecordSaveReqDTO {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 面试得分
     */
    private Integer interviewScore;

    /**
     * 面试建议
     */
    private String interviewSuggestions;

    /**
     * 面试方向
     */
    private String interviewDirection;

    /**
     * 用户名
     */
    private String username;
}