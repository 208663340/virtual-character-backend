# 面试题抽取接口文档

## 接口概述

本接口基于用户上传的简历PDF文件，利用AI智能分析简历内容，自动生成针对性的面试题目。支持多种职位类型和难度级别的定制化面试题生成。

## 接口信息

- **接口地址**: `/api/xunzhi-agent/admin/v1/agent/extract-interview-questions`
- **请求方式**: `POST`
- **内容类型**: `multipart/form-data`
- **响应类型**: `text/event-stream` (SSE流式响应)

## 请求参数

### 必填参数

| 参数名 | 类型 | 说明 | 示例值 |
|--------|------|------|--------|
| userName | String | 用户名 | "张三" |
| agentId | Long | 智能体ID | 1345345 |
| sessionId | String | 会话ID | "session_123456" |
| resumePdf | MultipartFile | 简历PDF文件 | 文件上传 |

### 可选参数

| 参数名 | 类型 | 说明 | 示例值 |
|--------|------|------|--------|
| position | String | 目标职位 | "Java开发工程师" |
| difficultyLevel | String | 难度级别 | "中级" |
| additionalInfo | String | 额外要求 | "重点考察Spring框架" |

## 文件要求

### PDF文件限制
- **文件格式**: 仅支持PDF格式
- **文件大小**: 建议不超过10MB
- **文件内容**: 支持中英文简历
- **加密文件**: 不支持加密的PDF文件
- **文本提取**: 自动提取PDF中的文本内容（限制8000字符）

## 响应格式

### SSE流式响应

接口采用Server-Sent Events (SSE) 技术，实时流式返回生成的面试题内容。

```
Content-Type: text/event-stream
Cache-Control: no-cache
Connection: keep-alive

data: 根据您的简历，我为您生成了以下面试题：

data: ## 1. 技术基础题

data: ### 1.1 Java基础

data: **题目**: 请解释Java中的多态性概念及其实现方式。

data: **考察要点**: 面向对象编程基础、继承、接口实现

data: **参考答案要点**: 
- 多态是指同一个接口可以有多种不同的实现方式
- 通过继承和接口实现
- 运行时动态绑定

data: [DONE]
```

### 生成内容结构

生成的面试题按以下结构组织：

1. **技术基础题（3-5题）**
   - 针对简历中提到的技术栈
   - 考察基础概念和原理

2. **项目经验题（2-3题）**
   - 基于简历中的项目经历
   - 考察实际应用能力

3. **综合能力题（2-3题）**
   - 考察问题解决能力
   - 系统设计思维

4. **情景应对题（1-2题）**
   - 考察沟通协作能力
   - 压力处理能力

每道题目包含：
- **题目内容**: 具体的面试问题
- **考察要点**: 该题目主要考察的知识点或能力
- **参考答案要点**: 回答该题目的关键点

## 使用示例

### JavaScript (使用EventSource)

```javascript
// 创建FormData对象
const formData = new FormData();
formData.append('userName', '张三');
formData.append('agentId', '1345345');
formData.append('sessionId', 'session_' + Date.now());
formData.append('resumePdf', pdfFile); // pdfFile是File对象
formData.append('position', 'Java开发工程师');
formData.append('difficultyLevel', '中级');
formData.append('additionalInfo', '重点考察Spring框架');

// 先上传文件并获取响应URL
fetch('/api/xunzhi-agent/admin/v1/agent/extract-interview-questions', {
    method: 'POST',
    body: formData
})
.then(response => {
    // 获取SSE连接URL
    const eventSource = new EventSource(response.url);
    
    eventSource.onmessage = function(event) {
        if (event.data === '[DONE]') {
            eventSource.close();
            console.log('面试题生成完成');
        } else {
            console.log('接收到数据:', event.data);
            // 将数据追加到页面显示
            document.getElementById('interview-questions').innerHTML += event.data;
        }
    };
    
    eventSource.onerror = function(event) {
        console.error('SSE连接错误:', event);
        eventSource.close();
    };
});
```

### cURL示例

```bash
curl -X POST \
  'http://localhost:8080/api/xunzhi-agent/admin/v1/agent/extract-interview-questions' \
  -H 'Content-Type: multipart/form-data' \
  -F 'userName=张三' \
  -F 'agentId=1345345' \
  -F 'sessionId=session_123456' \
  -F 'resumePdf=@/path/to/resume.pdf' \
  -F 'position=Java开发工程师' \
  -F 'difficultyLevel=中级' \
  -F 'additionalInfo=重点考察Spring框架'
```

## 错误处理

### 常见错误情况

1. **PDF文件解析失败**
   ```
   data: PDF文件解析失败，请检查文件格式是否正确。
   ```

2. **文件格式不支持**
   ```
   data: 文件类型必须是PDF格式
   ```

3. **文件过大**
   ```
   data: 文件大小超出限制，请上传小于10MB的文件
   ```

4. **系统异常**
   ```
   data: 系统异常，请稍后重试。
   ```

### 错误处理建议

- 在前端实现文件格式和大小验证
- 提供用户友好的错误提示
- 实现重试机制
- 监听SSE连接的错误事件

## 技术特点

### 核心功能
- **智能PDF解析**: 使用Apache PDFBox提取PDF文本内容
- **AI驱动生成**: 基于讯飞星火大模型生成面试题
- **流式响应**: 实时返回生成内容，提升用户体验
- **个性化定制**: 支持职位、难度级别等参数定制

### 性能优化
- **异步处理**: 使用CompletableFuture异步处理请求
- **文本限制**: 限制PDF文本长度为8000字符，避免token过多
- **连接管理**: 自动处理SSE连接超时和错误

## 注意事项

1. **文件安全**: 上传的PDF文件仅用于文本提取，不会永久存储
2. **隐私保护**: 简历内容仅用于面试题生成，不会泄露给第三方
3. **并发限制**: 建议控制并发请求数量，避免系统负载过高
4. **网络稳定**: SSE连接需要稳定的网络环境
5. **浏览器兼容**: 确保浏览器支持EventSource API

## 更新日志

### v1.0.0 (2024-01-XX)
- 初始版本发布
- 支持PDF简历上传和文本提取
- 实现基于AI的面试题生成
- 提供SSE流式响应
- 支持职位和难度级别定制