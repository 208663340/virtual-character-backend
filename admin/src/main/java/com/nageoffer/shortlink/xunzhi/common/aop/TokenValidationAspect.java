package com.nageoffer.shortlink.xunzhi.common.aop;

import com.nageoffer.shortlink.xunzhi.common.convention.exception.ClientException;
import com.nageoffer.shortlink.xunzhi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Token验证切面
 * 统一拦截需要token验证的接口
 * 
 * @author nageoffer
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenValidationAspect {

    private final UserService userService;

    /**
     * 拦截除UserController之外的所有Controller中的公共方法
     */
    @Before("execution(public * com.nageoffer.shortlink.xunzhi.controller.*.*(..)) && !execution(public * com.nageoffer.shortlink.xunzhi.controller.UserController.*(..))")
    public void validateToken(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new ClientException("无法获取请求上下文");
        }
        
        HttpServletRequest request = attributes.getRequest();
        String token = null;
        String username = null;
        
        // 先尝试从header获取Authorization
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && !authHeader.trim().isEmpty()) {
            // 处理Bearer前缀
            if (authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }
            
            // 解析username=xxx&token=xxx格式
            if (authHeader.contains("username=") && authHeader.contains("token=")) {
                String[] params = authHeader.split("&");
                for (String param : params) {
                    if (param.startsWith("username=")) {
                        username = param.substring(9); // "username="的长度是9
                    } else if (param.startsWith("token=")) {
                        token = param.substring(6); // "token="的长度是6
                    }
                }
            } else {
                // 如果不是新格式，按原来的逻辑处理
                token = authHeader;
                username = request.getHeader("username");
            }
        }
        
        // 如果header中没有获取到，尝试从URL参数获取
        if (token == null || token.trim().isEmpty()) {
            token = request.getParameter("token");
        }
        
        if (username == null || username.trim().isEmpty()) {
            username = request.getParameter("username");
        }
        
        // 验证参数
        if (token == null || token.trim().isEmpty()) {
            throw new ClientException("缺少token参数");
        }
        
        if (username == null || username.trim().isEmpty()) {
            throw new ClientException("缺少用户名");
        }
        
        // 验证token
        if (!userService.checkLogin(username, token)) {
            throw new ClientException("用户未登录或登录已过期");
        }
        
        log.debug("Token验证通过，用户: {}, 方法: {}", username, joinPoint.getSignature().getName());
    }
}