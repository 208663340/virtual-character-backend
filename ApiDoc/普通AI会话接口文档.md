---
title: 软件杯
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

# 软件杯

Base URLs:

# Authentication

# AI会话控制器

## POST 创建AI会话

POST /api/xunzhi/v1/ai/conversations

> Body 请求参数

```json
{
  "userName": "string",
  "aiId": 0,
  "firstMessage": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AiSessionCreateReqDTO](#schemaaisessioncreatereqdto)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "sessionId": "",
    "conversationTitle": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultAiSessionCreateRespDTO](#schemaresultaisessioncreaterespdto)|

## GET 分页查询会话列表

GET /api/xunzhi/v1/ai/conversations

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|current|query|integer| 否 |当前页|
|size|query|integer| 否 |每页大小|
|aiId|query|integer(int64)| 否 |AI配置ID|
|status|query|integer| 否 |会话状态：1-进行中，2-已结束|
|title|query|string| 否 |会话标题（模糊查询）|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "records": [
      {
        "sessionId": "",
        "username": "",
        "aiId": 0,
        "aiName": "",
        "title": "",
        "status": 0,
        "messageCount": 0,
        "lastMessageTime": "",
        "createTime": ""
      }
    ],
    "total": 0,
    "size": 0,
    "current": 0,
    "orders": [
      {
        "column": "",
        "asc": false
      }
    ],
    "optimizeCountSql": false,
    "searchCount": false,
    "optimizeJoinOfCountSql": false,
    "maxLimit": 0,
    "countId": "",
    "pages": 0
  },
  "requestId": "",
  "success": false
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultIPageAiConversationRespDTO](#schemaresultipageaiconversationrespdto)|

## PUT 更新会话信息

PUT /api/xunzhi/v1/ai/conversations/{sessionId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|path|string| 是 |none|
|messageCount|query|integer| 否 |none|
|title|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": null,
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultVoid](#schemaresultvoid)|

## DELETE 删除会话

DELETE /api/xunzhi/v1/ai/conversations/{sessionId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|path|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": null,
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultVoid](#schemaresultvoid)|

## GET 根据会话ID获取会话信息

GET /api/xunzhi/v1/ai/conversations/{sessionId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|path|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "sessionId": "",
    "username": "",
    "aiId": 0,
    "aiName": "",
    "title": "",
    "status": 0,
    "messageCount": 0,
    "lastMessageTime": "",
    "createTime": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultAiConversationRespDTO](#schemaresultaiconversationrespdto)|

## PUT 结束会话

PUT /api/xunzhi/v1/ai/conversations/{sessionId}/end

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|path|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": null,
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultVoid](#schemaresultvoid)|

# 数据模型

<h2 id="tocS_ResultVoid">ResultVoid</h2>

<a id="schemaresultvoid"></a>
<a id="schema_ResultVoid"></a>
<a id="tocSresultvoid"></a>
<a id="tocsresultvoid"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": null,
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|null|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_OrderItem">OrderItem</h2>

<a id="schemaorderitem"></a>
<a id="schema_OrderItem"></a>
<a id="tocSorderitem"></a>
<a id="tocsorderitem"></a>

```json
{
  "column": "string",
  "asc": true
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|column|string|false|none||none|
|asc|boolean|false|none||none|

<h2 id="tocS_AiSessionCreateRespDTO">AiSessionCreateRespDTO</h2>

<a id="schemaaisessioncreaterespdto"></a>
<a id="schema_AiSessionCreateRespDTO"></a>
<a id="tocSaisessioncreaterespdto"></a>
<a id="tocsaisessioncreaterespdto"></a>

```json
{
  "sessionId": "string",
  "conversationTitle": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|sessionId|string|false|none||会话ID|
|conversationTitle|string|false|none||会话标题|

<h2 id="tocS_ResultAiSessionCreateRespDTO">ResultAiSessionCreateRespDTO</h2>

<a id="schemaresultaisessioncreaterespdto"></a>
<a id="schema_ResultAiSessionCreateRespDTO"></a>
<a id="tocSresultaisessioncreaterespdto"></a>
<a id="tocsresultaisessioncreaterespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "sessionId": "string",
    "conversationTitle": "string"
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[AiSessionCreateRespDTO](#schemaaisessioncreaterespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_AiSessionCreateReqDTO">AiSessionCreateReqDTO</h2>

<a id="schemaaisessioncreatereqdto"></a>
<a id="schema_AiSessionCreateReqDTO"></a>
<a id="tocSaisessioncreatereqdto"></a>
<a id="tocsaisessioncreatereqdto"></a>

```json
{
  "userName": "string",
  "aiId": 0,
  "firstMessage": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userName|string|false|none||用户名|
|aiId|integer(int64)|false|none||AI配置ID|
|firstMessage|string|false|none||第一条消息|

<h2 id="tocS_AiConversationRespDTO">AiConversationRespDTO</h2>

<a id="schemaaiconversationrespdto"></a>
<a id="schema_AiConversationRespDTO"></a>
<a id="tocSaiconversationrespdto"></a>
<a id="tocsaiconversationrespdto"></a>

```json
{
  "sessionId": "string",
  "username": "string",
  "aiId": 0,
  "aiName": "string",
  "title": "string",
  "status": 0,
  "messageCount": 0,
  "lastMessageTime": "string",
  "createTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|sessionId|string|false|none||会话ID|
|username|string|false|none||用户名|
|aiId|integer(int64)|false|none||AI配置ID|
|aiName|string|false|none||AI名称|
|title|string|false|none||会话标题|
|status|integer|false|none||会话状态：1-进行中，2-已结束|
|messageCount|integer|false|none||消息总数|
|lastMessageTime|string|false|none||最后一条消息时间|
|createTime|string|false|none||创建时间|

<h2 id="tocS_IPageAiConversationRespDTO">IPageAiConversationRespDTO</h2>

<a id="schemaipageaiconversationrespdto"></a>
<a id="schema_IPageAiConversationRespDTO"></a>
<a id="tocSipageaiconversationrespdto"></a>
<a id="tocsipageaiconversationrespdto"></a>

```json
{
  "records": [
    {
      "sessionId": "string",
      "username": "string",
      "aiId": 0,
      "aiName": "string",
      "title": "string",
      "status": 0,
      "messageCount": 0,
      "lastMessageTime": "string",
      "createTime": "string"
    }
  ],
  "total": 0,
  "size": 0,
  "current": 0,
  "orders": [
    {
      "column": "string",
      "asc": true
    }
  ],
  "optimizeCountSql": true,
  "searchCount": true,
  "optimizeJoinOfCountSql": true,
  "maxLimit": 0,
  "countId": "string",
  "pages": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|records|[[AiConversationRespDTO](#schemaaiconversationrespdto)]|false|none||none|
|total|integer(int64)|false|none||none|
|size|integer(int64)|false|none||none|
|current|integer(int64)|false|none||none|
|orders|[[OrderItem](#schemaorderitem)]|false|none||none|
|optimizeCountSql|boolean|false|none||none|
|searchCount|boolean|false|none||none|
|optimizeJoinOfCountSql|boolean|false|none||none|
|maxLimit|integer(int64)|false|none||none|
|countId|string|false|none||none|
|pages|integer(int64)|false|none||none|

<h2 id="tocS_ResultIPageAiConversationRespDTO">ResultIPageAiConversationRespDTO</h2>

<a id="schemaresultipageaiconversationrespdto"></a>
<a id="schema_ResultIPageAiConversationRespDTO"></a>
<a id="tocSresultipageaiconversationrespdto"></a>
<a id="tocsresultipageaiconversationrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "records": [
      {
        "sessionId": "string",
        "username": "string",
        "aiId": 0,
        "aiName": "string",
        "title": "string",
        "status": 0,
        "messageCount": 0,
        "lastMessageTime": "string",
        "createTime": "string"
      }
    ],
    "total": 0,
    "size": 0,
    "current": 0,
    "orders": [
      {
        "column": "string",
        "asc": true
      }
    ],
    "optimizeCountSql": true,
    "searchCount": true,
    "optimizeJoinOfCountSql": true,
    "maxLimit": 0,
    "countId": "string",
    "pages": 0
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
|data|[IPageAiConversationRespDTO](#schemaipageaiconversationrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|
|success|boolean|false|none||none|

<h2 id="tocS_ResultAiConversationRespDTO">ResultAiConversationRespDTO</h2>

<a id="schemaresultaiconversationrespdto"></a>
<a id="schema_ResultAiConversationRespDTO"></a>
<a id="tocSresultaiconversationrespdto"></a>
<a id="tocsresultaiconversationrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "sessionId": "string",
    "username": "string",
    "aiId": 0,
    "aiName": "string",
    "title": "string",
    "status": 0,
    "messageCount": 0,
    "lastMessageTime": "string",
    "createTime": "string"
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[AiConversationRespDTO](#schemaaiconversationrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

