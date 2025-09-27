package com.hewei.hzyjy.xunzhi.dto.req.ai;

import com.hewei.hzyjy.xunzhi.dao.entity.AiCharacterDO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * AI角色分页查询请求DTO
 * @author nageoffer
 */
@Data
public class AiCharacterPageReqDTO extends Page<AiCharacterDO> {
    
    /**
     * AI名称（模糊查询）
     */
    private String aiName;
    
    /**
     * 构造函数，设置默认分页参数
     */
    public AiCharacterPageReqDTO() {
        super(1, 10); // 默认第1页，每页10条
    }
    
    /**
     * 构造函数，支持自定义分页参数
     */
    public AiCharacterPageReqDTO(long current, long size) {
        super(current, size);
    }
}