package com.hewei.hzyjy.xunzhi.dto.req.ai;

import lombok.Data;

/**
 * AI角色创建请求DTO
 * @author nageoffer
 */
@Data
public class AiCharacterCreateReqDTO {
    
    /**
     * AI名称
     */
    private String aiName;
    
    /**
     * AI头像
     */
    private String aiAvatar;
    
    /**
     * AI角色描述
     */
    private String description;
    
    /**
     * AI提示词
     */
    private String aiPrompt;
    
    /**
     * 音色详情ID
     */
    private Long voiceDetailId;
}