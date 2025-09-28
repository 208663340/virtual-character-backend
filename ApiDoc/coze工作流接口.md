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

# Coze工作流控制器

## GET 健康检查

GET /api/xunzhi/v1/coze/health

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "": {}
  },
  "requestId": "",
  "success": false
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultMapObject](#schemaresultmapobject)|

## POST SSE流式执行工作流

POST /api/xunzhi/v1/coze/workflow/{workflowId}/stream

> Body 请求参数

```json
{
  "userInput": "string",
  "conversationName": "string",
  "userId": "string",
  "extraParams": {
    "key": {}
  },
  "debug": false,
  "timeout": 30,
  "language": "zh-CN"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|workflowId|path|string| 是 |工作流ID|
|body|body|[CozeWorkflowStreamReqDTO](#schemacozeworkflowstreamreqdto)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET SSE流式执行工作流（GET方式，简单）

GET /api/xunzhi/v1/coze/workflow/{workflowId}/stream

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|workflowId|path|string| 是 |工作流ID|
|message|query|string| 否 |消息参数（可选）|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

# 数据模型

<h2 id="tocS_MapObject">MapObject</h2>

<a id="schemamapobject"></a>
<a id="schema_MapObject"></a>
<a id="tocSmapobject"></a>
<a id="tocsmapobject"></a>

```json
{
  "key": {}
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|key|[key](#schemakey)|false|none||none|

<h2 id="tocS_ResultMapObject">ResultMapObject</h2>

<a id="schemaresultmapobject"></a>
<a id="schema_ResultMapObject"></a>
<a id="tocSresultmapobject"></a>
<a id="tocsresultmapobject"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "key": {}
  },
  "requestId": "string",
  "success": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[MapObject](#schemamapobject)|false|none||响应数据|
|requestId|string|false|none||请求ID|
|success|boolean|false|none||none|

<h2 id="tocS_key">key</h2>

<a id="schemakey"></a>
<a id="schema_key"></a>
<a id="tocSkey"></a>
<a id="tocskey"></a>

```json
{}

```

### 属性

*None*

<h2 id="tocS_CozeWorkflowStreamReqDTO">CozeWorkflowStreamReqDTO</h2>

<a id="schemacozeworkflowstreamreqdto"></a>
<a id="schema_CozeWorkflowStreamReqDTO"></a>
<a id="tocScozeworkflowstreamreqdto"></a>
<a id="tocscozeworkflowstreamreqdto"></a>

```json
{
  "userInput": "string",
  "conversationName": "string",
  "userId": "string",
  "extraParams": {
    "key": {}
  },
  "debug": false,
  "timeout": 30,
  "language": "zh-CN"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userInput|string|false|none||用户输入内容|
|conversationName|string|false|none||会话ID（可选，用于上下文关联）|
|userId|string|false|none||用户ID（可选，用于用户关联）|
|extraParams|[MapObject](#schemamapobject)|false|none||额外参数（可选，用于传递其他工作流参数）|
|debug|boolean|false|none||是否启用调试模式|
|timeout|integer|false|none||超时时间（秒），默认30秒|
|language|string|false|none||语言设置（可选）|

