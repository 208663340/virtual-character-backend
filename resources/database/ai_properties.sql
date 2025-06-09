-- AI配置表
CREATE TABLE `ai_properties`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `ai_name`       varchar(256) NOT NULL COMMENT 'AI名称',
    `ai_type`       varchar(64) NOT NULL COMMENT 'AI类型：spark、openai、claude等',
    `api_key`       varchar(512) NOT NULL COMMENT 'API密钥',
    `api_secret`    varchar(512) DEFAULT NULL COMMENT 'API密钥（部分AI需要）',
    `api_url`       varchar(512) DEFAULT NULL COMMENT 'API地址',
    `model_name`    varchar(256) DEFAULT NULL COMMENT '模型名称',
    `max_tokens`    int(11) DEFAULT 4096 COMMENT '最大token数',
    `temperature`   decimal(3,2) DEFAULT 0.7 COMMENT '温度参数',
    `system_prompt` text DEFAULT NULL COMMENT '系统提示词',
    `is_enabled`    tinyint(1) DEFAULT 1 COMMENT '是否启用 0：禁用 1：启用',
    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT 0 COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    KEY `idx_ai_type` (`ai_type`) USING BTREE,
    KEY `idx_is_enabled` (`is_enabled`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI配置表';

-- 插入示例数据
INSERT INTO `ai_properties` (`ai_name`, `ai_type`, `api_key`, `api_secret`, `api_url`, `model_name`, `max_tokens`, `temperature`, `system_prompt`, `is_enabled`, `create_time`, `update_time`, `del_flag`) VALUES
('星火4.0Ultra', 'Spark4.0 Ultra', 'KuySrCFDAhsxEIWMKDMP:BviVMqNZCjNfyPYvBAPc', 'your_api_secret_here', 'https://spark-api-open.xf-yun.com/v1/chat/completions', '4.0Ultra', 8192, 0.1, '你是一个智能助手，请根据用户的问题提供准确、有用的回答。', 1, NOW(), NOW(), 0);
