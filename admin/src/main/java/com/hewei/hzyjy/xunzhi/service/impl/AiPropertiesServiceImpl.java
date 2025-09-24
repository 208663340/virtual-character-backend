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
import com.hewei.hzyjy.xunzhi.dao.entity.AiPropertiesDO;
import com.hewei.hzyjy.xunzhi.dao.mapper.AiPropertiesMapper;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiPropertiesCreateReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiPropertiesPageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiPropertiesUpdateReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.AiPropertiesRespDTO;
import com.hewei.hzyjy.xunzhi.service.AiPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AI配置Service实现类
 * @author nageoffer
 */
@Service
@RequiredArgsConstructor
public class AiPropertiesServiceImpl extends ServiceImpl<AiPropertiesMapper, AiPropertiesDO> implements AiPropertiesService {
    
    @Override
    public void createAiProperties(AiPropertiesCreateReqDTO requestParam) {
        // 检查AI名称是否已存在
        LambdaQueryWrapper<AiPropertiesDO> queryWrapper = Wrappers.lambdaQuery(AiPropertiesDO.class)
                .eq(AiPropertiesDO::getAiName, requestParam.getAiName())
                .eq(AiPropertiesDO::getDelFlag, 0);
        
        if (baseMapper.selectCount(queryWrapper) > 0) {
            throw new ClientException("AI名称已存在");
        }
        
        AiPropertiesDO aiPropertiesDO = new AiPropertiesDO();
        BeanUtil.copyProperties(requestParam, aiPropertiesDO);
        aiPropertiesDO.setCreateTime(new Date());
        aiPropertiesDO.setUpdateTime(new Date());
        aiPropertiesDO.setDelFlag(0);
        
        if (aiPropertiesDO.getIsEnabled() == null) {
            aiPropertiesDO.setIsEnabled(1);
        }
        
        baseMapper.insert(aiPropertiesDO);
    }
    
    @Override
    public void updateAiProperties(AiPropertiesUpdateReqDTO requestParam) {
        // 检查记录是否存在
        AiPropertiesDO existingRecord = baseMapper.selectById(requestParam.getId());
        if (existingRecord == null || existingRecord.getDelFlag() == 1) {
            throw new ClientException("AI配置不存在");
        }
        
        // 如果修改了AI名称，检查新名称是否已存在
        if (StrUtil.isNotBlank(requestParam.getAiName()) && !requestParam.getAiName().equals(existingRecord.getAiName())) {
            LambdaQueryWrapper<AiPropertiesDO> queryWrapper = Wrappers.lambdaQuery(AiPropertiesDO.class)
                    .eq(AiPropertiesDO::getAiName, requestParam.getAiName())
                    .eq(AiPropertiesDO::getDelFlag, 0)
                    .ne(AiPropertiesDO::getId, requestParam.getId());
            
            if (baseMapper.selectCount(queryWrapper) > 0) {
                throw new ClientException("AI名称已存在");
            }
        }
        
        AiPropertiesDO aiPropertiesDO = new AiPropertiesDO();
        BeanUtil.copyProperties(requestParam, aiPropertiesDO);
        aiPropertiesDO.setUpdateTime(new Date());
        
        baseMapper.updateById(aiPropertiesDO);
    }
    
    @Override
    public void deleteAiProperties(Long id) {
        AiPropertiesDO existingRecord = baseMapper.selectById(id);
        if (existingRecord == null || existingRecord.getDelFlag() == 1) {
            throw new ClientException("AI配置不存在");
        }
        
        LambdaUpdateWrapper<AiPropertiesDO> updateWrapper = Wrappers.lambdaUpdate(AiPropertiesDO.class)
                .eq(AiPropertiesDO::getId, id)
                .set(AiPropertiesDO::getDelFlag, 1)
                .set(AiPropertiesDO::getUpdateTime, new Date());
        
        baseMapper.update(null, updateWrapper);
    }
    
    @Override
    public AiPropertiesRespDTO getAiPropertiesById(Long id) {
        AiPropertiesDO aiPropertiesDO = baseMapper.selectById(id);
        if (aiPropertiesDO == null || aiPropertiesDO.getDelFlag() == 1) {
            throw new ClientException("AI配置不存在");
        }
        
        AiPropertiesRespDTO respDTO = new AiPropertiesRespDTO();
        BeanUtil.copyProperties(aiPropertiesDO, respDTO);
        
        // API密钥脱敏处理
        if (StrUtil.isNotBlank(respDTO.getApiKey())) {
            respDTO.setApiKey(maskApiKey(respDTO.getApiKey()));
        }
        
        return respDTO;
    }
    
