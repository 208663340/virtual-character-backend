# Sa-Token 集成指南

## 概述

项目已成功集成 Sa-Token 权限认证框架，替换了原有的自定义鉴权系统。Sa-Token 是一个轻量级 Java 权限认证框架，主要解决：登录认证、权限认证、Session会话、单点登录、OAuth2.0 等一系列权限相关问题。

## 主要变更

### 1. 依赖变更
- 添加了 `sa-token-spring-boot-starter` 和 `sa-token-dao-redis-jackson` 依赖
- 移除了原有的 `TokenValidationAspect` 自定义鉴权切面

### 2. 配置文件
- 新增 `application-satoken.yml` 配置文件
- 在主配置文件中引入 Sa-Token 配置

### 3. 核心类
- `SaTokenConfig.java`: Sa-Token 拦截器配置
- `StpInterfaceImpl.java`: 权限验证接口实现

### 4. 接口变更

#### 登录接口 `/api/xunzhi-agent/admin/v1/user/login`
**变更前:**
```json
{
  "token": "uuid-string"
}
```

**变更后:**
```json
{
  "token": "sa-token-value",
  "username": "用户名",
  "isAdmin": true/false
}
```

#### 检查登录状态 `/api/xunzhi-agent/admin/v1/user/check-login`
**变更前:** 需要传递 username 和 token 参数
**变更后:** 无需参数，自动从请求头获取token

返回格式:
```json
{
  "isLogin": true/false,
  "username": "用户名",
  "token": "当前token值"
}
```

#### 退出登录 `/api/xunzhi-agent/admin/v1/user/logout`
**变更前:** 需要传递 username 和 token 参数
**变更后:** 无需参数，自动处理当前登录用户的退出

#### 管理员检查 `/api/xunzhi-agent/admin/v1/user/is-admin`
**变更前:** 需要传递 username 参数
**变更后:** 无需参数，自动检查当前登录用户

## 前端调用方式

### 1. 登录
```javascript
// 登录请求
const loginResponse = await fetch('/api/xunzhi-agent/admin/v1/user/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    username: 'your_username',
    password: 'your_password'
  })
});

const result = await loginResponse.json();
const token = result.data.token;

// 保存token到localStorage或sessionStorage
localStorage.setItem('satoken', token);
```

### 2. 携带Token访问接口
```javascript
// 获取保存的token
const token = localStorage.getItem('satoken');

// 在请求头中携带token
const response = await fetch('/api/xunzhi-agent/admin/v1/user/info', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  }
});
```

### 3. 检查登录状态
```javascript
const checkResponse = await fetch('/api/xunzhi-agent/admin/v1/user/check-login', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`
  }
});

const result = await checkResponse.json();
if (result.data.isLogin) {
  console.log('用户已登录:', result.data.username);
} else {
  console.log('用户未登录');
}
```

### 4. 退出登录
```javascript
const logoutResponse = await fetch('/api/xunzhi-agent/admin/v1/user/logout', {
  method: 'DELETE',
  headers: {
    'Authorization': `Bearer ${token}`
  }
});

// 清除本地保存的token
localStorage.removeItem('satoken');
```

## 权限控制

### 角色权限
- `admin`: 管理员角色，可以访问所有管理员功能
- `user`: 普通用户角色

### 权限注解
- `@SaCheckRole("admin")`: 检查用户是否具有管理员角色
- `@SaCheckPermission("admin")`: 检查用户是否具有管理员权限

## 配置说明

### Sa-Token 主要配置项
- `token-name`: token名称，默认为 "satoken"
- `timeout`: token有效期，默认30天
- `activity-timeout`: token临时有效期，默认30分钟无操作过期
- `is-concurrent`: 是否允许同一账号并发登录
- `token-prefix`: token前缀，默认为 "Bearer"

## 注意事项

1. **Token格式变更**: 新的token不再是简单的UUID，而是Sa-Token生成的token
2. **请求头格式**: 建议使用 `Authorization: Bearer {token}` 格式
3. **自动过期**: Sa-Token会自动处理token过期，无需手动管理
4. **权限控制**: 管理员相关接口已添加权限验证，非管理员无法访问
5. **Redis依赖**: Sa-Token使用Redis存储会话信息，确保Redis服务正常运行

## 故障排除

1. **401 Unauthorized**: 检查token是否正确携带在请求头中
2. **403 Forbidden**: 检查用户是否具有相应的角色或权限
3. **Token失效**: 检查token是否过期，需要重新登录
4. **Redis连接**: 确保Redis服务正常，Sa-Token依赖Redis存储会话