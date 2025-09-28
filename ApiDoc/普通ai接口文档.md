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

# AI消息控制器

## POST AI聊天SSE接口

POST /api/xunzhi/v1/ai/sessions/{sessionId}/chat

> Body 请求参数

```json
{
  "sessionId": "string",
  "inputMessage": "string",
  "aiId": 0,
  "messageSeq": 0,
  "userName": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|path|string| 是 |none|
|body|body|[AiMessageReqDTO](#schemaaimessagereqdto)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[SseEmitter](#schemasseemitter)|

## GET 查询会话历史消息

GET /api/xunzhi/v1/ai/history/{sessionId}

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
  "data": [
    {
      "id": "",
      "sessionId": "",
      "messageType": 0,
      "messageContent": "",
      "messageSeq": 0,
      "tokenCount": 0,
      "responseTime": 0,
      "errorMessage": "",
      "createTime": ""
    }
  ],
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultListAiMessageHistoryRespDTO](#schemaresultlistaimessagehistoryrespdto)|

## GET 分页查询历史消息

GET /api/xunzhi/v1/ai/history/page

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|query|string| 否 |none|
|current|query|integer| 是 |none|
|size|query|integer| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "records": [
      {
        "id": "",
        "sessionId": "",
        "messageType": 0,
        "messageContent": "",
        "messageSeq": 0,
        "tokenCount": 0,
        "responseTime": 0,
        "errorMessage": "",
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
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultIPageAiMessageHistoryRespDTO](#schemaresultipageaimessagehistoryrespdto)|

# 数据模型

<h2 id="tocS_Handler">Handler</h2>

<a id="schemahandler"></a>
<a id="schema_Handler"></a>
<a id="tocShandler"></a>
<a id="tocshandler"></a>

```json
{}

