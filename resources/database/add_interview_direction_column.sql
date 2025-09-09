-- 为面试记录表添加面试方向字段
ALTER TABLE `interview_record` ADD COLUMN `interview_direction` varchar(128) DEFAULT NULL COMMENT '面试方向' AFTER `interview_suggestions`;