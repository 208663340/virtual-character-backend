package com.hewei.hzyjy.xunzhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterCreateReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterPageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterSearchReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterUpdateReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.AiCharacterRespDTO;
import com.hewei.hzyjy.xunzhi.service.AiCharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI角色控制器
 * @author nageoffer
 */
@RestController
@RequestMapping("/api/ai-character")
@RequiredArgsConstructor
public class AiCharacterController {
    
    private final AiCharacterService aiCharacterService;
    
    /**
     * 创建AI角色
     */
    @PostMapping
    public Result<Void> createAiCharacter(@RequestBody AiCharacterCreateReqDTO requestParam) {
        aiCharacterService.createAiCharacter(requestParam);
        return Results.success();
    }
    
    /**
     * 更新AI角色
     */
    @PutMapping
    public Result<Void> updateAiCharacter(@RequestBody AiCharacterUpdateReqDTO requestParam) {
        aiCharacterService.updateAiCharacter(requestParam);
        return Results.success();
    }
    
    /**
     * 删除AI角色
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteAiCharacter(@PathVariable Long id) {
        aiCharacterService.deleteAiCharacter(id);
        return Results.success();
    }
    
    /**
     * 根据ID查询AI角色
     */
    @GetMapping("/{id}")
    public Result<AiCharacterRespDTO> getAiCharacterById(@PathVariable Long id) {
        AiCharacterRespDTO result = aiCharacterService.getAiCharacterById(id);
        return Results.success(result);
    }
    
    /**
     * 分页查询AI角色列表
     */
    @PostMapping("/page")
    public Result<IPage<AiCharacterRespDTO>> pageAiCharacters(@RequestBody AiCharacterPageReqDTO requestParam) {
        IPage<AiCharacterRespDTO> result = aiCharacterService.pageAiCharacters(requestParam);
        return Results.success(result);
    }
    
    /**
     * 查询所有AI角色
     */
    @GetMapping("/list")
    public Result<List<AiCharacterRespDTO>> listAllAiCharacters() {
        List<AiCharacterRespDTO> result = aiCharacterService.listAllAiCharacters();
        return Results.success(result);
    }
    
    /**
     * 根据名称搜索AI角色
     */
    @PostMapping("/search")
    public Result<List<AiCharacterRespDTO>> searchAiCharactersByName(@RequestBody AiCharacterSearchReqDTO requestParam) {
        List<AiCharacterRespDTO> result = aiCharacterService.searchAiCharactersByName(requestParam);
        return Results.success(result);
    }
}