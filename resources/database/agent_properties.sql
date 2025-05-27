CREATE TABLE `agent_properties` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `agent_name` varchar(256) DEFAULT NULL COMMENT '智能体名称',
    `api_secret` varchar(512) DEFAULT NULL COMMENT '鉴权密钥',
    `api_key` varchar(512) DEFAULT NULL COMMENT '鉴权key',
    `api_flow_id` varchar(256) DEFAULT NULL COMMENT '工作流id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_agent_name` (`agent_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;