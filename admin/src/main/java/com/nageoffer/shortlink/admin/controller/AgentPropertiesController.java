package com.nageoffer.shortlink.admin.controller;


import com.nageoffer.shortlink.admin.common.convention.result.PageInfo;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.dto.req.agent.AgentPropertiesReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.agent.AgentPropertiesRespDTO;
import com.nageoffer.shortlink.admin.service.AgentPropertiesService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * agent配置管理层
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/short-link/admin/agentProperties")
public class AgentPropertiesController {

    private final AgentPropertiesService agentPropertiesService;

    /**
     * 创建agent配置
     * @param requestParam
     * @return
     */
    @PostMapping("create")
    public Result<Void> create(@RequestBody AgentPropertiesReqDTO requestParam) {
        agentPropertiesService.create(requestParam);
        return Results.success();
    }

    /**
     * 根据id删除agent配置
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id) {
        agentPropertiesService.delete(id);
        return Results.success();
    }

    /**
     * 更新agent配置
     * @param requestParam
     * @return
     */
    @PutMapping
    public Result<Void> update(@RequestBody AgentPropertiesReqDTO requestParam) {
        agentPropertiesService.update(requestParam);
        return Results.success();
    }

    /**
     * 根据名称查询agent配置
     * @param name
     * @return
     */
    @GetMapping("/byName")
    public Result<AgentPropertiesRespDTO> getByName(@RequestParam("name") String name) {
        return Results.success(agentPropertiesService.getByName(name));
    }

    /**
     * 分页查询agent配置
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageInfo<AgentPropertiesRespDTO>> getByPage(AgentPropertiesReqDTO requestParam) {
        return Results.success(agentPropertiesService.getByPage(requestParam));
    }
}