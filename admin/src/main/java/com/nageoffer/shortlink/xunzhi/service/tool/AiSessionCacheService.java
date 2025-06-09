package com.nageoffer.shortlink.xunzhi.service.tool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.nageoffer.shortlink.xunzhi.common.constant.RedisCacheConstant;
import com.nageoffer.shortlink.xunzhi.config.redis.RedisSessionProperties;
import com.nageoffer.shortlink.xunzhi.dao.entity.AgentMessage;
import com.nageoffer.shortlink.xunzhi.dao.repository.AgentMessageRepository;
import com.nageoffer.shortlink.xunzhi.dto.resp.agent.AgentMessageHistoryRespDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis会话缓存服务
 * 负责管理聊天记录的Redis缓存和异步数据库同步
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AiSessionCacheService {

    private final StringRedisTemplate stringRedisTemplate;
    private final AgentMessageRepository agentMessageRepository;
    private final RedisSessionProperties redisSessionProperties;

    /**
     * 添加消息到Redis缓存
     * @param sessionId 会话ID
     * @param message 消息对象
     */
    public void addMessageToCache(String sessionId, AgentMessage message) {
        try {
            String key = RedisCacheConstant.SESSION_MESSAGES_KEY + sessionId;
            String messageJson = JSON.toJSONString(message);
            
            // 使用List结构存储消息，按时间顺序
            stringRedisTemplate.opsForList().rightPush(key, messageJson);
            
            // 设置过期时间
            stringRedisTemplate.expire(key, redisSessionProperties.getMessageExpireSeconds(), TimeUnit.SECONDS);
            
            // 添加到同步队列
            addToSyncQueue(sessionId);
            
            log.debug("[Redis缓存] 添加消息到缓存: sessionId={}, messageType={}, messageSeq={}", 
                    sessionId, message.getMessageType(), message.getMessageSeq());
        } catch (Exception e) {
            log.error("[Redis缓存] 添加消息到缓存失败: sessionId={}", sessionId, e);
        }
    }

    /**
     * 从Redis缓存获取会话历史消息
     * @param sessionId 会话ID
     * @return 历史消息列表
     */
    public List<AgentMessageHistoryRespDTO> getMessagesFromCache(String sessionId) {
        try {
            String key = RedisCacheConstant.SESSION_MESSAGES_KEY + sessionId;
            List<String> messageJsonList = stringRedisTemplate.opsForList().range(key, 0, -1);
            
            if (CollUtil.isEmpty(messageJsonList)) {
                // 缓存中没有数据，从数据库加载
                return loadMessagesFromDatabase(sessionId);
            }
            
            // 刷新过期时间
            stringRedisTemplate.expire(key, redisSessionProperties.getMessageExpireSeconds(), TimeUnit.SECONDS);
            
            List<AgentMessageHistoryRespDTO> messages = messageJsonList.stream()
                    .map(json -> {
                        AgentMessage message = JSON.parseObject(json, AgentMessage.class);
                        AgentMessageHistoryRespDTO respDTO = new AgentMessageHistoryRespDTO();
                        BeanUtils.copyProperties(message, respDTO);
                        return respDTO;
                    })
                    .collect(Collectors.toList());
            
            log.debug("[Redis缓存] 从缓存获取消息: sessionId={}, count={}", sessionId, messages.size());
            return messages;
        } catch (Exception e) {
            log.error("[Redis缓存] 从缓存获取消息失败: sessionId={}", sessionId, e);
            // 缓存失败时从数据库获取
            return loadMessagesFromDatabase(sessionId);
        }
    }

    /**
     * 从数据库加载消息并缓存到Redis
     * @param sessionId 会话ID
     * @return 历史消息列表
     */
    private List<AgentMessageHistoryRespDTO> loadMessagesFromDatabase(String sessionId) {
        try {
            // 从数据库查询消息
            List<AgentMessage> messages = agentMessageRepository
                    .findBySessionIdAndDelFlagOrderByMessageSeqAsc(sessionId, 0);
            
            if (CollUtil.isNotEmpty(messages)) {
                // 缓存到Redis
                String key = RedisCacheConstant.SESSION_MESSAGES_KEY + sessionId;
                List<String> messageJsonList = messages.stream()
                        .map(JSON::toJSONString)
                        .collect(Collectors.toList());
                
                stringRedisTemplate.opsForList().rightPushAll(key, messageJsonList);
                stringRedisTemplate.expire(key, redisSessionProperties.getMessageExpireSeconds(), TimeUnit.SECONDS);
                
                log.debug("[Redis缓存] 从数据库加载并缓存消息: sessionId={}, count={}", sessionId, messages.size());
            }
            
            return messages.stream().map(message -> {
                AgentMessageHistoryRespDTO respDTO = new AgentMessageHistoryRespDTO();
                BeanUtils.copyProperties(message, respDTO);
                return respDTO;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[Redis缓存] 从数据库加载消息失败: sessionId={}", sessionId, e);
            return new ArrayList<>();
        }
    }

    /**
     * 添加会话到同步队列
     * @param sessionId 会话ID
     */
    private void addToSyncQueue(String sessionId) {
        try {
            // 检查是否需要同步（避免频繁同步）
            String lastSyncKey = RedisCacheConstant.SESSION_LAST_SYNC_KEY + sessionId;
            String lastSyncTime = stringRedisTemplate.opsForValue().get(lastSyncKey);
            
            long currentTime = System.currentTimeMillis();
            if (StrUtil.isNotBlank(lastSyncTime)) {
                long lastSync = Long.parseLong(lastSyncTime);
                if (currentTime - lastSync < redisSessionProperties.getSyncDelaySeconds() * 1000) {
                    return; // 距离上次同步时间太短，跳过
                }
            }
            
            // 添加到同步队列（使用Set避免重复）
            stringRedisTemplate.opsForSet().add(RedisCacheConstant.SESSION_SYNC_QUEUE_KEY, sessionId);
            
            // 异步执行同步
            asyncSyncToDatabase(sessionId);
        } catch (Exception e) {
            log.error("[Redis缓存] 添加到同步队列失败: sessionId={}", sessionId, e);
        }
    }

    /**
     * 异步同步会话数据到数据库
     * @param sessionId 会话ID
     */
    @Async
    public void asyncSyncToDatabase(String sessionId) {
        try {
            log.debug("[异步同步] 开始同步会话数据: sessionId={}", sessionId);
            
            String key = RedisCacheConstant.SESSION_MESSAGES_KEY + sessionId;
            List<String> messageJsonList = stringRedisTemplate.opsForList().range(key, 0, -1);
            
            if (CollUtil.isEmpty(messageJsonList)) {
                return;
            }
            
            // 获取数据库中已存在的消息序号
            Set<Integer> existingSeqs = agentMessageRepository
                    .findBySessionIdAndDelFlag(sessionId, 0)
                    .stream()
                    .map(AgentMessage::getMessageSeq)
                    .collect(Collectors.toSet());
            
            // 过滤出需要插入的新消息
            List<AgentMessage> newMessages = messageJsonList.stream()
                    .map(json -> JSON.parseObject(json, AgentMessage.class))
                    .filter(message -> !existingSeqs.contains(message.getMessageSeq()))
                    .collect(Collectors.toList());
            
            if (CollUtil.isNotEmpty(newMessages)) {
                // 批量插入新消息
                agentMessageRepository.saveAll(newMessages);
                log.info("[异步同步] 同步完成: sessionId={}, 新增消息数={}", sessionId, newMessages.size());
            }
            
            // 更新最后同步时间
            String lastSyncKey = RedisCacheConstant.SESSION_LAST_SYNC_KEY + sessionId;
            stringRedisTemplate.opsForValue().set(lastSyncKey, String.valueOf(System.currentTimeMillis()), 
                    redisSessionProperties.getMessageExpireSeconds(), TimeUnit.SECONDS);
            
            // 从同步队列中移除
            stringRedisTemplate.opsForSet().remove(RedisCacheConstant.SESSION_SYNC_QUEUE_KEY, sessionId);
            
        } catch (Exception e) {
            log.error("[异步同步] 同步会话数据失败: sessionId={}", sessionId, e);
        }
    }

    /**
     * 清理过期的会话缓存
     */
    @Async
    public void cleanExpiredCache() {
        try {
            // 获取所有同步队列中的会话ID
            Set<String> sessionIds = stringRedisTemplate.opsForSet().members(RedisCacheConstant.SESSION_SYNC_QUEUE_KEY);
            
            if (CollUtil.isNotEmpty(sessionIds)) {
                for (String sessionId : sessionIds) {
                    asyncSyncToDatabase(sessionId);
                }
                log.info("[缓存清理] 处理待同步会话数: {}", sessionIds.size());
            }
        } catch (Exception e) {
            log.error("[缓存清理] 清理过期缓存失败", e);
        }
    }

    /**
     * 获取会话的下一个消息序号
     * @param sessionId 会话ID
     * @return 下一个消息序号
     */
    public Integer getNextMessageSeq(String sessionId) {
        try {
            // 先从缓存获取最大序号
            String key = RedisCacheConstant.SESSION_MESSAGES_KEY + sessionId;
            List<String> messageJsonList = stringRedisTemplate.opsForList().range(key, -1, -1);
            
            if (CollUtil.isNotEmpty(messageJsonList)) {
                AgentMessage lastMessage = JSON.parseObject(messageJsonList.get(0), AgentMessage.class);
                return lastMessage.getMessageSeq() + 1;
            }
            
            // 缓存中没有数据，从数据库查询
            List<AgentMessage> messages = agentMessageRepository
                    .findBySessionIdAndDelFlagOrderByMessageSeqDesc(sessionId, 0);
            AgentMessage lastMessage = CollUtil.isNotEmpty(messages) ? messages.get(0) : null;
            
            return lastMessage != null ? lastMessage.getMessageSeq() + 1 : 1;
        } catch (Exception e) {
            log.error("[Redis缓存] 获取下一个消息序号失败: sessionId={}", sessionId, e);
            return 1;
        }
    }
}