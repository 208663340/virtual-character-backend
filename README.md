# 讯智AI智能助手后端系统

## 项目简介

讯智AI智能助手是一个基于Spring Boot 3.0的现代化AI对话系统，集成了多种主流AI大模型，提供高性能的流式对话服务。系统支持实时SSE（Server-Sent Events）推送，为用户提供流畅的AI对话体验。
 视频演示地址
https://www.bilibili.com/video/BV1o7nXzVEVm/?spm_id_from=333.1387.homepage.video_card.click&vd_source=9889d0ef5432d6b568bb0079110870e7

## 🚀 核心特性

### AI模型集成
- **豆包大模型**：字节跳动豆包AI，支持流式对话
- **讯飞星火**：科大讯飞星火认知大模型
- **Coze工作流**：扣子平台工作流集成

### 技术特性
- **SSE流式推送**：基于Reactor的响应式编程，实现真正的实时流式对话
- **多模型切换**：支持动态切换不同AI模型
- **会话管理**：完整的对话历史记录和会话管理
- **权限认证**：基于Sa-Token的用户认证和权限控制
- **防重复提交**：智能防重复提交机制，避免重复请求
- **异步处理**：高性能异步任务处理

## 🛠️ 技术栈

### 后端框架
- **Spring Boot 3.0.7** - 主框架
- **Spring WebFlux** - 响应式Web框架
- **Spring WebSocket** - WebSocket支持
- **MyBatis Plus 3.5.3** - ORM框架

### 数据库
- **MySQL 8.0+** - 关系型数据库
- **MongoDB 4.4+** - 文档数据库
- **Redis 6.0+** - 缓存数据库
- **ShardingSphere** - 数据库中间件

### AI集成
- **讯飞WebSDK** - 星火大模型集成
- **豆包API** - 字节跳动豆包大模型
- **Coze API** - 扣子平台工作流

### 工具库
- **Hutool** - Java工具类库
- **FastJSON2** - JSON处理
- **Sa-Token** - 权限认证框架
- **Lombok** - 代码简化
- **OkHttp** - HTTP客户端

## 📁 项目结构

```
xunzhi-agent-backrond/
├── admin/                          # 主应用模块
│   ├── src/main/java/
│   │   └── com/hewei/hzyjy/xunzhi/
│   │       ├── controller/         # 控制器层
│   │       │   ├── AiMessageController.java    # AI对话接口
│   │       │   └── CozeWorkflowController.java # Coze工作流接口
│   │       ├── service/            # 服务层
│   │       │   ├── AiMessageService.java       # AI消息服务
│   │       │   └── CozeWorkflowService.java    # Coze工作流服务
│   │       ├── toolkit/            # 工具包
│   │       │   ├── doubao/         # 豆包AI客户端
│   │       │   ├── xunfei/         # 讯飞AI客户端
│   │       │   └── coze/           # Coze客户端
│   │       ├── dao/                # 数据访问层
│   │       ├── dto/                # 数据传输对象
│   │       ├── config/             # 配置类
│   │       └── common/             # 公共组件
│   └── src/main/resources/
│       ├── application.yaml        # 主配置文件
│       └── shardingsphere-config-dev.yaml # 数据库配置
├── resources/
│   └── database/                   # 数据库脚本
│       ├── complete_database_schema.sql    # 完整数据库结构
│       └── complete_database_data.sql      # 初始化数据
└── pom.xml                         # Maven配置
```

## 🔧 环境要求

### 必需环境

1. **JDK 17+**
2. **Maven 3.6+**
3. **MySQL 8.0+**
4. **MongoDB 4.4+**
5. **Redis 6.0+**

### 可选环境

- **IDE**: IntelliJ IDEA 2023+ (推荐)
- **Git**: 用于代码管理

## 数据库配置

### MySQL配置

