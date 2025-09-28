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

# WebSocket管理控制器

## GET checkUserStatus

GET /api/xunzhi/v1/websocket/user/{userId}/status

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|path|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResponseEntityMapObject](#schemaresponseentitymapobject)|

## POST sendMessage

POST /api/xunzhi/v1/websocket/send-message

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|query|string| 是 |none|
|type|query|string| 是 |none|
|message|query|string| 是 |none|
|data|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResponseEntityMapObject](#schemaresponseentitymapobject)|

## POST sendNotification

POST /api/xunzhi/v1/websocket/notification/{userId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|path|string| 是 |none|
|message|query|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResponseEntityMapObject](#schemaresponseentitymapobject)|

## POST sendTranscriptionResult

POST /api/xunzhi/v1/websocket/transcription/{userId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|path|string| 是 |none|
|result|query|string| 是 |none|
|isFinal|query|boolean| 是 |none|

> 返回示例

> 200 Response

```json
{
  "": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResponseEntityMapObject](#schemaresponseentitymapobject)|

## POST sendErrorMessage

POST /api/xunzhi/v1/websocket/error/{userId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|path|string| 是 |none|
|errorMessage|query|string| 是 |none|

> 返回示例

> 200 Response

```json
{
  "": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResponseEntityMapObject](#schemaresponseentitymapobject)|

# 数据模型

<h2 id="tocS_ResponseEntityMapObject">ResponseEntityMapObject</h2>

<a id="schemaresponseentitymapobject"></a>
<a id="schema_ResponseEntityMapObject"></a>
<a id="tocSresponseentitymapobject"></a>
<a id="tocsresponseentitymapobject"></a>

```json
{
  "key": {}
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|key|object|false|none||none|

