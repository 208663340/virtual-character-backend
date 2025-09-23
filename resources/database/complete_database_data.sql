-- 讯智Agent项目完整数据库初始化数据SQL
-- 创建时间: 2025-01-15
-- 数据库: mainshi_agent

USE mainshi_agent;

-- 1. 管理员权限表初始化数据
INSERT INTO `admin_permission` (`id`, `user_id`, `username`, `is_admin`, `create_time`, `update_time`, `del_flag`) VALUES
(1, 1951952970161119233, 'admin', 1, '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(2, 1951952970161119234, 'testuser', 0, '2024-01-02 10:00:00', '2024-01-02 10:00:00', 0);

-- 2. AI配置表初始化数据
INSERT INTO `ai_properties` (`id`, `ai_name`, `ai_type`, `api_key`, `api_secret`, `api_url`, `model_name`, `max_tokens`, `temperature`, `system_prompt`, `is_enabled`, `create_time`, `update_time`, `del_flag`) VALUES
(1, '星火4.0Ultra', 'generalv3.5', 'KuySrCFDAhsxEIWMKDMP:BviVMqNZCjNfyPYvBAPc', 'your_api_secret_here', 'https://spark-api-open.xf-yun.com/v1/chat/completions', '4.0Ultra', 8192, 0.10, '你是一个智能助手，请根据用户的问题提供准确、有用的回答。', 1, '2025-06-08 13:19:01', '2025-06-08 13:19:01', 0),
(2, 'OpenAI GPT-4', 'openai', 'sk-your-openai-api-key-here', NULL, 'https://api.openai.com/v1/chat/completions', 'gpt-4', 4096, 0.70, '你是一个有用的AI助手，请提供准确和有帮助的回答。', 1, '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(3, 'Claude-3', 'claude', 'sk-ant-your-claude-api-key-here', NULL, 'https://api.anthropic.com/v1/messages', 'claude-3-sonnet-20240229', 4096, 0.70, '你是Claude，一个由Anthropic创建的AI助手。', 0, '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0);

-- 3. 智能体配置表初始化数据
INSERT INTO `agent_properties` (`id`, `agent_name`, `api_secret`, `api_key`, `api_flow_id`, `create_time`, `update_time`, `del_flag`) VALUES
(1, 'Java面试官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7335896457532809218', '2025-05-27 14:28:02', '2025-05-27 14:28:02', 0),
(2, 'Python面试官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7335896457532809218', '2025-05-27 14:28:11', '2025-05-27 14:28:11', 0),
(3, '前端面试官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7335896457532809218', '2025-05-27 14:28:14', '2025-05-27 14:28:14', 0),
(4, '算法面试官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7335896457532809218', '2025-05-27 14:28:16', '2025-05-27 14:28:16', 0),
(5, '数据库面试官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7335896457532809218', '2025-05-27 14:28:19', '2025-05-27 14:28:19', 0),
(6, '系统设计面试官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7335896457532809218', '2025-06-04 14:23:18', '2025-06-04 14:23:18', 0),
(7, '项目经验面试官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7335896457532809218', '2025-07-14 13:24:03', '2025-07-14 13:24:03', 0),
(8, '面试抽题官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7349266146860527618', '2025-07-14 13:24:03', '2025-07-14 13:24:03', 0),
(9, '神态分析官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7350343355962298370', '2025-07-15 13:46:36', '2025-07-15 13:46:36', 0),
(10, '技术深度面试官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7350361742072750080', '2025-07-15 15:51:27', '2025-07-15 15:51:27', 0),
(11, '面试题检察官', 'OGZkZGQ5ZDY0Yzc4MTllZWI3ZmU2MDU4', 'e8565c438f59b301616e0498a86ad95d', '7350361742072750080', '2025-07-15 15:51:27', '2025-07-15 15:51:27', 0);

-- 4. 智能体标签表初始化数据
INSERT INTO `agent_tag` (`id`, `tag_name`, `agent_id`, `description`, `create_time`, `update_time`, `del_flag`) VALUES
(1, 'Java', 1, 'Java技术栈相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(2, 'Spring', 1, 'Spring框架相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(3, 'Python', 2, 'Python技术栈相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(4, 'Django', 2, 'Django框架相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(5, 'JavaScript', 3, 'JavaScript相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(6, 'React', 3, 'React框架相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(7, 'Vue', 3, 'Vue框架相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(8, '算法', 4, '算法与数据结构面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(9, '数据结构', 4, '数据结构相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(10, 'MySQL', 5, 'MySQL数据库面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(11, 'Redis', 5, 'Redis缓存相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(12, '系统设计', 6, '系统架构设计面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(13, '微服务', 6, '微服务架构面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(14, '项目经验', 7, '项目经验相关面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(15, '团队协作', 7, '团队协作能力面试', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0);

-- 5. 面试记录表初始化数据
INSERT INTO `interview_record` (`id`, `user_id`, `session_id`, `interview_score`, `interview_suggestions`, `interview_direction`, `create_time`, `update_time`, `del_flag`) VALUES
(1, 1951952970161119233, 'session_001_java_backend', 85, '代码逻辑清晰，但需要加强异常处理; 对Spring框架理解较好，建议深入学习微服务架构; 数据库设计合理，可以优化查询性能', 'Java后端开发', '2024-01-15 10:30:00', '2024-01-15 10:30:00', 0),
(2, 1951952970161119233, 'session_002_frontend_react', 78, 'React组件设计规范，但状态管理需要改进; JavaScript基础扎实，建议学习TypeScript; CSS样式编写良好，可以尝试使用CSS-in-JS方案', '前端React开发', '2024-01-16 14:20:00', '2024-01-16 14:20:00', 0),
(3, 1951952970161119233, 'session_003_python_ai', 92, 'Python语法熟练，算法思维清晰; 机器学习理论基础扎实，实践经验丰富; 数据处理能力强，建议关注最新的AI技术发展', 'Python AI开发', '2024-01-17 09:15:00', '2024-01-17 09:15:00', 0),
(4, 1951952970161119233, 'session_004_devops', 73, 'Docker容器化技术掌握良好; CI/CD流程理解正确，但实际操作经验不足; 云平台使用熟练，建议加强监控和日志管理知识', 'DevOps运维', '2024-01-18 16:45:00', '2024-01-18 16:45:00', 0),
(5, 1951952970161119234, 'session_005_algorithm', 88, '算法思维敏捷，能够快速分析问题; 数据结构掌握扎实，时间复杂度分析准确; 建议多练习动态规划和图算法', '算法工程师', '2024-01-19 11:30:00', '2024-01-19 11:30:00', 0),
(6, 1951952970161119234, 'session_006_system_design', 82, '系统架构思路清晰，考虑因素全面; 对分布式系统有一定理解，建议深入学习一致性协议; 数据库分片策略合理', '系统架构师', '2024-01-20 15:20:00', '2024-01-20 15:20:00', 0);

-- 6. 用户表初始化数据
INSERT INTO `t_user` (`id`, `username`, `password`, `real_name`, `phone`, `mail`, `deletion_time`, `create_time`, `update_time`, `del_flag`) VALUES
(1, 'admin', '$2a$10$YourHashedPasswordHere', '系统管理员', '13800138000', 'admin@example.com', NULL, NOW(), NOW(), 0),
(2, 'testuser1', '$2a$10$YourHashedPasswordHere', '测试用户1', '13800138001', 'test1@example.com', NULL, NOW(), NOW(), 0),
(3, 'testuser2', '$2a$10$YourHashedPasswordHere', '测试用户2', '13800138002', 'test2@example.com', NULL, NOW(), NOW(), 0),
(4, 'testuser3', '$2a$10$YourHashedPasswordHere', '测试用户3', '13800138003', 'test3@example.com', NULL, NOW(), NOW(), 0),
(5, 'testuser4', '$2a$10$YourHashedPasswordHere', '测试用户4', '13800138004', 'test4@example.com', NULL, NOW(), NOW(), 0);

-- MongoDB集合初始化数据说明
-- 注意：以下是MongoDB集合的初始化数据示例，需要在MongoDB中执行
/*
MongoDB初始化数据示例：

// 1. AI会话集合初始化数据
db.ai_conversation.insertMany([
  {
    "sessionId": "ai_session_001",
    "username": "admin",
    "aiId": 1,
    "title": "Java学习讨论",
    "status": 1,
    "messageCount": 5,
    "lastMessageTime": new Date("2024-01-15T10:30:00Z"),
    "createTime": new Date("2024-01-15T10:00:00Z"),
    "updateTime": new Date("2024-01-15T10:30:00Z"),
    "delFlag": 0
  },
  {
    "sessionId": "ai_session_002",
    "username": "testuser",
    "aiId": 1,
    "title": "Python开发咨询",
    "status": 2,
    "messageCount": 8,
    "lastMessageTime": new Date("2024-01-16T14:20:00Z"),
    "createTime": new Date("2024-01-16T14:00:00Z"),
    "updateTime": new Date("2024-01-16T14:20:00Z"),
    "delFlag": 0
  }
]);

// 2. AI消息集合初始化数据
db.ai_message.insertMany([
  {
    "sessionId": "ai_session_001",
    "messageType": 1,
    "messageContent": "你好，我想学习Java，请问从哪里开始？",
    "messageSeq": 1,
    "parentMsgId": null,
    "tokenCount": 15,
    "responseTime": null,
    "errorMessage": null,
    "createTime": new Date("2024-01-15T10:00:00Z"),
    "updateTime": new Date("2024-01-15T10:00:00Z"),
    "delFlag": 0
  },
  {
    "sessionId": "ai_session_001",
    "messageType": 2,
    "messageContent": "你好！学习Java是一个很好的选择。建议你从以下几个方面开始：\n1. 了解Java基础语法\n2. 学习面向对象编程概念\n3. 掌握常用的Java API\n4. 练习编写简单的程序",
    "messageSeq": 2,
    "parentMsgId": null,
    "tokenCount": 45,
    "responseTime": 1200,
    "errorMessage": null,
    "createTime": new Date("2024-01-15T10:00:30Z"),
    "updateTime": new Date("2024-01-15T10:00:30Z"),
    "delFlag": 0
  }
]);

// 3. 智能体会话集合初始化数据
db.agent_conversation.insertMany([
  {
    "sessionId": "agent_session_001",
    "userId": 1951952970161119233,
    "agentId": 1,
    "conversationTitle": "Java后端面试",
    "messageCount": 10,
    "totalTokens": 500,
    "status": 2,
    "createTime": new Date("2024-01-15T10:30:00Z"),
    "updateTime": new Date("2024-01-15T11:00:00Z"),
    "delFlag": 0
  },
  {
    "sessionId": "agent_session_002",
    "userId": 1951952970161119234,
    "agentId": 2,
    "conversationTitle": "Python开发面试",
    "messageCount": 8,
    "totalTokens": 400,
    "status": 1,
    "createTime": new Date("2024-01-16T14:00:00Z"),
    "updateTime": new Date("2024-01-16T14:30:00Z"),
    "delFlag": 0
  }
]);

// 4. 智能体消息集合初始化数据
db.agent_message.insertMany([
  {
    "sessionId": "agent_session_001",
    "messageType": 1,
    "messageContent": "请介绍一下Java中的多态性",
    "messageSeq": 1,
    "parentMsgId": null,
    "tokenCount": 12,
    "responseTime": null,
    "errorMessage": null,
    "createTime": new Date("2024-01-15T10:30:00Z"),
    "updateTime": new Date("2024-01-15T10:30:00Z"),
    "delFlag": 0
  },
  {
    "sessionId": "agent_session_001",
    "messageType": 2,
    "messageContent": "多态性是面向对象编程的重要特性之一。它允许不同类的对象对同一消息做出不同的响应。在Java中，多态性主要通过继承和接口实现。\n\n主要表现形式：\n1. 方法重写（Override）\n2. 方法重载（Overload）\n3. 接口实现\n\n多态的好处包括代码复用、扩展性强、维护性好等。",
    "messageSeq": 2,
    "parentMsgId": null,
    "tokenCount": 85,
    "responseTime": 1500,
    "errorMessage": null,
    "createTime": new Date("2024-01-15T10:30:30Z"),
    "updateTime": new Date("2024-01-15T10:30:30Z"),
    "delFlag": 0
  }
]);

// 5. 面试题集合初始化数据
db.interview_question.insertMany([
  {
    "sessionId": "interview_session_001",
    "userName": "admin",
    "agentId": 1,
    "questions": [
      "请介绍一下Java中的集合框架",
      "什么是Spring IoC容器？",
      "解释一下MySQL的事务隔离级别",
      "如何优化SQL查询性能？",
      "描述一下你做过的最复杂的项目"
    ],
    "questionsJson": "{\"1\": \"请介绍一下Java中的集合框架\", \"2\": \"什么是Spring IoC容器？\", \"3\": \"解释一下MySQL的事务隔离级别\", \"4\": \"如何优化SQL查询性能？\", \"5\": \"描述一下你做过的最复杂的项目\"}",
    "suggestions": [
      "加强对集合框架底层实现的理解",
      "深入学习Spring框架的核心概念",
      "多练习数据库优化技巧",
      "总结项目经验，提炼技术亮点"
    ],
    "suggestionsJson": "{\"1\": \"加强对集合框架底层实现的理解\", \"2\": \"深入学习Spring框架的核心概念\", \"3\": \"多练习数据库优化技巧\", \"4\": \"总结项目经验，提炼技术亮点\"}",
    "resumeScore": 85,
    "interviewType": "Java后端开发",
    "resumeFileUrl": "/uploads/resumes/admin_resume_20240115.pdf",
    "rawResponseData": "{\"score\": 85, \"questions\": [...], \"suggestions\": [...]}",
    "responseTime": 2500,
    "tokenCount": 150,
    "createTime": new Date("2024-01-15T10:30:00Z"),
    "updateTime": new Date("2024-01-15T10:30:00Z"),
    "delFlag": 0
  }
]);
*/
