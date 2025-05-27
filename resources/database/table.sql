CREATE TABLE `t_user_0`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716344307570487299 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_1`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1726253659068588035 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_10`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1726262175087058946 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_11`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716835884998893571 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_12`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716356833762906114 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_13`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716777589441347586 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_14`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716835562859589634 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_15`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1752265616481370114 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_2`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1726260205890691074 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_3`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716826815625977859 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_4`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716777824704053251 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_5`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716835362095034371 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_6`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716991700406161411 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_7`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1716834641844936706 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_8`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_9`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username`      varchar(256) DEFAULT NULL COMMENT '用户名',
    `password`      varchar(512) DEFAULT NULL COMMENT '密码',
    `real_name`     varchar(256) DEFAULT NULL COMMENT '真实姓名',
    `phone`         varchar(128) DEFAULT NULL COMMENT '手机号',
    `mail`          varchar(512) DEFAULT NULL COMMENT '邮箱',
    `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1726852231086505986 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_properties`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `agent_name`      varchar(256) DEFAULT NULL COMMENT '智能体名称',
    `api_secret`      varchar(256) DEFAULT NULL COMMENT '鉴权密钥',
    `api_key`      varchar(512) DEFAULT NULL COMMENT '鉴权key',
    `api_flow_id`     varchar(256) DEFAULT NULL COMMENT '工作流id',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '修改时间',
    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_0` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716344345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_1` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716354345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_2` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716364345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_3` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716374345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_4` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716384345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_5` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716394345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_6` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716404345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_7` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716414345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_8` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716424345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_9` (
                                   `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                   `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                   `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                   `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                   `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                   `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                   PRIMARY KEY (`id`),
                                   INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716434345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_10` (
                                    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                    `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                    `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                    `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                    `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                    `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                    PRIMARY KEY (`id`),
                                    INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716444345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_11` (
                                    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                    `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                    `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                    `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                    `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                    `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                    PRIMARY KEY (`id`),
                                    INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716454345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_12` (
                                    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                    `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                    `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                    `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                    `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                    `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                    PRIMARY KEY (`id`),
                                    INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716464345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_13` (
                                    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                    `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                    `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                    `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                    `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                    `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                    PRIMARY KEY (`id`),
                                    INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716474345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_14` (
                                    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                    `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                    `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                    `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                    `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                    `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                    PRIMARY KEY (`id`),
                                    INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716484345346543 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `agent_message_15` (
                                    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                    `agent_id`      bigint(20) DEFAULT NULL COMMENT '智能体id',
                                    `chat_message`  varchar(256) DEFAULT NULL COMMENT '智能体消息',
                                    `user_id`       bigint(20) DEFAULT NULL COMMENT '用户id',
                                    `user_message`  varchar(256) DEFAULT NULL COMMENT '用户消息',
                                    `is_success`    tinyint(1) DEFAULT 1 COMMENT '是否完成对话',
                                    `create_time`   datetime DEFAULT NULL COMMENT '创建时间',
                                    `del_flag`      tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
                                    PRIMARY KEY (`id`),
                                    INDEX idx_user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1716494345346543 DEFAULT CHARSET=utf8mb4;