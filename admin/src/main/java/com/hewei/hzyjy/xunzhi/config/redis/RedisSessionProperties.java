package com.hewei.hzyjy.xunzhi.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis会话缓存配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "xunzhi-agent.redis-session")
public class RedisSessionProperties {

    /**
     * 是否启用Redis会话缓存
     */
    private boolean enable = true;

    /**
     * 会话消息缓存过期时间（秒）
     * 默认7天
     */
    private long messageExpireSeconds = 7 * 24 * 60 * 60;

    /**
     * 异步同步到数据库的延迟时间（秒）
     * 默认30秒
     */
    private long syncDelaySeconds = 30;

    /**
     * 批量同步的最大消息数量
     */
    private int batchSyncSize = 100;

    /**
     * 同步队列的最大长度
     */
    private int maxQueueSize = 10000;

    /**
     * 清理任务的执行间隔（秒）
     * 默认5分钟
     */
    private long cleanIntervalSeconds = 5 * 60;
}