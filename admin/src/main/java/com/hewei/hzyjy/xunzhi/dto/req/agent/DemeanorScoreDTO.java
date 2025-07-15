package com.hewei.hzyjy.xunzhi.dto.req.agent;

import lombok.Data;

/**
 * 神态评分数据传输对象
 */
@Data
public class DemeanorScoreDTO {

    /**
     * 慌乱度 (0-1)
     */
    private Float panicLevel;
    
    /**
     * 严肃程度 (0-1)
     */
    private Float seriousnessLevel;
    
    /**
     * 表情处理 (0-1)
     */
    private Float emoticonHandling;
    
    /**
     * 综合得分 (0-1)
     */
    private Float compositeScore;
}