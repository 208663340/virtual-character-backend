package com.hewei.hzyjy.xunzhi.aspect;

import cn.hutool.core.util.StrUtil;
import com.hewei.hzyjy.xunzhi.annotation.PreventDuplicateSubmit;
import com.hewei.hzyjy.xunzhi.common.convention.exception.ClientException;
import com.hewei.hzyjy.xunzhi.common.util.SaTokenUtil;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiMessageReqDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 防重提交切面
 * 基于Redisson分布式锁实现防重提交功能
 * 
 * @author nageoffer
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PreventDuplicateSubmitAspect {
    
    private final RedissonClient redissonClient;
    private final SaTokenUtil saTokenUtil;
    
    @Around("@annotation(preventDuplicateSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, PreventDuplicateSubmit preventDuplicateSubmit) throws Throwable {
        // 构建锁的key
        String lockKey = buildLockKey(joinPoint, preventDuplicateSubmit);
        
        // 获取分布式锁
        RLock lock = redissonClient.getLock(lockKey);
        
        boolean acquired = false;
        try {
            // 尝试获取锁
            acquired = lock.tryLock(preventDuplicateSubmit.waitTime(), preventDuplicateSubmit.expireTime(), TimeUnit.SECONDS);
            
            if (!acquired) {
                log.warn("防重提交拦截: {}", lockKey);
                throw new ClientException(preventDuplicateSubmit.message());
            }
            
            log.debug("获取防重提交锁成功: {}", lockKey);
            
            // 执行目标方法
            return joinPoint.proceed();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ClientException("系统繁忙，请稍后重试");
        } finally {
            // 释放锁
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.debug("释放防重提交锁: {}", lockKey);
            }
        }
    }
    
    /**
     * 构建锁的key
     */
    private String buildLockKey(ProceedingJoinPoint joinPoint, PreventDuplicateSubmit annotation) {
        StringBuilder keyBuilder = new StringBuilder(annotation.prefix());
        
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] paramNames = signature.getParameterNames();
        
        String username = null;
        String sessionId = null;
        Integer messageSeq = null;
        
        // 从HTTP请求中获取用户名
        if (annotation.userLevel()) {
            try {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    username = saTokenUtil.getUsernameFromRequest(request);
                }
            } catch (Exception e) {
                log.warn("获取用户名失败", e);
            }
        }
        
        // 从方法参数中提取sessionId和messageSeq
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            
            // 处理路径变量sessionId
            if ("sessionId".equals(paramNames[i]) && arg instanceof String) {
                sessionId = (String) arg;
            }
            
            // 处理AiMessageReqDTO参数
            if (arg instanceof AiMessageReqDTO) {
                AiMessageReqDTO reqDTO = (AiMessageReqDTO) arg;
                if (annotation.sessionLevel() && StrUtil.isNotBlank(reqDTO.getSessionId())) {
                    sessionId = reqDTO.getSessionId();
                }
                if (annotation.messageSeqLevel() && reqDTO.getMessageSeq() != null) {
                    messageSeq = reqDTO.getMessageSeq();
                }
            }
        }
        
        // 构建完整的锁key
        if (annotation.userLevel() && StrUtil.isNotBlank(username)) {
            keyBuilder.append(":").append(username);
        }
        
        if (annotation.sessionLevel() && StrUtil.isNotBlank(sessionId)) {
            keyBuilder.append(":").append(sessionId);
        }
        
        if (annotation.messageSeqLevel() && messageSeq != null) {
            keyBuilder.append(":").append(messageSeq);
        }
        
        // 如果没有任何标识符，使用方法签名作为fallback
        if (keyBuilder.toString().equals(annotation.prefix())) {
            keyBuilder.append(":").append(method.getName());
        }
        
        return keyBuilder.toString();
    }
}