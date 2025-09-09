package com.hewei.hzyjy.xunzhi.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 面试记录实体
 */
@Data
@TableName("interview_record")
public class InterviewRecordDO {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 会话ID
     */
    @TableField("session_id")
    private String sessionId;

    /**
     * 面试得分
     */
    @TableField("interview_score")
    private Integer interviewScore;

    /**
     * 面试建议
     */
    @TableField("interview_suggestions")
    private String interviewSuggestions;

    /**
     * 面试方向
     */
    @TableField("interview_direction")
    private String interviewDirection;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    @TableField("del_flag")
    private Integer delFlag;
}