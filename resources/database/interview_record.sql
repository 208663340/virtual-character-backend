-- 面试记录表
CREATE TABLE `interview_record` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `session_id` varchar(64) NOT NULL COMMENT '会话ID',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='面试记录表';