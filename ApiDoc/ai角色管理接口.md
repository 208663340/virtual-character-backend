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

# AI角色控制器

## POST 创建AI角色

POST /api/ai-character

> Body 请求参数

```json
{
  "aiName": "string",
  "aiAvatar": "string",
  "description": "string",
  "aiPrompt": "string",
  "voiceDetailId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AiCharacterCreateReqDTO](#schemaaicharactercreatereqdto)| 否 |none|

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

## PUT 更新AI角色

PUT /api/ai-character

> Body 请求参数

```json
{
  "id": 0,
  "aiName": "string",
  "aiAvatar": "string",
  "description": "string",
  "aiPrompt": "string",
  "voiceDetailId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AiCharacterUpdateReqDTO](#schemaaicharacterupdatereqdto)| 否 |none|

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

## DELETE 删除AI角色

DELETE /api/ai-character/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

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

## GET 根据ID查询AI角色

GET /api/ai-character/{id}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "id": 0,
    "aiName": "",
    "aiAvatar": "",
    "description": "",
    "aiPrompt": "",
    "voiceDetailId": 0,
    "createTime": "",
    "updateTime": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultAiCharacterRespDTO](#schemaresultaicharacterrespdto)|

## POST 分页查询AI角色列表

POST /api/ai-character/page

> Body 请求参数

```json
{
  "records": [
    {
      "createTime": "string",
      "updateTime": "string",
      "delFlag": 0,
      "id": 0,
      "aiName": "string",
      "aiAvatar": "string",
      "description": "string",
      "aiPrompt": "string",
      "voiceDetailId": 0
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
  "aiName": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AiCharacterPageReqDTO](#schemaaicharacterpagereqdto)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "records": [
      {
        "id": 0,
        "aiName": "",
        "aiAvatar": "",
        "description": "",
        "aiPrompt": "",
        "voiceDetailId": 0,
        "createTime": "",
        "updateTime": ""
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
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultIPageAiCharacterRespDTO](#schemaresultipageaicharacterrespdto)|

## GET 查询所有AI角色

GET /api/ai-character/list

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": [
    {
      "id": 0,
      "aiName": "",
      "aiAvatar": "",
      "description": "",
      "aiPrompt": "",
      "voiceDetailId": 0,
      "createTime": "",
      "updateTime": ""
    }
  ],
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultListAiCharacterRespDTO](#schemaresultlistaicharacterrespdto)|

## POST 根据名称搜索AI角色

POST /api/ai-character/search

> Body 请求参数

```json
{
  "aiName": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AiCharacterSearchReqDTO](#schemaaicharactersearchreqdto)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": [
    {
      "id": 0,
      "aiName": "",
      "aiAvatar": "",
      "description": "",
      "aiPrompt": "",
      "voiceDetailId": 0,
      "createTime": "",
      "updateTime": ""
    }
  ],
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultListAiCharacterRespDTO](#schemaresultlistaicharacterrespdto)|

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

<h2 id="tocS_AiCharacterCreateReqDTO">AiCharacterCreateReqDTO</h2>

<a id="schemaaicharactercreatereqdto"></a>
<a id="schema_AiCharacterCreateReqDTO"></a>
<a id="tocSaicharactercreatereqdto"></a>
<a id="tocsaicharactercreatereqdto"></a>

```json
{
  "aiName": "string",
  "aiAvatar": "string",
  "description": "string",
  "aiPrompt": "string",
  "voiceDetailId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|aiName|string|false|none||AI名称|
|aiAvatar|string|false|none||AI头像|
|description|string|false|none||AI角色描述|
|aiPrompt|string|false|none||AI提示词|
|voiceDetailId|integer(int64)|false|none||音色详情ID|

<h2 id="tocS_AiCharacterUpdateReqDTO">AiCharacterUpdateReqDTO</h2>

<a id="schemaaicharacterupdatereqdto"></a>
<a id="schema_AiCharacterUpdateReqDTO"></a>
<a id="tocSaicharacterupdatereqdto"></a>
<a id="tocsaicharacterupdatereqdto"></a>

```json
{
  "id": 0,
  "aiName": "string",
  "aiAvatar": "string",
  "description": "string",
  "aiPrompt": "string",
  "voiceDetailId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||ID|
|aiName|string|false|none||AI名称|
|aiAvatar|string|false|none||AI头像|
|description|string|false|none||AI角色描述|
|aiPrompt|string|false|none||AI提示词|
|voiceDetailId|integer(int64)|false|none||音色详情ID|

<h2 id="tocS_AiCharacterRespDTO">AiCharacterRespDTO</h2>

<a id="schemaaicharacterrespdto"></a>
<a id="schema_AiCharacterRespDTO"></a>
<a id="tocSaicharacterrespdto"></a>
<a id="tocsaicharacterrespdto"></a>

```json
{
  "id": 0,
  "aiName": "string",
  "aiAvatar": "string",
  "description": "string",
  "aiPrompt": "string",
  "voiceDetailId": 0,
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||ID|
|aiName|string|false|none||AI名称|
|aiAvatar|string|false|none||AI头像|
|description|string|false|none||AI角色描述|
|aiPrompt|string|false|none||AI提示词|
|voiceDetailId|integer(int64)|false|none||音色详情ID|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||修改时间|

<h2 id="tocS_ResultAiCharacterRespDTO">ResultAiCharacterRespDTO</h2>

<a id="schemaresultaicharacterrespdto"></a>
<a id="schema_ResultAiCharacterRespDTO"></a>
<a id="tocSresultaicharacterrespdto"></a>
<a id="tocsresultaicharacterrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "id": 0,
    "aiName": "string",
    "aiAvatar": "string",
    "description": "string",
    "aiPrompt": "string",
    "voiceDetailId": 0,
    "createTime": "string",
    "updateTime": "string"
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[AiCharacterRespDTO](#schemaaicharacterrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_IPageAiCharacterRespDTO">IPageAiCharacterRespDTO</h2>

<a id="schemaipageaicharacterrespdto"></a>
<a id="schema_IPageAiCharacterRespDTO"></a>
<a id="tocSipageaicharacterrespdto"></a>
<a id="tocsipageaicharacterrespdto"></a>

```json
{
  "records": [
    {
      "id": 0,
      "aiName": "string",
      "aiAvatar": "string",
      "description": "string",
      "aiPrompt": "string",
      "voiceDetailId": 0,
      "createTime": "string",
      "updateTime": "string"
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
|records|[[AiCharacterRespDTO](#schemaaicharacterrespdto)]|false|none||none|
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

<h2 id="tocS_ResultIPageAiCharacterRespDTO">ResultIPageAiCharacterRespDTO</h2>

<a id="schemaresultipageaicharacterrespdto"></a>
<a id="schema_ResultIPageAiCharacterRespDTO"></a>
<a id="tocSresultipageaicharacterrespdto"></a>
<a id="tocsresultipageaicharacterrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "records": [
      {
        "id": 0,
        "aiName": "string",
        "aiAvatar": "string",
        "description": "string",
        "aiPrompt": "string",
        "voiceDetailId": 0,
        "createTime": "string",
        "updateTime": "string"
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
|data|[IPageAiCharacterRespDTO](#schemaipageaicharacterrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|
|success|boolean|false|none||none|

<h2 id="tocS_AiCharacterDO">AiCharacterDO</h2>

<a id="schemaaicharacterdo"></a>
<a id="schema_AiCharacterDO"></a>
<a id="tocSaicharacterdo"></a>
<a id="tocsaicharacterdo"></a>

```json
{
  "createTime": "string",
  "updateTime": "string",
  "delFlag": 0,
  "id": 0,
  "aiName": "string",
  "aiAvatar": "string",
  "description": "string",
  "aiPrompt": "string",
  "voiceDetailId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||修改时间|
|delFlag|integer|false|none||删除标识 0：未删除 1：已删除|
|id|integer(int64)|false|none||ID|
|aiName|string|false|none||AI名称|
|aiAvatar|string|false|none||AI头像URL|
|description|string|false|none||AI角色描述|
|aiPrompt|string|false|none||AI提示词|
|voiceDetailId|integer(int64)|false|none||音色详情ID|

<h2 id="tocS_AiCharacterPageReqDTO">AiCharacterPageReqDTO</h2>

<a id="schemaaicharacterpagereqdto"></a>
<a id="schema_AiCharacterPageReqDTO"></a>
<a id="tocSaicharacterpagereqdto"></a>
<a id="tocsaicharacterpagereqdto"></a>

```json
{
  "records": [
    {
      "createTime": "string",
      "updateTime": "string",
      "delFlag": 0,
      "id": 0,
      "aiName": "string",
      "aiAvatar": "string",
      "description": "string",
      "aiPrompt": "string",
      "voiceDetailId": 0
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
  "aiName": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|records|[[AiCharacterDO](#schemaaicharacterdo)]|false|none||none|
|total|integer(int64)|false|none||none|
|size|integer(int64)|false|none||none|
|current|integer(int64)|false|none||none|
|orders|[[OrderItem](#schemaorderitem)]|false|none||none|
|optimizeCountSql|boolean|false|none||none|
|searchCount|boolean|false|none||none|
|optimizeJoinOfCountSql|boolean|false|none||none|
|maxLimit|integer(int64)|false|none||none|
|countId|string|false|none||none|
|aiName|string|false|none||AI名称（模糊查询）|

<h2 id="tocS_ResultListAiCharacterRespDTO">ResultListAiCharacterRespDTO</h2>

<a id="schemaresultlistaicharacterrespdto"></a>
<a id="schema_ResultListAiCharacterRespDTO"></a>
<a id="tocSresultlistaicharacterrespdto"></a>
<a id="tocsresultlistaicharacterrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": [
    {
      "id": 0,
      "aiName": "string",
      "aiAvatar": "string",
      "description": "string",
      "aiPrompt": "string",
      "voiceDetailId": 0,
      "createTime": "string",
      "updateTime": "string"
    }
  ],
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[[AiCharacterRespDTO](#schemaaicharacterrespdto)]|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_AiCharacterSearchReqDTO">AiCharacterSearchReqDTO</h2>

<a id="schemaaicharactersearchreqdto"></a>
<a id="schema_AiCharacterSearchReqDTO"></a>
<a id="tocSaicharactersearchreqdto"></a>
<a id="tocsaicharactersearchreqdto"></a>

```json
{
  "aiName": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|aiName|string|false|none||AI名称（模糊查询）|

