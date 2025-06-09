/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nageoffer.shortlink.xunzhi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.nageoffer.shortlink.xunzhi.common.convention.result.Result;
import com.nageoffer.shortlink.xunzhi.common.convention.result.Results;
import com.nageoffer.shortlink.xunzhi.dto.req.user.UserLoginReqDTO;
import com.nageoffer.shortlink.xunzhi.dto.req.user.UserRegisterReqDTO;
import com.nageoffer.shortlink.xunzhi.dto.req.user.UserUpdateReqDTO;
import com.nageoffer.shortlink.xunzhi.dto.resp.user.UserActualRespDTO;
import com.nageoffer.shortlink.xunzhi.dto.resp.user.UserLoginRespDTO;
import com.nageoffer.shortlink.xunzhi.dto.resp.user.UserRespDTO;
import com.nageoffer.shortlink.xunzhi.dto.req.admin.AdminUserReqDTO; // Assuming this DTO will be created
import com.nageoffer.shortlink.xunzhi.service.AdminPermissionService;
import com.nageoffer.shortlink.xunzhi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理控制层
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AdminPermissionService adminPermissionService;

    // TODO: Define AdminUserDTO, AdminUserPageReqDTO, AdminUserRespDTO etc. in respective packages
    // TODO: Add corresponding methods in AdminPermissionService and its implementation

    /**
     * 根据用户名查询用户信息
     */
    @GetMapping("/api/xunzhi-agent/admin/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }

    /**
     * 根据用户名查询无脱敏用户信息
     */
    @GetMapping("/api/xunzhi-agent/admin/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDTO.class));
    }

    /**
     * 查询用户名是否存在
     */
    @GetMapping("/api/xunzhi-agent/admin/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 注册用户
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam) {
        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 修改用户
     */
    @PutMapping("/api/xunzhi-agent/admin/v1/user")
    public Result<Void> update(@RequestBody UserUpdateReqDTO requestParam) {
        userService.update(requestParam);
        return Results.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam) {
        return Results.success(userService.login(requestParam));
    }

    /**
     * 检查用户是否登录
     */
    @GetMapping("/api/xunzhi-agent/admin/v1/user/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username, @RequestParam("token") String token) {
        return Results.success(userService.checkLogin(username, token));
    }

    /**
     * 用户退出登录
     */
    @DeleteMapping("/api/xunzhi-agent/admin/v1/user/logout")
    public Result<Void> logout(@RequestParam("username") String username, @RequestParam("token") String token) {
        userService.logout(username, token);
        return Results.success();
    }

    /**
     * 校验当前用户是否为管理员
     */
    @GetMapping("/api/xunzhi-agent/admin/v1/user/is-admin")
    public Result<Boolean> isAdmin(@RequestParam("username") String username) {
        return Results.success(adminPermissionService.isAdmin(username));
    }

    /**
     * 添加管理员
     */
    @PostMapping("/api/xunzhi-agent/admin/v1/admin")
    public Result<Void> addAdmin(@RequestBody AdminUserReqDTO requestParam) {
        adminPermissionService.setAdminByUserId(requestParam.getUserId());
        return Results.success();
    }

    /**
     * 删除管理员
     */
//    @DeleteMapping("/api/xunzhi-agent/admin/v1/admin/{username}")
//    public Result<Void> deleteAdmin(@PathVariable("username") String username) {
//         adminPermissionService.deleteAdmin(username);
//        return Results.success();
//    }

    /**
     * 修改管理员
     */
//    @PutMapping("/api/xunzhi-agent/admin/v1/admin")
//    public Result<Void> updateAdmin(@RequestBody /*AdminUserDTO*/ Object requestParam) { // Placeholder for AdminUserDTO
//         adminPermissionService.updateAdmin(requestParam);
//        return Results.success();
//    }

    /**
     * 根据用户名查询管理员信息
     */
//    @GetMapping("/api/xunzhi-agent/admin/v1/admin/{username}")
//    public Result</*AdminUserRespDTO*/ Object> getAdminByUsername(@PathVariable("username") String username) { // Placeholder for AdminUserRespDTO
//         return Results.success(adminPermissionService.getAdminByUsername(username));
//    }

    /**
     * 分页查询所有管理员信息
     */
//    @GetMapping("/api/xunzhi-agent/admin/v1/admins")
//    public Result</*Page<AdminUserRespDTO>*/ Object> listAdmins(/*@RequestBody AdminUserPageReqDTO requestParam*/) { // Placeholders
//         return Results.success(adminPermissionService.listAdmins(requestParam));
//    }
}