1. 创建数据库：
```sql
CREATE DATABASE mainshi_agent CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 配置信息（开发环境）：
   - 主机：127.0.0.1
   - 端口：3306
   - 数据库：mainshi_agent
   - 用户名：root
   - 密码：123456

### MongoDB配置

- 主机：127.0.0.1
- 端口：27017
- 数据库：xunzhi_agent

### Redis配置

- 主机：127.0.0.1
- 端口：6379
- 无密码（开发环境）

## 配置文件说明

### 主配置文件

**application.yaml**
- 服务端口：8002
- 文件上传配置：最大100MB
- 数据源配置：使用ShardingSphere
- 讯飞API配置
- 流量限制配置

### 数据库分片配置

**shardingsphere-config-dev.yaml**
- 用户表按username分16个表
- 消息表按session_id分16个表
- 敏感信息加密（手机号、邮箱）

### Sa-Token配置

**application-satoken.yml**
- Token名称：Authorization
- Token有效期：30天
- Token前缀：Bearer

## 🚀 快速开始

### 1. 环境准备

确保已安装以下软件：

```bash
# 检查Java版本
java -version

# 检查Maven版本
mvn -version

# 检查MySQL服务状态
mysql --version

# 检查MongoDB服务状态
mongod --version

# 检查Redis服务状态
redis-server --version
```

### 2. 启动基础服务

**Windows环境：**
```powershell
# 启动MySQL（如果未自启动）
net start mysql

# 启动MongoDB
net start mongodb

# 启动Redis
redis-server
```

**Linux/Mac环境：**
```bash
# 启动MySQL
sudo systemctl start mysql

# 启动MongoDB
sudo systemctl start mongod

# 启动Redis
sudo systemctl start redis
```

### 3. 克隆项目

```bash
git clone <repository-url>
cd xunzhi-agent-backrond
```

### 4. 数据库初始化

#### MySQL数据库设置

1. 创建数据库：
```sql
CREATE DATABASE mainshi_agent CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 导入数据库结构和数据：
```bash
# 导入数据库结构
mysql -u root -p mainshi_agent < resources/database/complete_database_schema.sql

# 导入初始化数据
mysql -u root -p mainshi_agent < resources/database/complete_database_data.sql
```

#### MongoDB数据库设置

MongoDB会在首次连接时自动创建数据库，无需手动初始化。

### 5. 配置文件修改

根据你的环境修改配置文件：

**application.yaml**
```yaml
# 修改数据库连接信息
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mainshi_agent?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
  
  data:
    mongodb:
      host: localhost
      port: 27017
      database: xunzhi_agent
  
  redis:
    host: localhost
    port: 6379
    password: # 如果有密码请填写

# 修改讯飞API配置
xunfei:
  appid: your_appid
  api-secret: your_api_secret
  api-key: your_api_key
```

### 6. 编译和运行

```bash
# 进入admin模块
cd admin

# 清理并编译项目
mvn clean compile

# 安装依赖
mvn install

# 运行项目
mvn spring-boot:run
```

或者使用IDE运行：
1. 导入项目到IntelliJ IDEA
2. 等待Maven依赖下载完成
3. 运行 `XunZhiAdminApplication.java` 主类

### 7. 验证启动

项目启动成功后，访问：
- 应用地址：http://localhost:8002
- 健康检查：http://localhost:8002/actuator/health（如果配置了actuator）

查看控制台日志，确认：
- ✅ 数据库连接成功
- ✅ Redis连接成功  
- ✅ MongoDB连接成功
- ✅ 应用启动完成

## 启动步骤

### 5. 配置修改

根据实际环境修改配置文件：
- `application.yaml` - 基础配置
- `shardingsphere-config-dev.yaml` - 数据库配置
- `application-satoken.yml` - 认证配置

### 6. 启动应用

#### 方式一：IDE启动
1. 导入项目到IntelliJ IDEA
2. 找到主启动类
3. 右键运行

#### 方式二：命令行启动
```bash
cd admin
mvn spring-boot:run
```

#### 方式三：jar包启动
```bash
cd admin
mvn clean package
java -jar target/xunzhi-agent-admin-*.jar
```

### 7. 验证启动

访问健康检查接口：
```bash
curl http://localhost:8002/actuator/health
```

## 📚 API接口文档

### 基础信息

- **服务地址**: http://localhost:8002
- **API前缀**: /api/xunzhi/v1
- **认证方式**: Bearer Token
- **数据格式**: JSON

### 认证说明

大部分接口需要在请求头中携带Token：
```
Authorization: Bearer <your_token>
```

### 核心接口模块

#### 1. AI对话模块 (/api/xunzhi/v1/ai)

