package com.hewei.hzyjy.xunzhi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hewei.hzyjy.xunzhi.dao.entity.AiCharacterDO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterCreateReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterPageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterSearchReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterUpdateReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.AiCharacterRespDTO;

import java.util.List;

/**
 * AI角色Service接口
 * @author nageoffer
 */
public interface AiCharacterService extends IService<AiCharacterDO> {
    
    /**
     * 创建AI角色
     */
    void createAiCharacter(AiCharacterCreateReqDTO requestParam);
    
    /**
     * 更新AI角色
     */
    void updateAiCharacter(AiCharacterUpdateReqDTO requestParam);
    
    /**
     * 删除AI角色
     */
    void deleteAiCharacter(Long id);
    
    /**
     * 根据ID查询AI角色
     */
    AiCharacterRespDTO getAiCharacterById(Long id);
    
    /**
     * 分页查询AI角色列表
     */
    IPage<AiCharacterRespDTO> pageAiCharacters(AiCharacterPageReqDTO requestParam);
    
    /**
     * 查询所有AI角色
     */
    List<AiCharacterRespDTO> listAllAiCharacters();
    
    /**
     * 根据名称搜索AI角色
     */
    List<AiCharacterRespDTO> searchAiCharactersByName(AiCharacterSearchReqDTO requestParam);
}