package com.hewei.hzyjy.xunzhi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hewei.hzyjy.xunzhi.common.convention.exception.ClientException;
import com.hewei.hzyjy.xunzhi.dao.entity.AiCharacterDO;
import com.hewei.hzyjy.xunzhi.dao.mapper.AiCharacterMapper;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterCreateReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterPageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterSearchReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiCharacterUpdateReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.AiCharacterRespDTO;
import com.hewei.hzyjy.xunzhi.service.AiCharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AI角色Service实现类
 * @author nageoffer
 */
@Service
@RequiredArgsConstructor
public class AiCharacterServiceImpl extends ServiceImpl<AiCharacterMapper, AiCharacterDO> implements AiCharacterService {
    
    @Override
    public void createAiCharacter(AiCharacterCreateReqDTO requestParam) {
        // 检查AI角色名称是否已存在
        LambdaQueryWrapper<AiCharacterDO> queryWrapper = Wrappers.lambdaQuery(AiCharacterDO.class)
                .eq(AiCharacterDO::getAiName, requestParam.getAiName())
                .eq(AiCharacterDO::getDelFlag, 0);
        
        if (baseMapper.selectCount(queryWrapper) > 0) {
            throw new ClientException("AI角色名称已存在");
        }
        
        AiCharacterDO aiCharacterDO = new AiCharacterDO();
        BeanUtil.copyProperties(requestParam, aiCharacterDO);
        aiCharacterDO.setCreateTime(new Date());
        aiCharacterDO.setUpdateTime(new Date());
        aiCharacterDO.setDelFlag(0);
        
        baseMapper.insert(aiCharacterDO);
    }
    
    @Override
    public void updateAiCharacter(AiCharacterUpdateReqDTO requestParam) {
        // 检查记录是否存在
        AiCharacterDO existingRecord = baseMapper.selectById(requestParam.getId());
        if (existingRecord == null || existingRecord.getDelFlag() == 1) {
            throw new ClientException("AI角色不存在");
        }
        
        // 如果修改了AI角色名称，检查新名称是否已存在
        if (StrUtil.isNotBlank(requestParam.getAiName()) && !requestParam.getAiName().equals(existingRecord.getAiName())) {
            LambdaQueryWrapper<AiCharacterDO> queryWrapper = Wrappers.lambdaQuery(AiCharacterDO.class)
                    .eq(AiCharacterDO::getAiName, requestParam.getAiName())
                    .eq(AiCharacterDO::getDelFlag, 0)
                    .ne(AiCharacterDO::getId, requestParam.getId());
            
            if (baseMapper.selectCount(queryWrapper) > 0) {
                throw new ClientException("AI角色名称已存在");
            }
        }
        
        AiCharacterDO aiCharacterDO = new AiCharacterDO();
        BeanUtil.copyProperties(requestParam, aiCharacterDO);
        aiCharacterDO.setUpdateTime(new Date());
        
        baseMapper.updateById(aiCharacterDO);
    }
    
    @Override
    public void deleteAiCharacter(Long id) {
        AiCharacterDO existingRecord = baseMapper.selectById(id);
        if (existingRecord == null || existingRecord.getDelFlag() == 1) {
            throw new ClientException("AI角色不存在");
        }
        
        LambdaUpdateWrapper<AiCharacterDO> updateWrapper = Wrappers.lambdaUpdate(AiCharacterDO.class)
                .eq(AiCharacterDO::getId, id)
                .set(AiCharacterDO::getDelFlag, 1)
                .set(AiCharacterDO::getUpdateTime, new Date());
        
        baseMapper.update(null, updateWrapper);
    }
    
    @Override
    public AiCharacterRespDTO getAiCharacterById(Long id) {
        AiCharacterDO aiCharacterDO = baseMapper.selectById(id);
        if (aiCharacterDO == null || aiCharacterDO.getDelFlag() == 1) {
            throw new ClientException("AI角色不存在");
        }
        
        AiCharacterRespDTO respDTO = new AiCharacterRespDTO();
        BeanUtil.copyProperties(aiCharacterDO, respDTO);
        return respDTO;
    }
    
    @Override
    public IPage<AiCharacterRespDTO> pageAiCharacters(AiCharacterPageReqDTO requestParam) {
        LambdaQueryWrapper<AiCharacterDO> queryWrapper = Wrappers.lambdaQuery(AiCharacterDO.class)
                .eq(AiCharacterDO::getDelFlag, 0)
                .like(StrUtil.isNotBlank(requestParam.getAiName()), AiCharacterDO::getAiName, requestParam.getAiName())
                .orderByDesc(AiCharacterDO::getCreateTime);
        
        Page<AiCharacterDO> page = new Page<>(requestParam.getCurrent(), requestParam.getSize());
        IPage<AiCharacterDO> aiCharacterPage = baseMapper.selectPage(page, queryWrapper);
        
        return aiCharacterPage.convert(item -> {
            AiCharacterRespDTO respDTO = new AiCharacterRespDTO();
            BeanUtil.copyProperties(item, respDTO);
            return respDTO;
        });
    }
    
    @Override
    public List<AiCharacterRespDTO> listAllAiCharacters() {
        LambdaQueryWrapper<AiCharacterDO> queryWrapper = Wrappers.lambdaQuery(AiCharacterDO.class)
                .eq(AiCharacterDO::getDelFlag, 0)
                .orderByDesc(AiCharacterDO::getCreateTime);
        
        List<AiCharacterDO> aiCharacterList = baseMapper.selectList(queryWrapper);
        
        return aiCharacterList.stream()
                .map(item -> {
                    AiCharacterRespDTO respDTO = new AiCharacterRespDTO();
                    BeanUtil.copyProperties(item, respDTO);
                    return respDTO;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AiCharacterRespDTO> searchAiCharactersByName(AiCharacterSearchReqDTO requestParam) {
        LambdaQueryWrapper<AiCharacterDO> queryWrapper = Wrappers.lambdaQuery(AiCharacterDO.class)
                .eq(AiCharacterDO::getDelFlag, 0)
                .like(StrUtil.isNotBlank(requestParam.getAiName()), AiCharacterDO::getAiName, requestParam.getAiName())
                .orderByDesc(AiCharacterDO::getCreateTime);
        
        List<AiCharacterDO> aiCharacterList = baseMapper.selectList(queryWrapper);
        
        return aiCharacterList.stream()
                .map(item -> {
                    AiCharacterRespDTO respDTO = new AiCharacterRespDTO();
                    BeanUtil.copyProperties(item, respDTO);
                    return respDTO;
                })
                .collect(Collectors.toList());
    }
}