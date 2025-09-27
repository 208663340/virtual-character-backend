-- AI角色表
CREATE TABLE `ai_character` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `ai_name` VARCHAR(100) NOT NULL COMMENT 'AI名称',
    `ai_avatar` VARCHAR(500) DEFAULT NULL COMMENT 'AI头像',
    `description` VARCHAR(500) DEFAULT NULL COMMENT 'AI角色描述',
    `ai_prompt` TEXT DEFAULT NULL COMMENT 'AI提示词',
    `voice_detail_id` BIGINT DEFAULT NULL COMMENT '音色详情ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_flag` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_ai_name` (`ai_name`, `del_flag`) COMMENT 'AI名称唯一索引',
    KEY `idx_create_time` (`create_time`) COMMENT '创建时间索引',
    KEY `idx_voice_detail_id` (`voice_detail_id`) COMMENT '音色详情ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI角色表';

-- 插入示例数据
INSERT INTO `ai_character` (`ai_name`, `ai_avatar`, `description`, `ai_prompt`, `voice_detail_id`) VALUES
('智能助手小智', 'https://example.com/avatar1.jpg', '专业的智能助手，能够为用户提供全方位的帮助和支持', '你是一个智能助手，能够帮助用户解答各种问题，提供专业的建议和帮助。', 1),
('编程导师小码', 'https://example.com/avatar2.jpg', '经验丰富的编程导师，专注于技术教学和问题解决', '你是一个编程导师，擅长各种编程语言和技术，能够帮助用户学习编程知识，解决技术问题。', 2),
('生活顾问小生', 'https://example.com/avatar3.jpg', '贴心的生活顾问，为用户提供实用的生活建议和指导', '你是一个生活顾问，能够为用户提供生活建议，帮助解决日常生活中的各种问题。', 3);