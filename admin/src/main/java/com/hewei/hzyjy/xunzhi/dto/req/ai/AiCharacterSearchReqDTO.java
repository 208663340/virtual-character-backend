package com.hewei.hzyjy.xunzhi.dto.req.ai;

import lombok.Data;

/**
 * AI角色搜索请求DTO
 * @author nageoffer
 */
@Data
public class AiCharacterSearchReqDTO {
    
    /**
     * AI名称（模糊查询）
     */
    private String aiName;
}