    @Override
    public IPage<AiPropertiesRespDTO> pageAiProperties(AiPropertiesPageReqDTO requestParam) {
        LambdaQueryWrapper<AiPropertiesDO> queryWrapper = Wrappers.lambdaQuery(AiPropertiesDO.class)
                .eq(AiPropertiesDO::getDelFlag, 0)
                .like(StrUtil.isNotBlank(requestParam.getAiName()), AiPropertiesDO::getAiName, requestParam.getAiName())
                .eq(StrUtil.isNotBlank(requestParam.getAiType()), AiPropertiesDO::getAiType, requestParam.getAiType())
                .eq(requestParam.getIsEnabled() != null, AiPropertiesDO::getIsEnabled, requestParam.getIsEnabled())
                .orderByDesc(AiPropertiesDO::getCreateTime);
        
        IPage<AiPropertiesDO> page = baseMapper.selectPage(requestParam, queryWrapper);
        
        IPage<AiPropertiesRespDTO> resultPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<AiPropertiesRespDTO> records = page.getRecords().stream()
                .map(record -> {
                    AiPropertiesRespDTO respDTO = new AiPropertiesRespDTO();
                    BeanUtil.copyProperties(record, respDTO);
                    // API密钥脱敏处理
                    if (StrUtil.isNotBlank(respDTO.getApiKey())) {
                        respDTO.setApiKey(maskApiKey(respDTO.getApiKey()));
                    }
                    return respDTO;
                })
                .collect(Collectors.toList());
        
        resultPage.setRecords(records);
        return resultPage;
    }
    
    @Override
    public List<AiPropertiesRespDTO> listEnabledAiProperties() {
        LambdaQueryWrapper<AiPropertiesDO> queryWrapper = Wrappers.lambdaQuery(AiPropertiesDO.class)
                .eq(AiPropertiesDO::getDelFlag, 0)
                .eq(AiPropertiesDO::getIsEnabled, 1)
                .orderByDesc(AiPropertiesDO::getCreateTime);
        
        List<AiPropertiesDO> list = baseMapper.selectList(queryWrapper);
        
        return list.stream()
                .map(record -> {
                    AiPropertiesRespDTO respDTO = new AiPropertiesRespDTO();
                    BeanUtil.copyProperties(record, respDTO);
                    // API密钥脱敏处理
                    if (StrUtil.isNotBlank(respDTO.getApiKey())) {
                        respDTO.setApiKey(maskApiKey(respDTO.getApiKey()));
                    }
                    return respDTO;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public void toggleAiPropertiesStatus(Long id, Integer isEnabled) {
        AiPropertiesDO existingRecord = baseMapper.selectById(id);
        if (existingRecord == null || existingRecord.getDelFlag() == 1) {
            throw new ClientException("AI配置不存在");
        }
        
        LambdaUpdateWrapper<AiPropertiesDO> updateWrapper = Wrappers.lambdaUpdate(AiPropertiesDO.class)
                .eq(AiPropertiesDO::getId, id)
                .set(AiPropertiesDO::getIsEnabled, isEnabled)
                .set(AiPropertiesDO::getUpdateTime, new Date());
        
        baseMapper.update(null, updateWrapper);
    }

    @Override
    public List<AiPropertiesRespDTO> getAllEnabledAiProperties() {
        return listEnabledAiProperties();
    }

    @Override
    public AiPropertiesDO getEnabledByAiType(String aiType) {
        LambdaQueryWrapper<AiPropertiesDO> queryWrapper = Wrappers.lambdaQuery(AiPropertiesDO.class)
                .eq(AiPropertiesDO::getDelFlag, 0)
                .eq(AiPropertiesDO::getIsEnabled, 1)
                .eq(AiPropertiesDO::getAiType, aiType)
                .orderByDesc(AiPropertiesDO::getCreateTime)
                .last("LIMIT 1");
        
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public AiPropertiesDO getDefaultDoubaoConfig() {
        AiPropertiesDO doubaoConfig = getEnabledByAiType("doubao");
        if (doubaoConfig == null) {
            throw new ClientException("豆包AI配置不存在或未启用");
        }
        return doubaoConfig;
    }

    /**
     * API密钥脱敏处理
     */
    private String maskApiKey(String apiKey) {
        if (StrUtil.isBlank(apiKey) || apiKey.length() <= 8) {
            return "****";
        }
        return apiKey.substring(0, 4) + "****" + apiKey.substring(apiKey.length() - 4);
    }
}