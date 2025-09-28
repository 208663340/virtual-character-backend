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

# AI配置控制器

## POST 创建AI配置

POST /api/xunzhi/v1/ai-properties

> Body 请求参数

```json
{
  "aiName": "string",
  "aiType": "string",
  "apiKey": "string",
  "apiSecret": "string",
  "apiUrl": "string",
  "modelName": "string",
  "maxTokens": 0,
  "temperature": 0,
  "systemPrompt": "string",
  "isEnabled": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AiPropertiesCreateReqDTO](#schemaaipropertiescreatereqdto)| 否 |none|

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

## PUT 更新AI配置

PUT /api/xunzhi/v1/ai-properties

> Body 请求参数

```json
{
  "id": 0,
  "aiName": "string",
  "aiType": "string",
  "apiKey": "string",
  "apiSecret": "string",
  "apiUrl": "string",
  "modelName": "string",
  "maxTokens": 0,
  "temperature": 0,
  "systemPrompt": "string",
  "isEnabled": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AiPropertiesUpdateReqDTO](#schemaaipropertiesupdatereqdto)| 否 |none|

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

## GET 分页查询AI配置

GET /api/xunzhi/v1/ai-properties

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|records[0].id|query|integer(int64)| 否 |ID|
|records[0].aiName|query|string| 否 |AI名称|
|records[0].aiType|query|string| 否 |AI类型：spark、openai、claude等|
|records[0].apiKey|query|string| 否 |API密钥|
|records[0].apiSecret|query|string| 否 |API密钥（部分AI需要）|
|records[0].apiUrl|query|string| 否 |API地址|
|records[0].modelName|query|string| 否 |模型名称|
|records[0].maxTokens|query|integer| 否 |最大token数|
|records[0].temperature|query|string| 否 |温度参数|
|records[0].systemPrompt|query|string| 否 |系统提示词|
|records[0].isEnabled|query|integer| 否 |是否启用 0：禁用 1：启用|
|records[0].createTime|query|string| 否 |创建时间|
|records[0].updateTime|query|string| 否 |修改时间|
|records[0].delFlag|query|integer| 否 |删除标识 0：未删除 1：已删除|
|total|query|integer(int64)| 否 |none|
|size|query|integer(int64)| 否 |none|
|current|query|integer(int64)| 否 |none|
|orders[0].column|query|string| 否 |none|
|orders[0].asc|query|boolean| 否 |none|
|optimizeCountSql|query|boolean| 否 |none|
|searchCount|query|boolean| 否 |none|
|optimizeJoinOfCountSql|query|boolean| 否 |none|
|maxLimit|query|integer(int64)| 否 |none|
|countId|query|string| 否 |none|
|aiName|query|string| 否 |AI名称（模糊查询）|
|aiType|query|string| 否 |AI类型|
|isEnabled|query|integer| 否 |是否启用 0：禁用 1：启用|

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
        "aiType": "",
        "apiKey": "",
        "apiUrl": "",
        "modelName": "",
        "maxTokens": 0,
        "temperature": 0,
        "systemPrompt": "",
        "isEnabled": 0,
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
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultIPageAiPropertiesRespDTO](#schemaresultipageaipropertiesrespdto)|

## DELETE 删除AI配置

DELETE /api/xunzhi/v1/ai-properties/{id}

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

## GET 根据ID查询AI配置

GET /api/xunzhi/v1/ai-properties/{id}

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
    "aiType": "",
    "apiKey": "",
    "apiUrl": "",
    "modelName": "",
    "maxTokens": 0,
    "temperature": 0,
    "systemPrompt": "",
    "isEnabled": 0,
    "createTime": "",
    "updateTime": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultAiPropertiesRespDTO](#schemaresultaipropertiesrespdto)|

## GET 查询所有启用的AI配置

GET /api/xunzhi/v1/ai-properties/enabled

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
      "aiType": "",
      "apiKey": "",
      "apiUrl": "",
      "modelName": "",
      "maxTokens": 0,
      "temperature": 0,
      "systemPrompt": "",
      "isEnabled": 0,
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
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultListAiPropertiesRespDTO](#schemaresultlistaipropertiesrespdto)|

## PUT 启用/禁用AI配置

PUT /api/xunzhi/v1/ai-properties/{id}/status

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |none|
|isEnabled|query|integer| 是 |none|

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

<h2 id="tocS_AiPropertiesCreateReqDTO">AiPropertiesCreateReqDTO</h2>

<a id="schemaaipropertiescreatereqdto"></a>
<a id="schema_AiPropertiesCreateReqDTO"></a>
<a id="tocSaipropertiescreatereqdto"></a>
<a id="tocsaipropertiescreatereqdto"></a>

```json
{
  "aiName": "string",
  "aiType": "string",
  "apiKey": "string",
  "apiSecret": "string",
  "apiUrl": "string",
  "modelName": "string",
  "maxTokens": 0,
  "temperature": 0,
  "systemPrompt": "string",
  "isEnabled": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|aiName|string|false|none||AI名称|
|aiType|string|false|none||AI类型：spark、openai、claude等|
|apiKey|string|false|none||API密钥|
|apiSecret|string|false|none||API密钥（部分AI需要）|
|apiUrl|string|false|none||API地址|
|modelName|string|false|none||模型名称|
|maxTokens|integer|false|none||最大token数|
|temperature|number|false|none||温度参数|
|systemPrompt|string|false|none||系统提示词|
|isEnabled|integer|false|none||是否启用 0：禁用 1：启用|

<h2 id="tocS_AiPropertiesUpdateReqDTO">AiPropertiesUpdateReqDTO</h2>

<a id="schemaaipropertiesupdatereqdto"></a>
<a id="schema_AiPropertiesUpdateReqDTO"></a>
<a id="tocSaipropertiesupdatereqdto"></a>
<a id="tocsaipropertiesupdatereqdto"></a>

```json
{
  "id": 0,
  "aiName": "string",
  "aiType": "string",
  "apiKey": "string",
  "apiSecret": "string",
  "apiUrl": "string",
  "modelName": "string",
  "maxTokens": 0,
  "temperature": 0,
  "systemPrompt": "string",
  "isEnabled": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||ID|
|aiName|string|false|none||AI名称|
|aiType|string|false|none||AI类型：spark、openai、claude等|
|apiKey|string|false|none||API密钥|
|apiSecret|string|false|none||API密钥（部分AI需要）|
|apiUrl|string|false|none||API地址|
|modelName|string|false|none||模型名称|
|maxTokens|integer|false|none||最大token数|
|temperature|number|false|none||温度参数|
|systemPrompt|string|false|none||系统提示词|
|isEnabled|integer|false|none||是否启用 0：禁用 1：启用|

<h2 id="tocS_AiPropertiesRespDTO">AiPropertiesRespDTO</h2>

<a id="schemaaipropertiesrespdto"></a>
<a id="schema_AiPropertiesRespDTO"></a>
<a id="tocSaipropertiesrespdto"></a>
<a id="tocsaipropertiesrespdto"></a>

```json
{
  "id": 0,
  "aiName": "string",
  "aiType": "string",
  "apiKey": "string",
  "apiUrl": "string",
  "modelName": "string",
  "maxTokens": 0,
  "temperature": 0,
  "systemPrompt": "string",
  "isEnabled": 0,
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||ID|
|aiName|string|false|none||AI名称|
|aiType|string|false|none||AI类型：spark、openai、claude等|
|apiKey|string|false|none||API密钥（脱敏显示）|
|apiUrl|string|false|none||API地址|
|modelName|string|false|none||模型名称|
|maxTokens|integer|false|none||最大token数|
|temperature|number|false|none||温度参数|
|systemPrompt|string|false|none||系统提示词|
|isEnabled|integer|false|none||是否启用 0：禁用 1：启用|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||修改时间|

<h2 id="tocS_ResultAiPropertiesRespDTO">ResultAiPropertiesRespDTO</h2>

<a id="schemaresultaipropertiesrespdto"></a>
<a id="schema_ResultAiPropertiesRespDTO"></a>
<a id="tocSresultaipropertiesrespdto"></a>
<a id="tocsresultaipropertiesrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "id": 0,
    "aiName": "string",
    "aiType": "string",
    "apiKey": "string",
    "apiUrl": "string",
    "modelName": "string",
    "maxTokens": 0,
    "temperature": 0,
    "systemPrompt": "string",
    "isEnabled": 0,
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
|data|[AiPropertiesRespDTO](#schemaaipropertiesrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_IPageAiPropertiesRespDTO">IPageAiPropertiesRespDTO</h2>

<a id="schemaipageaipropertiesrespdto"></a>
<a id="schema_IPageAiPropertiesRespDTO"></a>
<a id="tocSipageaipropertiesrespdto"></a>
<a id="tocsipageaipropertiesrespdto"></a>

```json
{
  "records": [
    {
      "id": 0,
      "aiName": "string",
      "aiType": "string",
      "apiKey": "string",
      "apiUrl": "string",
      "modelName": "string",
      "maxTokens": 0,
      "temperature": 0,
      "systemPrompt": "string",
      "isEnabled": 0,
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
|records|[[AiPropertiesRespDTO](#schemaaipropertiesrespdto)]|false|none||none|
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

<h2 id="tocS_ResultIPageAiPropertiesRespDTO">ResultIPageAiPropertiesRespDTO</h2>

<a id="schemaresultipageaipropertiesrespdto"></a>
<a id="schema_ResultIPageAiPropertiesRespDTO"></a>
<a id="tocSresultipageaipropertiesrespdto"></a>
<a id="tocsresultipageaipropertiesrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "records": [
      {
        "id": 0,
        "aiName": "string",
        "aiType": "string",
        "apiKey": "string",
        "apiUrl": "string",
        "modelName": "string",
        "maxTokens": 0,
        "temperature": 0,
        "systemPrompt": "string",
        "isEnabled": 0,
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
|data|[IPageAiPropertiesRespDTO](#schemaipageaipropertiesrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|
|success|boolean|false|none||none|

<h2 id="tocS_ResultListAiPropertiesRespDTO">ResultListAiPropertiesRespDTO</h2>

<a id="schemaresultlistaipropertiesrespdto"></a>
<a id="schema_ResultListAiPropertiesRespDTO"></a>
<a id="tocSresultlistaipropertiesrespdto"></a>
<a id="tocsresultlistaipropertiesrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": [
    {
      "id": 0,
      "aiName": "string",
      "aiType": "string",
      "apiKey": "string",
      "apiUrl": "string",
      "modelName": "string",
      "maxTokens": 0,
      "temperature": 0,
      "systemPrompt": "string",
      "isEnabled": 0,
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
|data|[[AiPropertiesRespDTO](#schemaaipropertiesrespdto)]|false|none||响应数据|
|requestId|string|false|none||请求ID|

