# 面试题抽取接口文档

## 接口概述
本接口用于根据上传的PDF简历文件，结合目标职位、难度级别等参数，智能生成个性化的面试题。接口采用SSE（Server-Sent Events）流式响应，实时返回生成的面试题内容。

## 技术特点
- **文件上传**：将PDF简历文件上传到讯飞服务器，获取文件URL用于AI分析
- **智能分析**：基于上传的简历文件、职位要求和难度级别生成针对性面试题
- **流式响应**：采用SSE技术，实时推送生成结果，提升用户体验
- **异常处理**：完善的错误处理机制，包括文件格式验证、上传失败处理、超时处理等

## 接口信息

### 基本信息
- **接口地址**：`POST /api/xunzhi-agent/admin/v1/agent/extract-interview-questions`
- **请求方式**：POST (multipart/form-data)
- **响应格式**：SSE流式响应 (text/event-stream)
- **认证方式**：需要登录认证

### 请求参数

#### 必填参数
| 参数名 | 类型 | 说明 | 示例值 |
|--------|------|------|--------|
| userName | String | 用户名 | "zhangsan" |
| agentId | Long | Agent ID | 1 |
| sessionId | String | 会话ID | "session_123" |
| resumePdf | MultipartFile | PDF简历文件 | 文件对象 |
| targetPosition | String | 目标职位 | "Java开发工程师" |

#### 可选参数
| 参数名 | 类型 | 说明 | 默认值 | 示例值 |
|--------|------|------|--------|--------|
| difficultyLevel | String | 难度级别 | "中等" | "初级/中等/高级" |
| additionalRequirements | String | 额外要求 | "" | "重点考察Spring框架" |

### 文件要求
- **文件格式**：仅支持PDF格式
- **文件大小**：建议不超过20MB
- **文件内容**：应包含完整的简历信息

### 响应格式

#### SSE流式响应
接口采用Server-Sent Events (SSE) 技术进行流式响应：

```
Content-Type: text/event-stream
Cache-Control: no-cache
Connection: keep-alive

data: {"type":"progress","message":"正在上传简历文件..."}

data: {"type":"progress","message":"文件上传成功，开始分析..."}

data: {"type":"content","data":"## 技术基础题\n\n1. 请介绍一下Java的面向对象特性"}

data: {"type":"content","data":"\n\n2. 解释一下Spring框架的核心概念"}

data: {"type":"complete","message":"面试题生成完成"}
```

#### 生成内容结构
生成的面试题通常包含以下结构：

1. **技术基础题**：根据简历中的技术栈生成基础概念题
2. **项目经验题**：基于简历中的项目经历提出深入问题
3. **场景应用题**：结合目标职位的实际工作场景
4. **综合能力题**：考察解决问题和学习能力

### 使用示例

#### JavaScript示例
```javascript
const formData = new FormData();
formData.append('userName', 'zhangsan');
formData.append('agentId', '1');
formData.append('sessionId', 'session_123');
formData.append('resumePdf', pdfFile); // File对象
formData.append('targetPosition', 'Java开发工程师');
formData.append('difficultyLevel', '中等');
formData.append('additionalRequirements', '重点考察Spring框架');

const eventSource = new EventSource('/api/xunzhi-agent/admin/v1/agent/extract-interview-questions');

// 使用fetch发送请求
fetch('/api/xunzhi-agent/admin/v1/agent/extract-interview-questions', {
    method: 'POST',
    body: formData
})
.then(response => {
    const reader = response.body.getReader();
    const decoder = new TextDecoder();
    
    function readStream() {
        return reader.read().then(({ done, value }) => {
            if (done) {
                console.log('流式响应结束');
                return;
            }
            
            const chunk = decoder.decode(value);
            const lines = chunk.split('\n');
            
            lines.forEach(line => {
                if (line.startsWith('data: ')) {
                    const data = line.substring(6);
                    try {
                        const parsed = JSON.parse(data);
                        console.log('接收到数据:', parsed);
                        // 处理接收到的数据
                        handleStreamData(parsed);
                    } catch (e) {
                        // 处理非JSON数据
                        console.log('接收到文本:', data);
                    }
                }
            });
            
            return readStream();
        });
    }
    
    return readStream();
})
.catch(error => {
    console.error('请求失败:', error);
});

function handleStreamData(data) {
    if (data.type === 'progress') {
        // 显示进度信息
        console.log('进度:', data.message);
    } else if (data.type === 'content') {
        // 显示生成的内容
        document.getElementById('result').innerHTML += data.data;
    } else if (data.type === 'complete') {
        // 生成完成
        console.log('完成:', data.message);
    }
}
```

#### cURL示例
```bash
curl -X POST \
  'http://localhost:8080/api/xunzhi-agent/admin/v1/agent/extract-interview-questions' \
  -H 'Content-Type: multipart/form-data' \
  -F 'userName=zhangsan' \
  -F 'agentId=1' \
  -F 'sessionId=session_123' \
  -F 'resumePdf=@/path/to/resume.pdf' \
  -F 'targetPosition=Java开发工程师' \
  -F 'difficultyLevel=中等' \
  -F 'additionalRequirements=重点考察Spring框架' \
  --no-buffer
```

### 错误处理

#### 常见错误码
| 错误类型 | 错误信息 | 解决方案 |
|----------|----------|----------|
| 文件格式错误 | "不支持的文件格式，请上传PDF文件" | 确保上传的是PDF格式文件 |
| 文件上传失败 | "文件上传失败，请重试" | 检查网络连接，重新上传文件 |
| Agent不存在 | "指定的Agent不存在" | 检查agentId是否正确 |
| 参数缺失 | "缺少必要参数" | 检查所有必填参数是否提供 |
| 服务异常 | "AI服务暂时不可用" | 稍后重试或联系技术支持 |

#### 错误响应示例
```
data: {"type":"error","message":"文件上传失败，请重试"}
```

### 注意事项

1. **文件处理流程**：
   - 首先将PDF文件上传到讯飞服务器
   - 获取文件URL后传递给AI进行分析
   - AI基于文件内容和参数生成面试题

2. **性能优化**：
   - 建议控制PDF文件大小，避免上传过大文件
   - 合理设置超时时间，避免长时间等待

3. **安全考虑**：
   - 上传的简历文件会临时存储在讯飞服务器
   - 建议在使用完成后及时清理相关数据

4. **兼容性**：
   - 确保浏览器支持SSE技术
   - 建议使用现代浏览器进行测试

5. **调试建议**：
   - 可以通过浏览器开发者工具查看SSE连接状态
   - 注意检查网络连接和响应头设置