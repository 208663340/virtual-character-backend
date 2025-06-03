-- 优化后的智能体消息表结构，支持历史消息检索功能
-- 作者：AI助手
-- 创建时间：2024年

-- 方案一：会话表 + 消息表分离设计（推荐）

-- 1. 智能体会话表
CREATE TABLE `agent_conversation` (
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `session_id`         varchar(64) NOT NULL COMMENT '会话ID，UUID格式',
    `user_id`            bigint(20) NOT NULL COMMENT '用户ID',
    `agent_id`           bigint(20) NOT NULL COMMENT '智能体ID',
    `conversation_title` varchar(255) DEFAULT NULL COMMENT '会话标题，可从首条消息自动生成',
    `message_count`      int(11) DEFAULT 0 COMMENT '消息总数',
    `total_tokens`       int(11) DEFAULT 0 COMMENT '总Token消耗',
    `status`             tinyint(1) DEFAULT 1 COMMENT '会话状态：1-进行中，2-已结束，3-已删除',
    `create_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    `del_flag`           tinyint(1) DEFAULT 0 COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_session_id` (`session_id`),
    INDEX `idx_user_time` (`user_id`, `update_time` DESC),
    INDEX `idx_agent_time` (`agent_id`, `update_time` DESC),
    INDEX `idx_user_agent` (`user_id`, `agent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='智能体会话表';

-- 2. 智能体消息详情表（分表设计，支持按session_id分表）
CREATE TABLE `agent_message_0` (
    `id`              bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `session_id`      varchar(64) NOT NULL COMMENT '会话ID',
    `message_type`    tinyint(1) NOT NULL COMMENT '消息类型：1-用户消息，2-AI回复',
    `message_content` text NOT NULL COMMENT '消息内容',
    `message_seq`     int(11) NOT NULL COMMENT '消息序号，同一会话内递增',
    `parent_msg_id`   bigint(20) DEFAULT NULL COMMENT '父消息ID，用于消息关联',
    `token_count`     int(11) DEFAULT NULL COMMENT 'Token消耗数量',
    `response_time`   int(11) DEFAULT NULL COMMENT '响应时间(毫秒)',
    `error_message`   varchar(500) DEFAULT NULL COMMENT '错误信息（如果处理失败）',
    `create_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `del_flag`        tinyint(1) DEFAULT 0 COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    INDEX `idx_session_seq` (`session_id`, `message_seq`),
    INDEX `idx_session_type` (`session_id`, `message_type`),
    INDEX `idx_create_time` (`create_time` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='智能体消息详情表_0';

-- 创建其他分表（1-15）
CREATE TABLE `agent_message_1` LIKE `agent_message_0`;
CREATE TABLE `agent_message_2` LIKE `agent_message_0`;
CREATE TABLE `agent_message_3` LIKE `agent_message_0`;
CREATE TABLE `agent_message_4` LIKE `agent_message_0`;
CREATE TABLE `agent_message_5` LIKE `agent_message_0`;
CREATE TABLE `agent_message_6` LIKE `agent_message_0`;
CREATE TABLE `agent_message_7` LIKE `agent_message_0`;
CREATE TABLE `agent_message_8` LIKE `agent_message_0`;
CREATE TABLE `agent_message_9` LIKE `agent_message_0`;
CREATE TABLE `agent_message_10` LIKE `agent_message_0`;
CREATE TABLE `agent_message_11` LIKE `agent_message_0`;
CREATE TABLE `agent_message_12` LIKE `agent_message_0`;
CREATE TABLE `agent_message_13` LIKE `agent_message_0`;
CREATE TABLE `agent_message_14` LIKE `agent_message_0`;
CREATE TABLE `agent_message_15` LIKE `agent_message_0`;

-- 方案二：单表优化设计（如果不想分表可以使用这个）

CREATE TABLE `agent_message_single` (
    `id`              bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `session_id`      varchar(64) NOT NULL COMMENT '会话ID，用于关联同一次对话',
    `user_id`         bigint(20) NOT NULL COMMENT '用户ID',
    `agent_id`        bigint(20) NOT NULL COMMENT '智能体ID',
    `message_type`    tinyint(1) NOT NULL COMMENT '消息类型：1-用户消息，2-AI回复',
    `message_content` text NOT NULL COMMENT '消息内容',
    `message_seq`     int(11) NOT NULL COMMENT '消息序号，同一会话内递增',
    `parent_msg_id`   bigint(20) DEFAULT NULL COMMENT '父消息ID，用于消息关联',
    `is_success`      tinyint(1) DEFAULT 1 COMMENT '是否成功处理',
    `token_count`     int(11) DEFAULT NULL COMMENT 'Token消耗数量',
    `response_time`   int(11) DEFAULT NULL COMMENT '响应时间(毫秒)',
    `error_message`   varchar(500) DEFAULT NULL COMMENT '错误信息',
    `create_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_flag`        tinyint(1) DEFAULT 0 COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    INDEX `idx_session_seq` (`session_id`, `message_seq`),
    INDEX `idx_user_time` (`user_id`, `create_time` DESC),
    INDEX `idx_agent_time` (`agent_id`, `create_time` DESC),
    INDEX `idx_user_session` (`user_id`, `session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='智能体消息记录表（单表版本）';


-- 历史消息检索相关的常用查询示例

-- 1. 查询用户的会话列表（按时间倒序）
/*
SELECT 
    c.session_id,
    c.conversation_title,
    c.message_count,
    c.update_time,
    ap.agent_name
FROM agent_conversation c
LEFT JOIN agent_properties ap ON c.agent_id = ap.id
WHERE c.user_id = ? AND c.del_flag = 0
ORDER BY c.update_time DESC
LIMIT 20;
*/

-- 2. 查询指定会话的完整消息记录
/*
SELECT 
    message_type,
    message_content,
    message_seq,
    token_count,
    response_time,
    create_time
FROM agent_message_0  -- 根据session_id选择对应分表
WHERE session_id = ? AND del_flag = 0
ORDER BY message_seq ASC;
*/

-- 3. 按关键词搜索消息内容
/*
SELECT 
    am.session_id,
    am.message_content,
    am.create_time,
    ac.conversation_title
FROM agent_message_0 am  -- 需要在所有分表中搜索
JOIN agent_conversation ac ON am.session_id = ac.session_id
WHERE am.message_content LIKE CONCAT('%', ?, '%') 
    AND am.del_flag = 0 
    AND ac.user_id = ?
ORDER BY am.create_time DESC
LIMIT 50;
*/

-- 4. 统计用户使用情况
/*
SELECT 
    COUNT(DISTINCT c.session_id) as conversation_count,
    SUM(c.message_count) as total_messages,
    SUM(c.total_tokens) as total_tokens,
    COUNT(DISTINCT c.agent_id) as agent_count
FROM agent_conversation c
WHERE c.user_id = ? 
    AND c.create_time >= DATE_SUB(NOW(), INTERVAL 30 DAY)
    AND c.del_flag = 0;
*/