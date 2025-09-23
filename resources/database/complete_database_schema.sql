-- 讯智Agent项目完整数据库建表SQL
-- 创建时间: 2025-01-15
-- 数据库: mainshi_agent

-- 创建数据库和Schema
CREATE DATABASE IF NOT EXISTS mainshi_agent DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE mainshi_agent;

-- 1. 管理员权限表
CREATE TABLE IF NOT EXISTS `admin_permission` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `username` varchar(256) NOT NULL COMMENT '用户名',
    `is_admin` tinyint(1) DEFAULT '0' COMMENT '是否管理员 0：普通用户 1：管理员',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_user_id` (`user_id`) USING BTREE,
    KEY `idx_username` (`username`) USING BTREE,
    KEY `idx_is_admin` (`is_admin`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员权限表';

-- 2. 智能体配置表
CREATE TABLE IF NOT EXISTS `agent_properties` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `agent_name` varchar(256) DEFAULT NULL COMMENT '智能体名称',
    `api_secret` varchar(512) DEFAULT NULL COMMENT '鉴权密钥',
    `api_key` varchar(512) DEFAULT NULL COMMENT '鉴权key',
    `api_flow_id` varchar(256) DEFAULT NULL COMMENT '工作流id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    KEY `idx_agent_name` (`agent_name`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='智能体配置表';

-- 3. 智能体标签表
CREATE TABLE IF NOT EXISTS `agent_tag` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `tag_name` varchar(128) NOT NULL COMMENT '标签名称',
    `agent_id` bigint NOT NULL COMMENT '关联的智能体ID',
    `description` varchar(512) DEFAULT NULL COMMENT '标签描述',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    KEY `idx_agent_id` (`agent_id`) USING BTREE,
    KEY `idx_tag_name` (`tag_name`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='智能体标签表';

-- 4. AI配置表
CREATE TABLE IF NOT EXISTS `ai_properties` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `ai_name` varchar(256) NOT NULL COMMENT 'AI名称',
    `ai_type` varchar(64) NOT NULL COMMENT 'AI类型：spark、openai、claude等',
    `api_key` varchar(512) NOT NULL COMMENT 'API密钥',
    `api_secret` varchar(512) DEFAULT NULL COMMENT 'API密钥（部分AI需要）',
    `api_url` varchar(512) DEFAULT NULL COMMENT 'API地址',
    `model_name` varchar(256) DEFAULT NULL COMMENT '模型名称',
    `max_tokens` int DEFAULT '4096' COMMENT '最大token数',
    `temperature` decimal(3,2) DEFAULT '0.70' COMMENT '温度参数',
    `system_prompt` text COMMENT '系统提示词',
    `is_enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用 0：禁用 1：启用',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    KEY `idx_ai_name` (`ai_name`) USING BTREE,
    KEY `idx_ai_type` (`ai_type`) USING BTREE,
    KEY `idx_is_enabled` (`is_enabled`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='AI配置表';

-- 5. 面试记录表
CREATE TABLE IF NOT EXISTS `interview_record` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `session_id` varchar(128) NOT NULL COMMENT '会话ID',
    `interview_score` int DEFAULT NULL COMMENT '面试得分',
    `interview_suggestions` text COMMENT '面试建议',
    `interview_direction` varchar(128) DEFAULT NULL COMMENT '面试方向',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`) USING BTREE,
    KEY `idx_session_id` (`session_id`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='面试记录表';

-- 6. 用户表（单表结构）
CREATE TABLE IF NOT EXISTS `t_user` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username` varchar(256) DEFAULT NULL COMMENT '用户名',
    `password` varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE,
    KEY `idx_phone` (`phone`) USING BTREE,
    KEY `idx_mail` (`mail`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- MongoDB集合说明（这些集合需要在MongoDB中创建）
-- 注意：以下是MongoDB集合的结构说明，不是SQL语句
/*
MongoDB集合结构说明：

1. ai_message 集合 - AI消息表
{
  "_id": ObjectId,
  "sessionId": String (索引),
  "messageType": Number, // 1-用户消息，2-AI回复
  "messageContent": String,
  "messageSeq": Number,
  "parentMsgId": Number,
  "tokenCount": Number,
  "responseTime": Number,
  "errorMessage": String,
  "createTime": Date,
  "updateTime": Date,
  "delFlag": Number
}

2. ai_conversation 集合 - AI会话表
{
  "_id": ObjectId,
  "sessionId": String (唯一索引),
  "username": String (索引),
  "aiId": Number (索引),
  "title": String,
  "status": Number, // 1-进行中，2-已结束
  "messageCount": Number,
  "lastMessageTime": Date,
  "createTime": Date,
  "updateTime": Date,
  "delFlag": Number
}

3. agent_message 集合 - 智能体消息表
{
  "_id": ObjectId,
  "sessionId": String (索引),
  "messageType": Number, // 1-用户消息，2-AI回复
  "messageContent": String,
  "messageSeq": Number,
  "parentMsgId": Number,
  "tokenCount": Number,
  "responseTime": Number,
  "errorMessage": String,
  "createTime": Date,
  "updateTime": Date,
  "delFlag": Number
}

4. agent_conversation 集合 - 智能体会话表
{
  "_id": ObjectId,
  "sessionId": String (唯一索引),
  "userId": Number (索引),
  "agentId": Number (索引),
  "conversationTitle": String,
  "messageCount": Number,
  "totalTokens": Number,
  "status": Number, // 1-进行中，2-已结束，3-已删除
  "createTime": Date,
  "updateTime": Date,
  "delFlag": Number
}

5. interview_question 集合 - 面试题存储表
{
  "_id": ObjectId,
  "sessionId": String (索引),
  "userName": String,
  "agentId": Number,
  "questions": Array, // 面试题列表（向后兼容）
  "questionsJson": String, // 面试题JSON格式存储
  "suggestions": Array, // 建议列表（向后兼容）
  "suggestionsJson": String, // 建议JSON格式存储
  "resumeScore": Number,
  "interviewType": String,
  "resumeFileUrl": String,
  "rawResponseData": String,
  "responseTime": Number,
  "tokenCount": Number,
  "createTime": Date,
  "updateTime": Date,
  "delFlag": Number
}
*/

-- 建表完成提示
SELECT 'Database schema creation completed successfully!' as message;