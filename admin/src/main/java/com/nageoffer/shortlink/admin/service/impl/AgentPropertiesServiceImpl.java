package com.nageoffer.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.result.PageInfo;
import com.nageoffer.shortlink.admin.dao.entity.AgentPropertiesDO;
import com.nageoffer.shortlink.admin.dao.mapper.AgentPropertiesMapper;
import com.nageoffer.shortlink.admin.dto.req.agent.AgentPropertiesReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.agent.AgentPropertiesRespDTO;
import com.nageoffer.shortlink.admin.service.AgentPropertiesService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 20866
* @description 针对表【agent_properties】的数据库操作Service实现
* @createDate 2025-05-27 10:08:58
*/
@Service
public class AgentPropertiesServiceImpl extends ServiceImpl<AgentPropertiesMapper, AgentPropertiesDO>
    implements AgentPropertiesService {

    @Override
    public void create(AgentPropertiesReqDTO requestParam) {
        AgentPropertiesDO agentPropertiesDO = new AgentPropertiesDO();
        BeanUtils.copyProperties(requestParam, agentPropertiesDO);
        agentPropertiesDO.setCreateTime(new Date());
        agentPropertiesDO.setUpdateTime(new Date());
        agentPropertiesDO.setDelFlag(0);
        baseMapper.insert(agentPropertiesDO);
    }

    @Override
    public void delete(Long id) {
        LambdaUpdateWrapper<AgentPropertiesDO> updateWrapper = Wrappers.lambdaUpdate(AgentPropertiesDO.class)
                .eq(AgentPropertiesDO::getId, id)
                .set(AgentPropertiesDO::getDelFlag, 1)
                .set(AgentPropertiesDO::getUpdateTime, new Date());
        baseMapper.update(null, updateWrapper);
    }

    @Override
    public void update(AgentPropertiesReqDTO requestParam) {
        AgentPropertiesDO agentPropertiesDO = new AgentPropertiesDO();
        BeanUtils.copyProperties(requestParam, agentPropertiesDO);
        agentPropertiesDO.setUpdateTime(new Date());
        LambdaUpdateWrapper<AgentPropertiesDO> updateWrapper = Wrappers.lambdaUpdate(AgentPropertiesDO.class)
                .eq(AgentPropertiesDO::getId, requestParam.getId())
                .eq(AgentPropertiesDO::getDelFlag, 0);
        baseMapper.update(agentPropertiesDO, updateWrapper);
    }

    @Override
    public AgentPropertiesRespDTO getByName(String name) {
        LambdaQueryWrapper<AgentPropertiesDO> queryWrapper = Wrappers.lambdaQuery(AgentPropertiesDO.class)
                .eq(AgentPropertiesDO::getAgentName, name)
                .eq(AgentPropertiesDO::getDelFlag, 0);
        AgentPropertiesDO agentPropertiesDO = baseMapper.selectOne(queryWrapper);
        AgentPropertiesRespDTO result = new AgentPropertiesRespDTO();
        if (agentPropertiesDO != null) {
            BeanUtils.copyProperties(agentPropertiesDO, result);
        }
        return result;
    }

    @Override
    public PageInfo<AgentPropertiesRespDTO> getByPage(AgentPropertiesReqDTO requestParam) {
        Page<AgentPropertiesDO> page = new Page<>(requestParam.getPageNum(), requestParam.getPageSize());
        LambdaQueryWrapper<AgentPropertiesDO> queryWrapper = Wrappers.lambdaQuery(AgentPropertiesDO.class)
                .eq(AgentPropertiesDO::getDelFlag, 0)
                .orderByDesc(AgentPropertiesDO::getCreateTime);
        Page<AgentPropertiesDO> agentPropertiesDOPage = baseMapper.selectPage(page, queryWrapper);
        List<AgentPropertiesRespDTO> resultList = agentPropertiesDOPage.getRecords().stream()
                .map(item -> {
                    AgentPropertiesRespDTO respDTO = new AgentPropertiesRespDTO();
                    BeanUtils.copyProperties(item, respDTO);
                    return respDTO;
                })
                .collect(Collectors.toList());
        PageInfo<AgentPropertiesRespDTO> pageInfo = new PageInfo<>();
        pageInfo.setRecords(resultList);
        pageInfo.setTotal(agentPropertiesDOPage.getTotal());
        pageInfo.setPages(agentPropertiesDOPage.getCurrent());
        pageInfo.setSize(agentPropertiesDOPage.getSize());
        return pageInfo;
    }

    @Override
    public List<AgentPropertiesDO> listTop10() {
        LambdaQueryWrapper<AgentPropertiesDO> queryWrapper = Wrappers.lambdaQuery(AgentPropertiesDO.class)
                .eq(AgentPropertiesDO::getDelFlag, 0)
                .orderByDesc(AgentPropertiesDO::getCreateTime)
                .last("limit 10"); // Limit to top 10
        return baseMapper.selectList(queryWrapper);
    }
}