```

### 属性

*None*

<h2 id="tocS_MapString">MapString</h2>

<a id="schemamapstring"></a>
<a id="schema_MapString"></a>
<a id="tocSmapstring"></a>
<a id="tocsmapstring"></a>

```json
{
  "key": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|key|string|false|none||none|

<h2 id="tocS_MediaType">MediaType</h2>

<a id="schemamediatype"></a>
<a id="schema_MediaType"></a>
<a id="tocSmediatype"></a>
<a id="tocsmediatype"></a>

```json
{
  "type": "string",
  "subtype": "string",
  "parameters": {
    "key": "string"
  },
  "toStringValue": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|type|string|false|none||none|
|subtype|string|false|none||none|
|parameters|[MapString](#schemamapstring)|false|none||none|
|toStringValue|string¦null|false|none||none|

<h2 id="tocS_DataWithMediaType">DataWithMediaType</h2>

<a id="schemadatawithmediatype"></a>
<a id="schema_DataWithMediaType"></a>
<a id="tocSdatawithmediatype"></a>
<a id="tocsdatawithmediatype"></a>

```json
{
  "data": {},
  "mediaType": {
    "type": "string",
    "subtype": "string",
    "parameters": {
      "key": "string"
    },
    "toStringValue": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|data|object|false|none||none|
|mediaType|[MediaType](#schemamediatype)|false|none||none|

<h2 id="tocS_Throwable">Throwable</h2>

<a id="schemathrowable"></a>
<a id="schema_Throwable"></a>
<a id="tocSthrowable"></a>
<a id="tocsthrowable"></a>

```json
{
  "detailMessage": "string",
  "cause": "this",
  "stackTrace": "new StackTraceElement[0]",
  "suppressedExceptions": "Collections.emptyList()"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|detailMessage|string|false|none||Specific details about the Throwable.  For example, for<br />{@code FileNotFoundException}, this contains the name of<br />the file that could not be found.|
|cause|[Throwable](#schemathrowable)|false|none||The throwable that caused this throwable to get thrown, or null if this<br />throwable was not caused by another throwable, or if the causative<br />throwable is unknown.  If this field is equal to this throwable itself,<br />it indicates that the cause of this throwable has not yet been<br />initialized.|
|stackTrace|[[StackTraceElement](#schemastacktraceelement)]|false|none||The stack trace, as returned by{@link #getStackTrace()}.<br /><br />The field is initialized to a zero-length array.  A{@code<br />    * null} value of this field indicates subsequent calls to{@link<br />    * #setStackTrace(StackTraceElement[])} and{@link<br />    * #fillInStackTrace()} will be no-ops.|
|suppressedExceptions|[[Throwable](#schemathrowable)]|false|none||The list of suppressed exceptions, as returned by{@link<br />    * #getSuppressed()}.  The list is initialized to a zero-element<br />unmodifiable sentinel list.  When a serialized Throwable is<br />read in, if the{@code suppressedExceptions} field points to a<br />zero-element list, the field is reset to the sentinel value.|

<h2 id="tocS_StackTraceElement">StackTraceElement</h2>

<a id="schemastacktraceelement"></a>
<a id="schema_StackTraceElement"></a>
<a id="tocSstacktraceelement"></a>
<a id="tocsstacktraceelement"></a>

```json
{
  "classLoaderName": "string",
  "moduleName": "string",
  "moduleVersion": "string",
  "declaringClass": "string",
  "methodName": "string",
  "fileName": "string",
  "lineNumber": 0,
  "format": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|classLoaderName|string|false|none||The name of the class loader.|
|moduleName|string|false|none||The module name.|
|moduleVersion|string|false|none||The module version.|
|declaringClass|string|false|none||The declaring class.|
|methodName|string|false|none||The method name.|
|fileName|string|false|none||The source file name.|
|lineNumber|integer|false|none||The source line number.|
|format|integer|false|none||Control to show full or partial module, package, and class names.|

<h2 id="tocS_Runnable">Runnable</h2>

<a id="schemarunnable"></a>
<a id="schema_Runnable"></a>
<a id="tocSrunnable"></a>
<a id="tocsrunnable"></a>

```json
{}

```

### 属性

*None*

<h2 id="tocS_DefaultCallback">DefaultCallback</h2>

<a id="schemadefaultcallback"></a>
<a id="schema_DefaultCallback"></a>
<a id="tocSdefaultcallback"></a>
<a id="tocsdefaultcallback"></a>

```json
{
  "delegate": {}
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|delegate|[Runnable](#schemarunnable)|false|none||none|

<h2 id="tocS_ErrorCallback">ErrorCallback</h2>

<a id="schemaerrorcallback"></a>
<a id="schema_ErrorCallback"></a>
<a id="tocSerrorcallback"></a>
<a id="tocserrorcallback"></a>

```json
{}

```

### 属性

*None*

<h2 id="tocS_SseEmitter">SseEmitter</h2>

<a id="schemasseemitter"></a>
<a id="schema_SseEmitter"></a>
<a id="tocSsseemitter"></a>
<a id="tocssseemitter"></a>

```json
{
  "timeout": 0,
  "handler": {},
  "earlySendAttempts": "new LinkedHashSet<>(8)",
  "complete": true,
  "failure": {
    "detailMessage": "string",
    "cause": "this",
    "stackTrace": "new StackTraceElement[0]",
    "suppressedExceptions": "Collections.emptyList()"
  },
  "sendFailed": true,
  "timeoutCallback": "new DefaultCallback()",
  "errorCallback": "new ErrorCallback()",
  "completionCallback": "new DefaultCallback()"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|timeout|integer(int64)¦null|false|none||none|
|handler|[Handler](#schemahandler)|false|none||none|
|earlySendAttempts|[[DataWithMediaType](#schemadatawithmediatype)]|false|none||Store send data before handler is initialized.|
|complete|boolean|false|none||Store successful completion before the handler is initialized.|
|failure|[Throwable](#schemathrowable)|false|none||Store an error before the handler is initialized.|
|sendFailed|boolean|false|none||After an I/O error, we don't call{@link #completeWithError} directly but<br />wait for the Servlet container to call us via{@code AsyncListener#onError}<br />on a container thread at which point we call completeWithError.<br />This flag is used to ignore further calls to complete or completeWithError<br />that may come for example from an application try-catch block on the<br />thread of the I/O error.|
|timeoutCallback|[DefaultCallback](#schemadefaultcallback)|false|none||none|
|errorCallback|[ErrorCallback](#schemaerrorcallback)|false|none||none|
|completionCallback|[DefaultCallback](#schemadefaultcallback)|false|none||none|

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

<h2 id="tocS_AiMessageHistoryRespDTO">AiMessageHistoryRespDTO</h2>

<a id="schemaaimessagehistoryrespdto"></a>
<a id="schema_AiMessageHistoryRespDTO"></a>
<a id="tocSaimessagehistoryrespdto"></a>
<a id="tocsaimessagehistoryrespdto"></a>

```json
{
  "id": "string",
  "sessionId": "string",
  "messageType": 0,
  "messageContent": "string",
  "messageSeq": 0,
  "tokenCount": 0,
  "responseTime": 0,
  "errorMessage": "string",
  "createTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|string|false|none||消息ID|
|sessionId|string|false|none||会话ID|
|messageType|integer|false|none||消息类型：1-用户消息，2-AI回复|
|messageContent|string|false|none||消息内容|
|messageSeq|integer|false|none||消息序号|
|tokenCount|integer|false|none||Token消耗数量|
|responseTime|integer|false|none||响应时间(毫秒)|
|errorMessage|string|false|none||错误信息|
|createTime|string|false|none||创建时间|

<h2 id="tocS_ResultListAiMessageHistoryRespDTO">ResultListAiMessageHistoryRespDTO</h2>

<a id="schemaresultlistaimessagehistoryrespdto"></a>
<a id="schema_ResultListAiMessageHistoryRespDTO"></a>
<a id="tocSresultlistaimessagehistoryrespdto"></a>
<a id="tocsresultlistaimessagehistoryrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": [
    {
      "id": "string",
      "sessionId": "string",
      "messageType": 0,
      "messageContent": "string",
      "messageSeq": 0,
      "tokenCount": 0,
      "responseTime": 0,
      "errorMessage": "string",
      "createTime": "string"
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
|data|[[AiMessageHistoryRespDTO](#schemaaimessagehistoryrespdto)]|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_IPageAiMessageHistoryRespDTO">IPageAiMessageHistoryRespDTO</h2>

<a id="schemaipageaimessagehistoryrespdto"></a>
<a id="schema_IPageAiMessageHistoryRespDTO"></a>
<a id="tocSipageaimessagehistoryrespdto"></a>
<a id="tocsipageaimessagehistoryrespdto"></a>

```json
{
  "records": [
    {
      "id": "string",
      "sessionId": "string",
      "messageType": 0,
      "messageContent": "string",
      "messageSeq": 0,
      "tokenCount": 0,
      "responseTime": 0,
      "errorMessage": "string",
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
|records|[[AiMessageHistoryRespDTO](#schemaaimessagehistoryrespdto)]|false|none||none|
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

<h2 id="tocS_ResultIPageAiMessageHistoryRespDTO">ResultIPageAiMessageHistoryRespDTO</h2>

<a id="schemaresultipageaimessagehistoryrespdto"></a>
<a id="schema_ResultIPageAiMessageHistoryRespDTO"></a>
<a id="tocSresultipageaimessagehistoryrespdto"></a>
<a id="tocsresultipageaimessagehistoryrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "records": [
      {
        "id": "string",
        "sessionId": "string",
        "messageType": 0,
        "messageContent": "string",
        "messageSeq": 0,
        "tokenCount": 0,
        "responseTime": 0,
        "errorMessage": "string",
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
|data|[IPageAiMessageHistoryRespDTO](#schemaipageaimessagehistoryrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|
|success|boolean|false|none||none|

<h2 id="tocS_AiMessageReqDTO">AiMessageReqDTO</h2>

<a id="schemaaimessagereqdto"></a>
<a id="schema_AiMessageReqDTO"></a>
<a id="tocSaimessagereqdto"></a>
<a id="tocsaimessagereqdto"></a>

```json
{
  "sessionId": "string",
  "inputMessage": "string",
  "aiId": 0,
  "messageSeq": 0,
  "userName": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|sessionId|string|false|none||会话ID|
|inputMessage|string|false|none||用户输入消息|
|aiId|integer(int64)|false|none||AI配置ID|
|messageSeq|integer|false|none||消息序号|
|userName|string|false|none||用户名|