**AI流式聊天接口**
```http
POST /api/xunzhi/v1/ai/sessions/{sessionId}/chat
Content-Type: application/json
Authorization: Bearer <token>

{
  "message": "你好，请介绍一下自己",
  "aiId": "1", // 可选，AI配置ID
  "userName": "user123" // 可选，用户名
}
```

响应：SSE流式数据
```
data: {"type":"content","content":"你好！我是"}

data: {"type":"content","content":"一个AI助手"}

data: [DONE]
```

#### 2. Coze工作流模块 (/api/xunzhi/v1/coze)

**流式执行工作流**
```http
POST /api/xunzhi/v1/coze/workflow/{workflowId}/stream
Content-Type: application/json

{
  "userInput": "请帮我分析这个问题",
  "parameters": {
    "key1": "value1",
    "key2": "value2"
  }
}
```

**简单工作流执行**
```http
GET /api/xunzhi/v1/coze/workflow/{workflowId}/stream?message=Hello
```

#### 3. AI角色扮演模块 (/api/xunzhi/v1/ai/roleplay)

**音色训练上传**
```http
POST /api/xunzhi/v1/ai/roleplay/voice-training/upload
Content-Type: multipart/form-data

audioFiles: [音频文件数组]
voiceName: "我的音色"
voiceDescription: "音色描述"
trainingType: "standard"
language: "zh-CN"
```

### 响应格式

#### 成功响应
```json
{
  "code": "0",
  "message": "success", 
  "data": {
    // 具体数据
  },
  "success": true
}
```

#### 错误响应
```json
{
  "code": "A000001",
  "message": "用户请求参数错误",
  "data": null,
  "success": false
}
```

### SSE流式接口说明

AI聊天和工作流接口使用Server-Sent Events (SSE) 实现实时流式响应：

**前端调用示例**
```javascript
// 创建SSE连接
const eventSource = new EventSource('/api/xunzhi/v1/ai/sessions/123/chat', {
  headers: {
    'Authorization': 'Bearer your_token'
  }
});

// 监听消息
eventSource.onmessage = function(event) {
  const data = JSON.parse(event.data);
  if (data.type === 'content') {
    console.log('AI回复:', data.content);
  }
};

// 监听错误
eventSource.onerror = function(event) {
  console.log('连接错误:', event);
  eventSource.close();
};

// 监听结束
eventSource.addEventListener('done', function(event) {
  console.log('对话结束');
  eventSource.close();
});
```

**cURL调用示例**
```bash
# AI聊天
curl -X POST http://localhost:8002/api/xunzhi/v1/ai/sessions/test123/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer your_token" \
  -d '{"message":"你好"}'

# Coze工作流
curl -X POST http://localhost:8002/api/xunzhi/v1/coze/workflow/workflow123/stream \
  -H "Content-Type: application/json" \
  -d '{"userInput":"测试消息"}'
```

## 接口文档

| 方法 | 路径 | 描述 | 认证 |
|------|------|------|------|
| POST | / | 创建AI配置 | 是 |
| GET | / | 分页查询AI配置 | 是 |
| GET | /list | 获取所有AI配置 | 是 |
| GET | /{id} | 获取AI配置详情 | 是 |
| PUT | /{id} | 更新AI配置 | 是 |
| DELETE | /{id} | 删除AI配置 | 是 |

#### 7. Agent配置管理 (/api/xunzhi/v1/agent-properties)

| 方法 | 路径 | 描述 | 认证 |
|------|------|------|------|
| POST | / | 创建Agent配置 | 是 |
| GET | / | 分页查询Agent配置 | 是 |
| GET | /list | 获取所有Agent配置 | 是 |
| GET | /{id} | 获取Agent配置详情 | 是 |
| PUT | /{id} | 更新Agent配置 | 是 |
| DELETE | /{id} | 删除Agent配置 | 是 |

### 响应格式

#### 成功响应
```json
{
  "code": "0",
  "message": "success",
  "data": {
    // 具体数据
  },
  "success": true
}
```

#### 错误响应
```json
{
  "code": "A000001",
  "message": "用户请求参数错误",
  "data": null,
  "success": false
}
```

### SSE流式接口说明

聊天接口使用Server-Sent Events (SSE) 实现流式响应：

