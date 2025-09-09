package com.hewei.hzyjy.xunzhi.dto.resp.interview;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 面试记录响应DTO
 */
@Data
public class InterviewRecordRespDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 面试得分
     */
    private Integer interviewScore;

    /**
     * 面试建议（原始字符串格式）
     */
    private String interviewSuggestions;
    
    /**
     * 面试建议（解析后的Map格式）
     * key为编号，value为建议内容
     */
    private Map<String, String> interviewSuggestionsMap;

    /**
     * 面试方向
     */
    private String interviewDirection;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}