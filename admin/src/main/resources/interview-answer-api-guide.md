# 面试题回答接口使用指南

## 接口概述

新的面试题回答接口支持两种方式提交答案：
1. **文字回答**：直接提交文字内容
2. **录音回答**：上传录音文件，系统自动转换为文字后进行评分

## 接口信息

- **URL**: `POST /api/xunzhi/v1/agents/interview/answer`
- **Content-Type**: `multipart/form-data`

## 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| questionNumber | String | 是 | 题号（如："1", "2", "3"等） |
| answerContent | String | 否* | 文字回答内容 |
| audioFile | File | 否* | 录音文件（支持格式：pcm, wav, mp3, flac） |
| sessionId | String | 否 | 会话ID |
| agentId | Long | 否 | Agent ID（默认：1345345） |

> **注意**：`answerContent` 和 `audioFile` 必须提供其中一个

## 响应格式

```json
{
  "success": true,
  "data": {
    "questionNumber": "1",
    "questionContent": "请介绍一下你的项目经验",
    "score": 85,
    "totalScore": 85,
    "isSuccess": true,
    "errorMessage": null,
    "feedback": "回答内容详细，逻辑清晰..."
  },
  "message": "操作成功"
}
```

## 使用示例

### 1. 文字回答示例

```javascript
const formData = new FormData();
formData.append('questionNumber', '1');
formData.append('answerContent', '我有3年的Java开发经验，主要负责...');
formData.append('sessionId', 'session_123');

fetch('/api/xunzhi/v1/agents/interview/answer', {
    method: 'POST',
    headers: {
        'Authorization': 'Bearer your_token_here'
    },
    body: formData
})
.then(response => response.json())
.then(data => {
    console.log('评分结果:', data);
});
```

### 2. 录音回答示例

```javascript
// 假设已经获取到录音文件
const audioFile = document.getElementById('audioInput').files[0];

const formData = new FormData();
formData.append('questionNumber', '2');
formData.append('audioFile', audioFile);
formData.append('sessionId', 'session_123');

fetch('/api/xunzhi/v1/agents/interview/answer', {
    method: 'POST',
    headers: {
        'Authorization': 'Bearer your_token_here'
    },
    body: formData
})
.then(response => response.json())
.then(data => {
    if (data.success) {
        console.log('语音转文字并评分成功:', data.data);
        console.log('本次得分:', data.data.score);
        console.log('累计总分:', data.data.totalScore);
    } else {
        console.error('评分失败:', data.message);
    }
});
```

### 3. HTML表单示例

```html
<form id="answerForm" enctype="multipart/form-data">
    <div>
        <label>题号:</label>
        <input type="text" name="questionNumber" required>
    </div>
    
    <div>
        <label>文字回答:</label>
        <textarea name="answerContent" rows="4" cols="50"></textarea>
    </div>
    
    <div>
        <label>或上传录音:</label>
        <input type="file" name="audioFile" accept=".wav,.mp3,.pcm,.flac">
    </div>
    
    <div>
        <label>会话ID:</label>
        <input type="text" name="sessionId">
    </div>
    
    <button type="submit">提交答案</button>
</form>

<script>
document.getElementById('answerForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const formData = new FormData(this);
    
    // 验证必须提供文字回答或录音文件
    const answerContent = formData.get('answerContent');
    const audioFile = formData.get('audioFile');
    
    if (!answerContent && (!audioFile || audioFile.size === 0)) {
        alert('请提供文字回答或录音文件');
        return;
    }
    
    fetch('/api/xunzhi/v1/agents/interview/answer', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(`评分成功！本次得分：${data.data.score}，累计总分：${data.data.totalScore}`);
        } else {
            alert('评分失败：' + data.message);
        }
    })
    .catch(error => {
        console.error('请求失败:', error);
        alert('请求失败，请重试');
    });
});
</script>
```

## 支持的音频格式

- **PCM**: 原始音频格式
- **WAV**: 无损音频格式（推荐）
- **MP3**: 压缩音频格式（推荐）
- **FLAC**: 无损压缩音频格式

> **不支持的格式**：M4A、AAC等格式不被支持，需要先转换为支持的格式

## 错误处理

### 常见错误码

1. **题目不存在**: `"题目不存在或已过期，请重新抽取面试题"`
2. **文件格式错误**: `"不支持的音频格式: .m4a，仅支持: pcm, wav, mp3, flac"`
3. **文件过大**: `"音频文件大小不能超过50MB"`
4. **语音转文字失败**: `"语音转文字失败，请检查录音文件格式或重新录制"`
5. **参数缺失**: `"请提供文字回答或录音文件"`

### 错误处理示例

```javascript
fetch('/api/xunzhi/v1/agents/interview/answer', {
    method: 'POST',
    body: formData
})
.then(response => response.json())
.then(data => {
    if (data.success && data.data.isSuccess) {
        // 评分成功
        console.log('评分成功:', data.data);
    } else {
        // 评分失败
        const errorMessage = data.data?.errorMessage || data.message || '未知错误';
        console.error('评分失败:', errorMessage);
        
        // 根据错误类型进行不同处理
        if (errorMessage.includes('题目不存在')) {
            // 重新获取面试题
            window.location.href = '/interview/questions';
        } else if (errorMessage.includes('音频格式')) {
            alert('请上传支持的音频格式：wav, mp3, pcm, flac');
        } else if (errorMessage.includes('语音转文字失败')) {
            alert('语音识别失败，请检查录音质量或改用文字回答');
        }
    }
})
.catch(error => {
    console.error('网络错误:', error);
    alert('网络错误，请检查网络连接后重试');
});
```

## 注意事项

1. **音频质量**：为了获得更好的语音识别效果，请确保录音清晰，环境安静
2. **文件大小**：音频文件大小限制为50MB
3. **超时设置**：语音转文字可能需要一些时间，建议设置合适的请求超时时间
4. **错误重试**：如果语音识别失败，可以重新上传录音或改用文字回答
5. **权限验证**：请求需要携带有效的Authorization token

## 技术实现说明

1. **语音转文字**：使用讯飞星火语音听写API进行音频转文字
2. **AI评分**：将题目和答案发送给AI Agent进行智能评分
3. **分数累加**：评分结果会自动累加到Redis缓存中的用户总分
4. **数据存储**：评分记录和反馈会保存到数据库中