package com.hewei.hzyjy.xunzhi.dto.resp.ai;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * AI角色响应DTO
 * @author nageoffer
 */
@Data
public class AiCharacterRespDTO {
    
    /**
     * ID
     */
    private Long id;
    
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
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}