```javascript
// 前端调用示例
const eventSource = new EventSource('/api/xunzhi/v1/ai/sessions/123/chat', {
  headers: {
    'Authorization': 'Bearer your_token'
  }
});

eventSource.onmessage = function(event) {
  console.log('收到消息:', event.data);
};

eventSource.onerror = function(event) {
  console.log('连接错误:', event);
};
```

## ❓ 常见问题

### 1. 启动失败

**问题**: 端口被占用
```
Port 8002 was already in use
```
**解决方案**: 
- 修改 `application.yaml` 中的 `server.port`
- 或者杀死占用进程：`netstat -ano | findstr :8002`

**问题**: 数据库连接失败
```
Could not connect to MySQL server
```
**解决方案**:
- 检查MySQL服务是否启动
- 验证数据库连接配置
- 确认数据库已创建

**问题**: Maven依赖下载失败
```
Could not resolve dependencies
```
**解决方案**:
- 检查网络连接
- 配置Maven镜像源
- 清理本地仓库：`mvn clean`

### 2. 运行时问题

**问题**: SSE连接断开
**解决方案**:
- 检查网络连接
- 增加连接超时时间
- 确认Token有效性

**问题**: AI响应慢或无响应
**解决方案**:
- 检查讯飞API配置
- 验证API密钥有效性
- 查看网络延迟

### 3. 配置问题

**问题**: 讯飞API调用失败
**解决方案**:
- 检查 `appid`、`api-key`、`api-secret` 配置
- 确认API额度充足
- 验证API权限

## 🔧 开发指南

### 代码结构说明

- **Controller层**: 处理HTTP请求，参数验证
- **Service层**: 业务逻辑处理，事务管理
- **DAO层**: 数据访问，数据库操作
- **DTO层**: 数据传输对象，接口参数封装
- **Config层**: 配置类，Bean定义
- **Toolkit层**: 第三方服务集成

### 开发规范

1. **命名规范**: 遵循Java驼峰命名法
2. **注释规范**: 类和方法必须有JavaDoc注释
3. **异常处理**: 统一异常处理，避免空指针
4. **日志规范**: 使用SLF4J，合理设置日志级别
5. **代码格式**: 使用IDE格式化，保持代码整洁

### 扩展开发

**添加新的AI模型**:
1. 在 `toolkit` 包下创建新的客户端类
2. 实现统一的接口规范
3. 在 `AiMessageService` 中集成
4. 添加相应的配置项

**添加新的API接口**:
1. 创建DTO类定义请求/响应参数
2. 在Controller中添加接口方法
3. 在Service中实现业务逻辑
4. 添加必要的参数验证和异常处理

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 联系方式

- **项目维护者**: nageoffer
- **技术支持**: 请提交 Issue
- **邮箱**: support@example.com

---

**感谢使用讯智Agent后端服务！** 🎉
```
Communications link failure
```
**解决**: 检查MySQL是否启动，配置是否正确

### 2. 接口调用失败

**问题**: 403 Forbidden
**解决**: 检查Token是否正确携带，是否已过期

**问题**: 跨域错误
**解决**: 已配置CORS，如仍有问题请检查前端请求方式

### 3. 功能异常

**问题**: 讯飞API调用失败
**解决**: 检查讯飞API密钥配置是否正确

**问题**: 文件上传失败
**解决**: 检查上传目录权限，文件大小是否超限

## 开发调试

### 日志配置

日志级别可在logback.xml中配置：
```xml
<logger name="com.hewei.hzyjy.xunzhi" level="DEBUG"/>
```

### 数据库调试

ShardingSphere SQL日志已开启：
```yaml
props:
  sql-show: true
```

### 性能监控

- 流量限制：每秒最多20次请求
- 连接池监控：HikariCP
- Redis会话缓存：7天过期

## 部署说明

### 生产环境配置

1. 修改数据库配置文件：`shardingsphere-config-prod.yaml`
2. 更新讯飞API密钥
3. 配置HTTPS证书
4. 设置合适的JVM参数：

```bash
java -Xms2g -Xmx4g -jar xunzhi-agent-admin.jar --spring.profiles.active=prod
```

### Docker部署

```dockerfile
FROM openjdk:17-jre-slim
COPY target/xunzhi-agent-admin-*.jar app.jar
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 联系方式

如有问题，请联系开发团队或查看项目文档。

---

**最后更新**: 2024年12月
**版本**: v1.0.0
```