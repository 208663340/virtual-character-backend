package com.hewei.hzyjy.xunzhi.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hewei.hzyjy.xunzhi.common.database.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AI角色实体类
 * @author nageoffer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ai_character")
public class AiCharacterDO extends BaseDO {
    
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * AI名称
     */
    private String aiName;
    
    /**
     * AI头像URL
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