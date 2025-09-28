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

# agent配置管理层

## POST 创建agent配置

POST /api/xunzhi/v1/agent-properties

> Body 请求参数

```json
{
  "id": 0,
  "agentName": "string",
  "apiSecret": "string",
  "apiKey": "string",
  "apiFlowId": "string",
  "tagCodes": [
    0
  ],
  "pageNum": 0,
  "pageSize": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AgentPropertiesReqDTO](#schemaagentpropertiesreqdto)| 否 |none|

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

## PUT 更新agent配置

PUT /api/xunzhi/v1/agent-properties

> Body 请求参数

```json
{
  "id": 0,
  "agentName": "string",
  "apiSecret": "string",
  "apiKey": "string",
  "apiFlowId": "string",
  "tagCodes": [
    0
  ],
  "pageNum": 0,
  "pageSize": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AgentPropertiesReqDTO](#schemaagentpropertiesreqdto)| 否 |none|

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

## GET 分页查询agent配置

GET /api/xunzhi/v1/agent-properties

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|query|integer(int64)| 否 |none|
|agentName|query|string| 否 |none|
|apiSecret|query|string| 否 |none|
|apiKey|query|string| 否 |none|
|apiFlowId|query|string| 否 |none|
|tagCodes|query|string| 否 |标签代码列表，对应AgentTagType枚举的code值|
|pageNum|query|integer| 否 |当前页码|
|pageSize|query|integer| 否 |每页数量|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "current": 0,
    "size": 0,
    "total": 0,
    "pages": 0,
    "records": [
      {
        "id": 0,
        "agentName": "",
        "apiSecret": "",
        "apiKey": "",
        "apiFlowId": "",
        "tags": [
          {
            "code": 0,
            "name": "",
            "color": ""
          }
        ]
      }
    ]
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultPageInfoAgentPropertiesRespDTO](#schemaresultpageinfoagentpropertiesrespdto)|

## DELETE 根据id删除agent配置

DELETE /api/xunzhi/v1/agent-properties/{id}

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

## GET 根据名称查询agent配置

GET /api/xunzhi/v1/agent-properties/byName

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|name|query|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "id": 0,
    "agentName": "",
    "apiSecret": "",
    "apiKey": "",
    "apiFlowId": "",
    "tags": [
      {
        "code": 0,
        "name": "",
        "color": ""
      }
    ]
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultAgentPropertiesRespDTO](#schemaresultagentpropertiesrespdto)|

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

<h2 id="tocS_AgentPropertiesReqDTO">AgentPropertiesReqDTO</h2>

<a id="schemaagentpropertiesreqdto"></a>
<a id="schema_AgentPropertiesReqDTO"></a>
<a id="tocSagentpropertiesreqdto"></a>
<a id="tocsagentpropertiesreqdto"></a>

```json
{
  "id": 0,
  "agentName": "string",
  "apiSecret": "string",
  "apiKey": "string",
  "apiFlowId": "string",
  "tagCodes": [
    0
  ],
  "pageNum": 0,
  "pageSize": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|agentName|string|false|none||none|
|apiSecret|string|false|none||none|
|apiKey|string|false|none||none|
|apiFlowId|string|false|none||none|
|tagCodes|[integer]|false|none||标签代码列表，对应AgentTagType枚举的code值|
|pageNum|integer|false|none||当前页码|
|pageSize|integer|false|none||每页数量|

<h2 id="tocS_AgentPropertiesRespDTO">AgentPropertiesRespDTO</h2>

<a id="schemaagentpropertiesrespdto"></a>
<a id="schema_AgentPropertiesRespDTO"></a>
<a id="tocSagentpropertiesrespdto"></a>
<a id="tocsagentpropertiesrespdto"></a>

```json
{
  "id": 0,
  "agentName": "string",
  "apiSecret": "string",
  "apiKey": "string",
  "apiFlowId": "string",
  "tags": [
    {
      "code": 0,
      "name": "string",
      "color": "string"
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|agentName|string|false|none||none|
|apiSecret|string|false|none||none|
|apiKey|string|false|none||none|
|apiFlowId|string|false|none||none|
|tags|[[TagInfo](#schemataginfo)]|false|none||标签信息列表|

<h2 id="tocS_ResultAgentPropertiesRespDTO">ResultAgentPropertiesRespDTO</h2>

<a id="schemaresultagentpropertiesrespdto"></a>
<a id="schema_ResultAgentPropertiesRespDTO"></a>
<a id="tocSresultagentpropertiesrespdto"></a>
<a id="tocsresultagentpropertiesrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "id": 0,
    "agentName": "string",
    "apiSecret": "string",
    "apiKey": "string",
    "apiFlowId": "string",
    "tags": [
      {
        "code": 0,
        "name": "string",
        "color": "string"
      }
    ]
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[AgentPropertiesRespDTO](#schemaagentpropertiesrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_PageInfoAgentPropertiesRespDTO">PageInfoAgentPropertiesRespDTO</h2>

<a id="schemapageinfoagentpropertiesrespdto"></a>
<a id="schema_PageInfoAgentPropertiesRespDTO"></a>
<a id="tocSpageinfoagentpropertiesrespdto"></a>
<a id="tocspageinfoagentpropertiesrespdto"></a>

```json
{
  "current": 1,
  "size": 10,
  "total": 0,
  "pages": 0,
  "records": [
    {
      "id": 0,
      "agentName": "string",
      "apiSecret": "string",
      "apiKey": "string",
      "apiFlowId": "string",
      "tags": [
        {
          "code": 0,
          "name": "string",
          "color": "string"
        }
      ]
    }
  ]
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|current|integer(int64)|false|none||当前页码|
|size|integer(int64)|false|none||每页数量|
|total|integer(int64)|false|none||总记录数|
|pages|integer(int64)|false|none||总页数|
|records|[[AgentPropertiesRespDTO](#schemaagentpropertiesrespdto)]|false|none||分页数据|

<h2 id="tocS_ResultPageInfoAgentPropertiesRespDTO">ResultPageInfoAgentPropertiesRespDTO</h2>

<a id="schemaresultpageinfoagentpropertiesrespdto"></a>
<a id="schema_ResultPageInfoAgentPropertiesRespDTO"></a>
<a id="tocSresultpageinfoagentpropertiesrespdto"></a>
<a id="tocsresultpageinfoagentpropertiesrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "current": 1,
    "size": 10,
    "total": 0,
    "pages": 0,
    "records": [
      {
        "id": 0,
        "agentName": "string",
        "apiSecret": "string",
        "apiKey": "string",
        "apiFlowId": "string",
        "tags": [
          {
            "code": null,
            "name": null,
            "color": null
          }
        ]
      }
    ]
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[PageInfoAgentPropertiesRespDTO](#schemapageinfoagentpropertiesrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_TagInfo">TagInfo</h2>

<a id="schemataginfo"></a>
<a id="schema_TagInfo"></a>
<a id="tocStaginfo"></a>
<a id="tocstaginfo"></a>

```json
{
  "code": 0,
  "name": "string",
  "color": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||标签代码|
|name|string|false|none||标签名称|
|color|string|false|none||标签颜色|

