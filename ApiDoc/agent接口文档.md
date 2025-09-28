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

# Agent文字聊天接口

## POST 创建Agent会话

POST /api/xunzhi/v1/agents/sessions

> Body 请求参数

```json
{
  "userName": "string",
  "agentId": 0,
  "firstMessage": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AgentSessionCreateReqDTO](#schemaagentsessioncreatereqdto)| 否 |none|

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
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultAgentSessionCreateRespDTO](#schemaresultagentsessioncreaterespdto)|

## POST Agent文字聊天SSE接口

POST /api/xunzhi/v1/agents/sessions/{sessionId}/chat

> Body 请求参数

```json
{
  "userName": "string",
  "agentId": 0,
  "inputMessage": "string",
  "messageSeq": 0,
  "sessionId": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|path|string| 是 |none|
|body|body|[UserMessageReqDTO](#schemausermessagereqdto)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "timeout": 0,
  "handler": {},
  "earlySendAttempts": [
    {
      "data": {},
      "mediaType": {
        "type": "",
        "subtype": "",
        "parameters": {
          "": ""
        },
        "toStringValue": ""
      }
    }
  ],
  "complete": false,
  "failure": {
    "detailMessage": "",
    "cause": {
      "detailMessage": "",
      "cause": {},
      "stackTrace": [
        {
          "classLoaderName": "",
          "moduleName": "",
          "moduleVersion": "",
          "declaringClass": "",
          "methodName": "",
          "fileName": "",
          "lineNumber": 0,
          "format": 0
        }
      ],
      "suppressedExceptions": [
        {}
      ]
    },
    "stackTrace": [
      {
        "classLoaderName": "",
        "moduleName": "",
        "moduleVersion": "",
        "declaringClass": "",
        "methodName": "",
        "fileName": "",
        "lineNumber": 0,
        "format": 0
      }
    ],
    "suppressedExceptions": [
      {
        "detailMessage": "",
        "cause": {},
        "stackTrace": [
          {
            "classLoaderName": "",
            "moduleName": "",
            "moduleVersion": "",
            "declaringClass": "",
            "methodName": "",
            "fileName": "",
            "lineNumber": 0,
            "format": 0
          }
        ],
        "suppressedExceptions": [
          {}
        ]
      }
    ]
  },
  "sendFailed": false,
  "timeoutCallback": {
    "delegate": {}
  },
  "errorCallback": {},
  "completionCallback": {
    "delegate": {}
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[SseEmitter](#schemasseemitter)|

## GET 分页查询用户会话列表

GET /api/xunzhi/v1/agents/conversations

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|current|query|integer| 否 |当前页码|
|size|query|integer| 否 |每页大小|
|agentId|query|integer(int64)| 否 |智能体ID（可选）|
|status|query|integer| 否 |会话状态（可选）：1-进行中，2-已结束|
|keyword|query|string| 否 |搜索关键词（可选）|

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
        "agentId": 0,
        "agentName": "",
        "conversationTitle": "",
        "messageCount": 0,
        "totalTokens": 0,
        "status": 0,
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
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultIPageAgentConversationRespDTO](#schemaresultipageagentconversationrespdto)|

## POST Agent文字聊天Flux接口 - 响应式流，性能更优

POST /api/xunzhi/v1/agents/sessions/{sessionId}/chat-flux

> Body 请求参数

```json
{
  "userName": "string",
  "agentId": 0,
  "inputMessage": "string",
  "messageSeq": 0,
  "sessionId": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|path|string| 是 |none|
|body|body|[UserMessageReqDTO](#schemausermessagereqdto)| 否 |none|

> 返回示例

> 200 Response

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET 查询会话历史消息

GET /api/xunzhi/v1/agents/conversations/{sessionId}/messages

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
      "createTime": ""
    }
  ],
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultListAgentMessageHistoryRespDTO](#schemaresultlistagentmessagehistoryrespdto)|

## GET 分页查询历史消息

GET /api/xunzhi/v1/agents/messages/history

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
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultIPageAgentMessageHistoryRespDTO](#schemaresultipageagentmessagehistoryrespdto)|

## PUT 结束会话

PUT /api/xunzhi/v1/agents/conversations/{sessionId}/end

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

## POST 面试题获取接口

POST /api/xunzhi/v1/agents/sessions/{sessionId}/interview-questions

> Body 请求参数

```yaml
resumePdf: string

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|path|string| 是 |none|
|agentId|query|integer| 是 |none|
|body|body|object| 否 |none|
|» resumePdf|body|string(binary)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "timeout": 0,
  "handler": {},
  "earlySendAttempts": [
    {
      "data": {},
      "mediaType": {
        "type": "",
        "subtype": "",
        "parameters": {
          "": ""
        },
        "toStringValue": ""
      }
    }
  ],
  "complete": false,
  "failure": {
    "detailMessage": "",
    "cause": {
      "detailMessage": "",
      "cause": {},
      "stackTrace": [
        {
          "classLoaderName": "",
          "moduleName": "",
          "moduleVersion": "",
          "declaringClass": "",
          "methodName": "",
          "fileName": "",
          "lineNumber": 0,
          "format": 0
        }
      ],
      "suppressedExceptions": [
        {}
      ]
    },
    "stackTrace": [
      {
        "classLoaderName": "",
        "moduleName": "",
        "moduleVersion": "",
        "declaringClass": "",
        "methodName": "",
        "fileName": "",
        "lineNumber": 0,
        "format": 0
      }
    ],
    "suppressedExceptions": [
      {
        "detailMessage": "",
        "cause": {},
        "stackTrace": [
          {
            "classLoaderName": "",
            "moduleName": "",
            "moduleVersion": "",
            "declaringClass": "",
            "methodName": "",
            "fileName": "",
            "lineNumber": 0,
            "format": 0
          }
        ],
        "suppressedExceptions": [
          {}
        ]
      }
    ]
  },
  "sendFailed": false,
  "timeoutCallback": {
    "delegate": {}
  },
  "errorCallback": {},
  "completionCallback": {
    "delegate": {}
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultInterviewQuestionRespDTO](#schemaresultinterviewquestionrespdto)|

## POST 回答面试题接口

POST /api/xunzhi/v1/agents/interview/answer

> Body 请求参数

```yaml
audioFile: string

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|questionNumber|query|string| 是 |none|
|answerContent|query|string| 否 |none|
|sessionId|query|string| 否 |none|
|agentId|query|integer| 否 |none|
|body|body|object| 否 |none|
|» audioFile|body|string(binary)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "questionNumber": "",
    "questionContent": "",
    "score": 0,
    "totalScore": 0,
    "isSuccess": false,
    "errorMessage": "",
    "feedback": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultInterviewAnswerRespDTO](#schemaresultinterviewanswerrespdto)|

## GET 获取用户面试题列表

GET /api/xunzhi/v1/agents/interview/questions

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultMapString](#schemaresultmapstring)|

## GET 获取用户当前总分

GET /api/xunzhi/v1/agents/interview/score

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": 0,
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultInteger](#schemaresultinteger)|

## GET 获取用户面试建议列表

GET /api/xunzhi/v1/agents/interview/suggestions

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultMapString](#schemaresultmapstring)|

## GET 获取用户简历评分

GET /api/xunzhi/v1/agents/resume/score

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": 0,
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultInteger](#schemaresultinteger)|

## GET 获取雷达图数据

GET /api/xunzhi/v1/agents/radar-chart

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|sessionId|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "resumeScore": 0,
    "interviewPerformance": 0,
    "demeanorEvaluation": 0,
    "potentialIndex": 0,
    "professionalSkills": 0
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultRadarChartDTO](#schemaresultradarchartdto)|

## POST 神态评分接口

POST /api/xunzhi/v1/agents/demeanor-evaluation

> Body 请求参数

```yaml
userPhoto: string

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|agentId|query|integer| 是 |none|
|sessionId|query|string| 是 |none|
|body|body|object| 否 |none|
|» userPhoto|body|string(binary)| 是 |none|

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

## POST 保存面试记录

POST /api/xunzhi/v1/agents/interview/record

> Body 请求参数

```json
{
  "sessionId": "string",
  "interviewScore": 0,
  "interviewSuggestions": "string",
  "interviewDirection": "string",
  "username": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[InterviewRecordSaveReqDTO](#schemainterviewrecordsavereqdto)| 否 |none|

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

## GET 分页查询面试记录

GET /api/xunzhi/v1/agents/interview/records

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|pageNum|query|integer| 否 |当前页码|
|pageSize|query|integer| 否 |每页数量|
|sessionId|query|string| 否 |会话ID（可选，用于筛选）|
|minScore|query|integer| 否 |最低分数（可选，用于筛选）|
|maxScore|query|integer| 否 |最高分数（可选，用于筛选）|
|interviewDirection|query|string| 否 |面试方向（可选，用于筛选）|

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
        "userId": 0,
        "sessionId": "",
        "interviewScore": 0,
        "interviewSuggestions": "",
        "interviewSuggestionsMap": {
          "": ""
        },
        "interviewDirection": "",
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
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultIPageInterviewRecordRespDTO](#schemaresultipageinterviewrecordrespdto)|

## GET 根据会话ID获取面试记录

GET /api/xunzhi/v1/agents/interview/record/{sessionId}

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
    "id": 0,
    "userId": 0,
    "sessionId": "",
    "interviewScore": 0,
    "interviewSuggestions": "",
    "interviewSuggestionsMap": {
      "": ""
    },
    "interviewDirection": "",
    "createTime": "",
    "updateTime": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultInterviewRecordRespDTO](#schemaresultinterviewrecordrespdto)|

## POST 从Redis保存面试记录

POST /api/xunzhi/v1/agents/interview/record/save-from-redis/{sessionId}

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

## POST 解析面试建议字符串为Map格式

POST /api/xunzhi/v1/agents/interview/suggestions/parse

> Body 请求参数

```json
"string"
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": "",
  "message": "",
  "data": {
    "": ""
  },
  "requestId": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[ResultMapString](#schemaresultmapstring)|

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

<h2 id="tocS_AgentSessionCreateRespDTO">AgentSessionCreateRespDTO</h2>

<a id="schemaagentsessioncreaterespdto"></a>
<a id="schema_AgentSessionCreateRespDTO"></a>
<a id="tocSagentsessioncreaterespdto"></a>
<a id="tocsagentsessioncreaterespdto"></a>

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

<h2 id="tocS_ResultAgentSessionCreateRespDTO">ResultAgentSessionCreateRespDTO</h2>

<a id="schemaresultagentsessioncreaterespdto"></a>
<a id="schema_ResultAgentSessionCreateRespDTO"></a>
<a id="tocSresultagentsessioncreaterespdto"></a>
<a id="tocsresultagentsessioncreaterespdto"></a>

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
|data|[AgentSessionCreateRespDTO](#schemaagentsessioncreaterespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

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

<h2 id="tocS_AgentSessionCreateReqDTO">AgentSessionCreateReqDTO</h2>

<a id="schemaagentsessioncreatereqdto"></a>
<a id="schema_AgentSessionCreateReqDTO"></a>
<a id="tocSagentsessioncreatereqdto"></a>
<a id="tocsagentsessioncreatereqdto"></a>

```json
{
  "userName": "string",
  "agentId": 0,
  "firstMessage": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userName|string|false|none||用户名|
|agentId|integer(int64)|false|none||AgentID|
|firstMessage|string|false|none||首条消息内容|

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
  "cause": {
    "detailMessage": "string",
    "cause": {
      "detailMessage": "string",
      "cause": {
        "detailMessage": "string",
        "cause": {
          "detailMessage": null,
          "cause": null,
          "stackTrace": null,
          "suppressedExceptions": null
        },
        "stackTrace": [
          {}
        ],
        "suppressedExceptions": [
          {}
        ]
      },
      "stackTrace": [
        {
          "classLoaderName": "string",
          "moduleName": "string",
          "moduleVersion": "string",
          "declaringClass": "string",
          "methodName": "string",
          "fileName": "string",
          "lineNumber": 0,
          "format": -127
        }
      ],
      "suppressedExceptions": [
        {
          "detailMessage": "string",
          "cause": {},
          "stackTrace": [
            null
          ],
          "suppressedExceptions": [
            null
          ]
        }
      ]
    },
    "stackTrace": [
      {
        "classLoaderName": "string",
        "moduleName": "string",
        "moduleVersion": "string",
        "declaringClass": "string",
        "methodName": "string",
        "fileName": "string",
        "lineNumber": 0,
        "format": -127
      }
    ],
    "suppressedExceptions": [
      {
        "detailMessage": "string",
        "cause": {
          "detailMessage": "string",
          "cause": {},
          "stackTrace": [
            null
          ],
          "suppressedExceptions": [
            null
          ]
        },
        "stackTrace": [
          {
            "classLoaderName": null,
            "moduleName": null,
            "moduleVersion": null,
            "declaringClass": null,
            "methodName": null,
            "fileName": null,
            "lineNumber": null,
            "format": null
          }
        ],
        "suppressedExceptions": [
          {
            "detailMessage": null,
            "cause": null,
            "stackTrace": null,
            "suppressedExceptions": null
          }
        ]
      }
    ]
  },
  "stackTrace": [
    {
      "classLoaderName": "string",
      "moduleName": "string",
      "moduleVersion": "string",
      "declaringClass": "string",
      "methodName": "string",
      "fileName": "string",
      "lineNumber": 0,
      "format": -127
    }
  ],
  "suppressedExceptions": [
    {
      "detailMessage": "string",
      "cause": {
        "detailMessage": "string",
        "cause": {
          "detailMessage": "string",
          "cause": {},
          "stackTrace": [
            null
          ],
          "suppressedExceptions": [
            null
          ]
        },
        "stackTrace": [
          {
            "classLoaderName": null,
            "moduleName": null,
            "moduleVersion": null,
            "declaringClass": null,
            "methodName": null,
            "fileName": null,
            "lineNumber": null,
            "format": null
          }
        ],
        "suppressedExceptions": [
          {
            "detailMessage": null,
            "cause": null,
            "stackTrace": null,
            "suppressedExceptions": null
          }
        ]
      },
      "stackTrace": [
        {
          "classLoaderName": "string",
          "moduleName": "string",
          "moduleVersion": "string",
          "declaringClass": "string",
          "methodName": "string",
          "fileName": "string",
          "lineNumber": 0,
          "format": -127
        }
      ],
      "suppressedExceptions": [
        {
          "detailMessage": "string",
          "cause": {
            "detailMessage": null,
            "cause": null,
            "stackTrace": null,
            "suppressedExceptions": null
          },
          "stackTrace": [
            {}
          ],
          "suppressedExceptions": [
            {}
          ]
        }
      ]
    }
  ]
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
  "format": -127
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
    "cause": {
      "detailMessage": "string",
      "cause": {
        "detailMessage": "string",
        "cause": {
          "detailMessage": null,
          "cause": null,
          "stackTrace": null,
          "suppressedExceptions": null
        },
        "stackTrace": [
          {}
        ],
        "suppressedExceptions": [
          {}
        ]
      },
      "stackTrace": [
        {
          "classLoaderName": "string",
          "moduleName": "string",
          "moduleVersion": "string",
          "declaringClass": "string",
          "methodName": "string",
          "fileName": "string",
          "lineNumber": 0,
          "format": -127
        }
      ],
      "suppressedExceptions": [
        {
          "detailMessage": "string",
          "cause": {},
          "stackTrace": [
            null
          ],
          "suppressedExceptions": [
            null
          ]
        }
      ]
    },
    "stackTrace": [
      {
        "classLoaderName": "string",
        "moduleName": "string",
        "moduleVersion": "string",
        "declaringClass": "string",
        "methodName": "string",
        "fileName": "string",
        "lineNumber": 0,
        "format": -127
      }
    ],
    "suppressedExceptions": [
      {
        "detailMessage": "string",
        "cause": {
          "detailMessage": "string",
          "cause": {},
          "stackTrace": [
            null
          ],
          "suppressedExceptions": [
            null
          ]
        },
        "stackTrace": [
          {
            "classLoaderName": null,
            "moduleName": null,
            "moduleVersion": null,
            "declaringClass": null,
            "methodName": null,
            "fileName": null,
            "lineNumber": null,
            "format": null
          }
        ],
        "suppressedExceptions": [
          {
            "detailMessage": null,
            "cause": null,
            "stackTrace": null,
            "suppressedExceptions": null
          }
        ]
      }
    ]
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

<h2 id="tocS_AgentConversationRespDTO">AgentConversationRespDTO</h2>

<a id="schemaagentconversationrespdto"></a>
<a id="schema_AgentConversationRespDTO"></a>
<a id="tocSagentconversationrespdto"></a>
<a id="tocsagentconversationrespdto"></a>

```json
{
  "sessionId": "string",
  "agentId": 0,
  "agentName": "string",
  "conversationTitle": "string",
  "messageCount": 0,
  "totalTokens": 0,
  "status": 0,
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|sessionId|string|false|none||会话ID|
|agentId|integer(int64)|false|none||智能体ID|
|agentName|string|false|none||智能体名称|
|conversationTitle|string|false|none||会话标题|
|messageCount|integer|false|none||消息总数|
|totalTokens|integer|false|none||总Token消耗|
|status|integer|false|none||会话状态：1-进行中，2-已结束|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||最后更新时间|

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

<h2 id="tocS_IPageAgentConversationRespDTO">IPageAgentConversationRespDTO</h2>

<a id="schemaipageagentconversationrespdto"></a>
<a id="schema_IPageAgentConversationRespDTO"></a>
<a id="tocSipageagentconversationrespdto"></a>
<a id="tocsipageagentconversationrespdto"></a>

```json
{
  "records": [
    {
      "sessionId": "string",
      "agentId": 0,
      "agentName": "string",
      "conversationTitle": "string",
      "messageCount": 0,
      "totalTokens": 0,
      "status": 0,
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
|records|[[AgentConversationRespDTO](#schemaagentconversationrespdto)]|false|none||none|
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

<h2 id="tocS_ResultIPageAgentConversationRespDTO">ResultIPageAgentConversationRespDTO</h2>

<a id="schemaresultipageagentconversationrespdto"></a>
<a id="schema_ResultIPageAgentConversationRespDTO"></a>
<a id="tocSresultipageagentconversationrespdto"></a>
<a id="tocsresultipageagentconversationrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "records": [
      {
        "sessionId": "string",
        "agentId": 0,
        "agentName": "string",
        "conversationTitle": "string",
        "messageCount": 0,
        "totalTokens": 0,
        "status": 0,
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
|data|[IPageAgentConversationRespDTO](#schemaipageagentconversationrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|
|success|boolean|false|none||none|

<h2 id="tocS_AgentMessageHistoryRespDTO">AgentMessageHistoryRespDTO</h2>

<a id="schemaagentmessagehistoryrespdto"></a>
<a id="schema_AgentMessageHistoryRespDTO"></a>
<a id="tocSagentmessagehistoryrespdto"></a>
<a id="tocsagentmessagehistoryrespdto"></a>

```json
{
  "id": "string",
  "sessionId": "string",
  "messageType": 0,
  "messageContent": "string",
  "messageSeq": 0,
  "tokenCount": 0,
  "responseTime": 0,
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
|createTime|string|false|none||创建时间|

<h2 id="tocS_ResultListAgentMessageHistoryRespDTO">ResultListAgentMessageHistoryRespDTO</h2>

<a id="schemaresultlistagentmessagehistoryrespdto"></a>
<a id="schema_ResultListAgentMessageHistoryRespDTO"></a>
<a id="tocSresultlistagentmessagehistoryrespdto"></a>
<a id="tocsresultlistagentmessagehistoryrespdto"></a>

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
|data|[[AgentMessageHistoryRespDTO](#schemaagentmessagehistoryrespdto)]|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_IPageAgentMessageHistoryRespDTO">IPageAgentMessageHistoryRespDTO</h2>

<a id="schemaipageagentmessagehistoryrespdto"></a>
<a id="schema_IPageAgentMessageHistoryRespDTO"></a>
<a id="tocSipageagentmessagehistoryrespdto"></a>
<a id="tocsipageagentmessagehistoryrespdto"></a>

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
|records|[[AgentMessageHistoryRespDTO](#schemaagentmessagehistoryrespdto)]|false|none||none|
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

<h2 id="tocS_ResultIPageAgentMessageHistoryRespDTO">ResultIPageAgentMessageHistoryRespDTO</h2>

<a id="schemaresultipageagentmessagehistoryrespdto"></a>
<a id="schema_ResultIPageAgentMessageHistoryRespDTO"></a>
<a id="tocSresultipageagentmessagehistoryrespdto"></a>
<a id="tocsresultipageagentmessagehistoryrespdto"></a>

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
|data|[IPageAgentMessageHistoryRespDTO](#schemaipageagentmessagehistoryrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|
|success|boolean|false|none||none|

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

<h2 id="tocS_InterviewAnswerRespDTO">InterviewAnswerRespDTO</h2>

<a id="schemainterviewanswerrespdto"></a>
<a id="schema_InterviewAnswerRespDTO"></a>
<a id="tocSinterviewanswerrespdto"></a>
<a id="tocsinterviewanswerrespdto"></a>

```json
{
  "questionNumber": "string",
  "questionContent": "string",
  "score": 0,
  "totalScore": 0,
  "isSuccess": true,
  "errorMessage": "string",
  "feedback": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|questionNumber|string|false|none||题号|
|questionContent|string|false|none||题目内容|
|score|integer|false|none||本次得分|
|totalScore|integer|false|none||累计总分|
|isSuccess|boolean|false|none||是否成功|
|errorMessage|string|false|none||错误信息|
|feedback|string|false|none||AI评价内容（可选）|

<h2 id="tocS_ResultInterviewAnswerRespDTO">ResultInterviewAnswerRespDTO</h2>

<a id="schemaresultinterviewanswerrespdto"></a>
<a id="schema_ResultInterviewAnswerRespDTO"></a>
<a id="tocSresultinterviewanswerrespdto"></a>
<a id="tocsresultinterviewanswerrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "questionNumber": "string",
    "questionContent": "string",
    "score": 0,
    "totalScore": 0,
    "isSuccess": true,
    "errorMessage": "string",
    "feedback": "string"
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[InterviewAnswerRespDTO](#schemainterviewanswerrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_InterviewAnswerReqDTO">InterviewAnswerReqDTO</h2>

<a id="schemainterviewanswerreqdto"></a>
<a id="schema_InterviewAnswerReqDTO"></a>
<a id="tocSinterviewanswerreqdto"></a>
<a id="tocsinterviewanswerreqdto"></a>

```json
{
  "questionNumber": "string",
  "answerContent": "string",
  "sessionId": "string",
  "agentId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|questionNumber|string|false|none||题号|
|answerContent|string|false|none||用户回答内容|
|sessionId|string|false|none||会话ID（可选，用于关联面试会话）|
|agentId|integer(int64)|false|none||Agent ID（可选，用于指定评分的Agent）|

<h2 id="tocS_ResultMapString">ResultMapString</h2>

<a id="schemaresultmapstring"></a>
<a id="schema_ResultMapString"></a>
<a id="tocSresultmapstring"></a>
<a id="tocsresultmapstring"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "key": "string"
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[MapString](#schemamapstring)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_ResultInteger">ResultInteger</h2>

<a id="schemaresultinteger"></a>
<a id="schema_ResultInteger"></a>
<a id="tocSresultinteger"></a>
<a id="tocsresultinteger"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": 0,
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|integer|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_RadarChartDTO">RadarChartDTO</h2>

<a id="schemaradarchartdto"></a>
<a id="schema_RadarChartDTO"></a>
<a id="tocSradarchartdto"></a>
<a id="tocsradarchartdto"></a>

```json
{
  "resumeScore": 0,
  "interviewPerformance": 0,
  "demeanorEvaluation": 0,
  "potentialIndex": 0,
  "professionalSkills": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|resumeScore|integer|false|none||简历评估得分 (0-1)|
|interviewPerformance|integer|false|none||面试表现得分 (0-1)|
|demeanorEvaluation|integer|false|none||神态管理评分 (0-1)|
|potentialIndex|integer|false|none||用户潜力指数 (0-1)|
|professionalSkills|integer|false|none||专业技能评分 (0-1)|

<h2 id="tocS_ResultRadarChartDTO">ResultRadarChartDTO</h2>

<a id="schemaresultradarchartdto"></a>
<a id="schema_ResultRadarChartDTO"></a>
<a id="tocSresultradarchartdto"></a>
<a id="tocsresultradarchartdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "resumeScore": 0,
    "interviewPerformance": 0,
    "demeanorEvaluation": 0,
    "potentialIndex": 0,
    "professionalSkills": 0
  },
  "requestId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|string|false|none||返回码|
|message|string|false|none||返回消息|
|data|[RadarChartDTO](#schemaradarchartdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_DemeanorEvaluationReqDTO">DemeanorEvaluationReqDTO</h2>

<a id="schemademeanorevaluationreqdto"></a>
<a id="schema_DemeanorEvaluationReqDTO"></a>
<a id="tocSdemeanorevaluationreqdto"></a>
<a id="tocsdemeanorevaluationreqdto"></a>

```json
{
  "userName": "string",
  "agentId": 0,
  "sessionId": "string",
  "userPhoto": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userName|string|false|none||用户名|
|agentId|integer(int64)|false|none||智能体ID|
|sessionId|string|false|none||会话ID|
|userPhoto|string|false|none||用户照片|

<h2 id="tocS_UserMessageReqDTO">UserMessageReqDTO</h2>

<a id="schemausermessagereqdto"></a>
<a id="schema_UserMessageReqDTO"></a>
<a id="tocSusermessagereqdto"></a>
<a id="tocsusermessagereqdto"></a>

```json
{
  "userName": "string",
  "agentId": 0,
  "inputMessage": "string",
  "messageSeq": 0,
  "sessionId": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userName|string|false|none||用户名|
|agentId|integer(int64)|false|none||AgentID|
|inputMessage|string|false|none||用户输入信息|
|messageSeq|integer|false|none||消息序号|
|sessionId|string|false|none||会话ID（|

<h2 id="tocS_InterviewQuestionRespDTO">InterviewQuestionRespDTO</h2>

<a id="schemainterviewquestionrespdto"></a>
<a id="schema_InterviewQuestionRespDTO"></a>
<a id="tocSinterviewquestionrespdto"></a>
<a id="tocsinterviewquestionrespdto"></a>

```json
{
  "id": "string",
  "sessionId": "string",
  "userName": "string",
  "agentId": 0,
  "questions": {
    "key": "string"
  },
  "suggestions": {
    "key": "string"
  },
  "interviewType": "string",
  "resumeFileUrl": "string",
  "responseTime": 0,
  "tokenCount": 0,
  "resumeScore": 0,
  "questionCount": 0,
  "suggestionCount": 0,
  "isSuccess": 1,
  "errorMessage": "string",
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|string|false|none||ID|
|sessionId|string|false|none||会话ID|
|userName|string|false|none||用户名|
|agentId|integer(int64)|false|none||智能体ID|
|questions|[MapString](#schemamapstring)|false|none||面试题Map（题号 -> 题目内容）|
|suggestions|[MapString](#schemamapstring)|false|none||建议Map（编号 -> 建议内容）|
|interviewType|string|false|none||面试类型（如：backend、frontend等）|
|resumeFileUrl|string|false|none||简历文件URL|
|responseTime|integer|false|none||响应时间(毫秒)|
|tokenCount|integer|false|none||Token消耗数量|
|resumeScore|integer|false|none||简历评分（0-100）|
|questionCount|integer|false|none||生成的面试题数量|
|suggestionCount|integer|false|none||建议数量|
|isSuccess|integer|false|none||是否成功|
|errorMessage|string|false|none||错误信息（如果处理失败）|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||更新时间|

<h2 id="tocS_ResultInterviewQuestionRespDTO">ResultInterviewQuestionRespDTO</h2>

<a id="schemaresultinterviewquestionrespdto"></a>
<a id="schema_ResultInterviewQuestionRespDTO"></a>
<a id="tocSresultinterviewquestionrespdto"></a>
<a id="tocsresultinterviewquestionrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "id": "string",
    "sessionId": "string",
    "userName": "string",
    "agentId": 0,
    "questions": {
      "key": "string"
    },
    "suggestions": {
      "key": "string"
    },
    "interviewType": "string",
    "resumeFileUrl": "string",
    "responseTime": 0,
    "tokenCount": 0,
    "resumeScore": 0,
    "questionCount": 0,
    "suggestionCount": 0,
    "isSuccess": 1,
    "errorMessage": "string",
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
|data|[InterviewQuestionRespDTO](#schemainterviewquestionrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

<h2 id="tocS_InterviewRecordSaveReqDTO">InterviewRecordSaveReqDTO</h2>

<a id="schemainterviewrecordsavereqdto"></a>
<a id="schema_InterviewRecordSaveReqDTO"></a>
<a id="tocSinterviewrecordsavereqdto"></a>
<a id="tocsinterviewrecordsavereqdto"></a>

```json
{
  "sessionId": "string",
  "interviewScore": 0,
  "interviewSuggestions": "string",
  "interviewDirection": "string",
  "username": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|sessionId|string|false|none||会话ID|
|interviewScore|integer|false|none||面试得分|
|interviewSuggestions|string|false|none||面试建议|
|interviewDirection|string|false|none||面试方向|
|username|string|false|none||用户名|

<h2 id="tocS_InterviewRecordRespDTO">InterviewRecordRespDTO</h2>

<a id="schemainterviewrecordrespdto"></a>
<a id="schema_InterviewRecordRespDTO"></a>
<a id="tocSinterviewrecordrespdto"></a>
<a id="tocsinterviewrecordrespdto"></a>

```json
{
  "id": 0,
  "userId": 0,
  "sessionId": "string",
  "interviewScore": 0,
  "interviewSuggestions": "string",
  "interviewSuggestionsMap": {
    "key": "string"
  },
  "interviewDirection": "string",
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||ID|
|userId|integer(int64)|false|none||用户ID|
|sessionId|string|false|none||会话ID|
|interviewScore|integer|false|none||面试得分|
|interviewSuggestions|string|false|none||面试建议（原始字符串格式）|
|interviewSuggestionsMap|[MapString](#schemamapstring)|false|none||面试建议（解析后的Map格式）<br />key为编号，value为建议内容|
|interviewDirection|string|false|none||面试方向|
|createTime|string|false|none||创建时间|
|updateTime|string|false|none||修改时间|

<h2 id="tocS_IPageInterviewRecordRespDTO">IPageInterviewRecordRespDTO</h2>

<a id="schemaipageinterviewrecordrespdto"></a>
<a id="schema_IPageInterviewRecordRespDTO"></a>
<a id="tocSipageinterviewrecordrespdto"></a>
<a id="tocsipageinterviewrecordrespdto"></a>

```json
{
  "records": [
    {
      "id": 0,
      "userId": 0,
      "sessionId": "string",
      "interviewScore": 0,
      "interviewSuggestions": "string",
      "interviewSuggestionsMap": {
        "key": "string"
      },
      "interviewDirection": "string",
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
|records|[[InterviewRecordRespDTO](#schemainterviewrecordrespdto)]|false|none||none|
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

<h2 id="tocS_ResultIPageInterviewRecordRespDTO">ResultIPageInterviewRecordRespDTO</h2>

<a id="schemaresultipageinterviewrecordrespdto"></a>
<a id="schema_ResultIPageInterviewRecordRespDTO"></a>
<a id="tocSresultipageinterviewrecordrespdto"></a>
<a id="tocsresultipageinterviewrecordrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "records": [
      {
        "id": 0,
        "userId": 0,
        "sessionId": "string",
        "interviewScore": 0,
        "interviewSuggestions": "string",
        "interviewSuggestionsMap": {
          "key": "string"
        },
        "interviewDirection": "string",
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
|data|[IPageInterviewRecordRespDTO](#schemaipageinterviewrecordrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|
|success|boolean|false|none||none|

<h2 id="tocS_ResultInterviewRecordRespDTO">ResultInterviewRecordRespDTO</h2>

<a id="schemaresultinterviewrecordrespdto"></a>
<a id="schema_ResultInterviewRecordRespDTO"></a>
<a id="tocSresultinterviewrecordrespdto"></a>
<a id="tocsresultinterviewrecordrespdto"></a>

```json
{
  "code": "string",
  "message": "string",
  "data": {
    "id": 0,
    "userId": 0,
    "sessionId": "string",
    "interviewScore": 0,
    "interviewSuggestions": "string",
    "interviewSuggestionsMap": {
      "key": "string"
    },
    "interviewDirection": "string",
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
|data|[InterviewRecordRespDTO](#schemainterviewrecordrespdto)|false|none||响应数据|
|requestId|string|false|none||请求ID|

