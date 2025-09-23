package com.hewei.hzyjy.xunzhi.controller;

import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.dto.req.doubao.DoubaoMessageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.doubao.DoubaoMessageRespDTO;
import com.hewei.hzyjy.xunzhi.toolkit.doubao.DoubaoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 豆包大模型功能控制器
 * 提供豆包大模型对话接口
 * 
 * @author hewei
 */
@Slf4j
@RestController
@RequestMapping("/api/xunzhi/v1/doubao")
@RequiredArgsConstructor
public class DoubaoController {

    private final DoubaoClient doubaoClient;

    /**
     * 豆包大模型对话接口（无需鉴权）
     * 
     * @param request 对话请求
     * @return 对话响应
     */
    @PostMapping("/chat")
    public Result<?> chat(@RequestBody DoubaoMessageReqDTO request) {
        log.info("收到豆包对话请求: {}", request.getMessage());
        
        try {
            // 检查客户端是否已初始化
            if (!doubaoClient.isInitialized()) {
                log.error("豆包客户端未初始化");
                return Results.failure("500", "豆包客户端未初始化，请检查配置");
            }
            
            // 调用豆包大模型
            String response = doubaoClient.chat(request.getMessage(), request.getSystemPrompt());
            
            // 构建响应
            DoubaoMessageRespDTO respDTO = DoubaoMessageRespDTO.builder()
                    .message(response)
                    .modelId("doubao-lite-32k-240828")
                    .timestamp(System.currentTimeMillis())
                    .build();
            
            log.info("豆包对话成功，响应长度: {}", response.length());
            return Results.success(respDTO);
            
        } catch (Exception e) {
            log.error("豆包对话处理失败", e);
            return Results.failure("500", "对话处理失败: " + e.getMessage());
        }
    }
    
    /**
     * 简单文本对话接口（GET方式，便于测试）
     * 
     * @param message 用户消息
     * @return 对话响应
     */
    @GetMapping("/simple-chat")
    public Result<?> simpleChat(@RequestParam String message) {
        log.info("收到简单豆包对话请求: {}", message);
        
        try {
            // 检查客户端是否已初始化
            if (!doubaoClient.isInitialized()) {
                log.error("豆包客户端未初始化");
                return Results.failure("500", "豆包客户端未初始化，请检查配置");
            }
            
            // 调用豆包大模型
            String response = doubaoClient.chat(message);
            
            // 构建响应
            DoubaoMessageRespDTO respDTO = DoubaoMessageRespDTO.builder()
                    .message(response)
                    .modelId("doubao-lite-32k-240828")
                    .timestamp(System.currentTimeMillis())
                    .build();
            
            log.info("简单豆包对话成功，响应长度: {}", response.length());
            return Results.success(respDTO);
            
        } catch (Exception e) {
            log.error("简单豆包对话处理失败", e);
            return Results.failure("500", "对话处理失败: " + e.getMessage());
        }
    }
    
    /**
     * 健康检查接口
     * 
     * @return 健康状态
     */
    @GetMapping("/health")
    public Result<?> health() {
        try {
            boolean initialized = doubaoClient.isInitialized();
            if (initialized) {
                return Results.success("豆包大模型服务正常");
            } else {
                return Results.failure("500", "豆包大模型服务未初始化");
            }
        } catch (Exception e) {
            log.error("健康检查失败", e);
            return Results.failure("500", "健康检查失败: " + e.getMessage());
        }
    }
}
