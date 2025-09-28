---
title: 默认模块
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# 默认模块

Base URLs:

# Authentication

# AI角色扮演Controller

## POST 上传音色训练音频文件

POST /api/xunzhi/v1/ai/roleplay/voice-training/upload

> Body 请求参数

```yaml
audioFiles: string

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|voiceName|query|string| 是 |none|
|voiceDescription|query|string| 否 |none|
|trainingType|query|string| 是 |none|
|language|query|string| 是 |none|
|sessionId|query|string| 否 |none|
|userId|query|integer| 否 |none|
|body|body|object| 否 |none|
|» audioFiles|body|string(binary)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "trainingTaskId": "",
    "voiceName": "",
    "trainingStatus": "",
    "audioFileCount": 0,
    "totalDuration": 0,
    "trainingStartTime": 0,
    "estimatedCompletionTime": 0,
    "progress": 0,
    "errorMessage": "",
    "voiceId": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultVoiceTrainingUploadRespDTO](#schemaresultvoicetraininguploadrespdto)|

## GET 查询音色训练状态

GET /api/xunzhi/v1/ai/roleplay/voice-training/status/{trainingTaskId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|trainingTaskId|path|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "trainingTaskId": "",
    "voiceName": "",
    "trainingStatus": "",
    "audioFileCount": 0,
    "totalDuration": 0,
    "trainingStartTime": 0,
    "estimatedCompletionTime": 0,
    "progress": 0,
    "errorMessage": "",
    "voiceId": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultVoiceTrainingUploadRespDTO](#schemaresultvoicetraininguploadrespdto)|

## POST TTS语音合成（直接返回MP3文件流）

POST /api/xunzhi/v1/ai/roleplay/tts/synthesis/stream

> Body 请求参数

```json
{
  "text": "string",
  "voiceType": "string",
  "speed": 0,
  "pitch": 0,
  "volume": 0,
  "audioFormat": "string",
  "sampleRate": 0,
  "sessionId": "string",
  "userId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[TtsSynthesisReqDTO](#schemattssynthesisreqdto)| 否 |none|

> 返回示例

> 200 Response

```json
[
  0
]
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## POST TTS语音合成

POST /api/xunzhi/v1/ai/roleplay/tts/synthesis

> Body 请求参数

```json
{
  "text": "string",
  "voiceType": "string",
  "speed": 0,
  "pitch": 0,
  "volume": 0,
  "audioFormat": "string",
  "sampleRate": 0,
  "sessionId": "string",
  "userId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[TtsSynthesisReqDTO](#schemattssynthesisreqdto)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "audioId": "",
    "audioUrl": "",
    "duration": 0,
    "fileSize": 0,
    "audioFormat": "",
    "sampleRate": 0,
    "synthesisTime": 0,
    "voiceType": "",
    "actualSpeed": 0,
    "actualPitch": 0,
    "actualVolume": 0
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultTtsSynthesisRespDTO](#schemaresultttssynthesisrespdto)|

## GET 获取支持的语音类型

GET /api/xunzhi/v1/ai/roleplay/tts/voices

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": [
    ""
  ],
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultString1](#schemaresultstring1)|

## GET 健康检查接口

GET /api/xunzhi/v1/ai/roleplay/health

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": "",
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultString](#schemaresultstring)|

# 数据模型

<h2 id="tocS_ResultString">ResultString</h2>

<a id="schemaresultstring"></a>
<a id="schema_ResultString"></a>
<a id="tocSresultstring"></a>
<a id="tocsresultstring"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": "string",
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|string|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_VoiceTrainingUploadRespDTO">VoiceTrainingUploadRespDTO</h2>

<a id="schemavoicetraininguploadrespdto"></a>
<a id="schema_VoiceTrainingUploadRespDTO"></a>
<a id="tocSvoicetraininguploadrespdto"></a>
<a id="tocsvoicetraininguploadrespdto"></a>

```json
{
  "trainingTaskId": "string",
  "voiceName": "string",
  "trainingStatus": "string",
  "audioFileCount": 0,
  "totalDuration": 0,
  "trainingStartTime": 0,
  "estimatedCompletionTime": 0,
  "progress": 0,
  "errorMessage": "string",
  "voiceId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|trainingTaskId|string|false|none||训练任务ID|
|voiceName|string|false|none||音色名称|
|trainingStatus|string|false|none||训练状态：uploading（上传中）、training（训练中）、completed（完成）、failed（失败）|
|audioFileCount|integer|false|none||上传的音频文件数量|
|totalDuration|integer(int64)|false|none||总音频时长（秒）|
|trainingStartTime|integer(int64)|false|none||训练开始时间|
|estimatedCompletionTime|integer(int64)|false|none||预计完成时间|
|progress|integer|false|none||训练进度（0-100）|
|errorMessage|string|false|none||错误信息（如果训练失败）|
|voiceId|string|false|none||生成的音色ID（训练完成后）|

<h2 id="tocS_ResultVoiceTrainingUploadRespDTO">ResultVoiceTrainingUploadRespDTO</h2>

<a id="schemaresultvoicetraininguploadrespdto"></a>
<a id="schema_ResultVoiceTrainingUploadRespDTO"></a>
<a id="tocSresultvoicetraininguploadrespdto"></a>
<a id="tocsresultvoicetraininguploadrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "trainingTaskId": "string",
    "voiceName": "string",
    "trainingStatus": "string",
    "audioFileCount": 0,
    "totalDuration": 0,
    "trainingStartTime": 0,
    "estimatedCompletionTime": 0,
    "progress": 0,
    "errorMessage": "string",
    "voiceId": "string"
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[VoiceTrainingUploadRespDTO](#schemavoicetraininguploadrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_TtsSynthesisRespDTO">TtsSynthesisRespDTO</h2>

<a id="schemattssynthesisrespdto"></a>
<a id="schema_TtsSynthesisRespDTO"></a>
<a id="tocSttssynthesisrespdto"></a>
<a id="tocsttssynthesisrespdto"></a>

```json
{
  "audioId": "string",
  "audioUrl": "string",
  "duration": 0,
  "fileSize": 0,
  "audioFormat": "string",
  "sampleRate": 0,
  "synthesisTime": 0,
  "voiceType": "string",
  "actualSpeed": 0,
  "actualPitch": 0,
  "actualVolume": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|audioId|string|false|none||音频文件ID|
|audioUrl|string|false|none||音频文件URL|
|duration|integer(int64)|false|none||音频时长（毫秒）|
|fileSize|integer(int64)|false|none||文件大小（字节）|
|audioFormat|string|false|none||音频格式|
|sampleRate|integer|false|none||采样率|
|synthesisTime|integer(int64)|false|none||合成时间戳|
|voiceType|string|false|none||使用的语音类型|
|actualSpeed|number|false|none||实际使用的语速|
|actualPitch|number|false|none||实际使用的音调|
|actualVolume|number|false|none||实际使用的音量|

<h2 id="tocS_ResultTtsSynthesisRespDTO">ResultTtsSynthesisRespDTO</h2>

<a id="schemaresultttssynthesisrespdto"></a>
<a id="schema_ResultTtsSynthesisRespDTO"></a>
<a id="tocSresultttssynthesisrespdto"></a>
<a id="tocsresultttssynthesisrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "audioId": "string",
    "audioUrl": "string",
    "duration": 0,
    "fileSize": 0,
    "audioFormat": "string",
    "sampleRate": 0,
    "synthesisTime": 0,
    "voiceType": "string",
    "actualSpeed": 0,
    "actualPitch": 0,
    "actualVolume": 0
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[TtsSynthesisRespDTO](#schemattssynthesisrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_TtsSynthesisReqDTO">TtsSynthesisReqDTO</h2>

<a id="schemattssynthesisreqdto"></a>
<a id="schema_TtsSynthesisReqDTO"></a>
<a id="tocSttssynthesisreqdto"></a>
<a id="tocsttssynthesisreqdto"></a>

```json
{
  "text": "string",
  "voiceType": "string",
  "speed": 0,
  "pitch": 0,
  "volume": 0,
  "audioFormat": "string",
  "sampleRate": 0,
  "sessionId": "string",
  "userId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|text|string|false|none||要合成的文本内容|
|voiceType|string|false|none||语音类型：xiaoyun、xiaogang、xiaomei等|
|speed|number|false|none||语速：0.5-2.0，默认1.0|
|pitch|number|false|none||音调：0.5-2.0，默认1.0|
|volume|number|false|none||音量：0.0-1.0，默认1.0|
|audioFormat|string|false|none||音频格式：mp3、wav、pcm等|
|sampleRate|integer|false|none||采样率：8000、16000、44100等|
|sessionId|string|false|none||会话ID|
|userId|integer(int64)|false|none||用户ID|

<h2 id="tocS_ResultString1">ResultString1</h2>

<a id="schemaresultstring1"></a>
<a id="schema_ResultString1"></a>
<a id="tocSresultstring1"></a>
<a id="tocsresultstring1"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": [
    "string"
  ],
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[string]|false|none||响应数据|
|requestId|string|false|none||请求ID|

