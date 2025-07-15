package com.hewei.hzyjy.xunzhi.dto.resp.agent;

import lombok.Data;

/**
 * 雷达图数据传输对象
 */
@Data
public class RadarChartDTO {
    
    /**
     * 简历评估得分 (0-1)
     */
    private Float resumeScore;
    
    /**
     * 面试表现得分 (0-1)
     */
    private Float interviewPerformance;
    
    /**
     * 神态管理评分 (0-1)
     */
    private Float demeanorEvaluation;
    
    /**
     * 用户潜力指数 (0-1)
     */
    private Float potentialIndex;
    
    /**
     * 专业技能评分 (0-1)
     */
    private Float professionalSkills;
}