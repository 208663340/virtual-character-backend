package com.nageoffer.shortlink.xunzhi.config.redis;

import com.nageoffer.shortlink.xunzhi.service.tool.AiSessionCacheService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Redis会话缓存定时任务配置
 * 负责定期清理过期缓存和执行异步同步任务
 */
@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "xunzhi-agent.redis-session.enable", havingValue = "true", matchIfMissing = true)
public class RedisSessionScheduleConfig {

    private final AiSessionCacheService aiSessionCacheService;

    /**
     * 定期清理过期缓存和同步数据
     * 每5分钟执行一次
     */
    @Scheduled(fixedRate = 5 * 60 * 1000) // 5分钟
    public void cleanExpiredCacheTask() {
        try {
            log.debug("[定时任务] 开始执行Redis会话缓存清理任务");
            aiSessionCacheService.cleanExpiredCache();
            log.debug("[定时任务] Redis会话缓存清理任务执行完成");
        } catch (Exception e) {
            log.error("[定时任务] Redis会话缓存清理任务执行失败", e);
        }
    }

    /**
     * 应用关闭时的清理任务
     * 确保所有缓存数据都同步到数据库
     */
    @PreDestroy
    public void onApplicationShutdown() {
        try {
            log.info("[应用关闭] 开始执行最终的缓存同步任务");
            aiSessionCacheService.cleanExpiredCache();
            // 等待一段时间确保异步任务完成
            Thread.sleep(3000);
            log.info("[应用关闭] 缓存同步任务执行完成");
        } catch (Exception e) {
            log.error("[应用关闭] 缓存同步任务执行失败", e);
        }
    }
}