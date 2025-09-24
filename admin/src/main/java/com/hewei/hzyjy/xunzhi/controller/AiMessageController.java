package com.hewei.hzyjy.xunzhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hewei.hzyjy.xunzhi.annotation.PreventDuplicateSubmit;
import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.dto.req.ai.AiMessageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.ai.AiMessageHistoryRespDTO;
import com.hewei.hzyjy.xunzhi.service.AiMessageService;
import com.hewei.hzyjy.xunzhi.common.util.SaTokenUtil;
import com.hewei.hzyjy.xunzhi.toolkit.doubao.DoubaoStreamClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * AI消息控制器
 * @author nageoffer
 */
@Slf4j
@RestController
@RequestMapping("/api/xunzhi/v1/ai")
@RequiredArgsConstructor
public class AiMessageController {
    
    private final AiMessageService aiMessageService;
    private final SaTokenUtil saTokenUtil;
    private final DoubaoStreamClient doubaoStreamClient;
    
    /**
     * AI聊天Flux接口（默认使用豆包大模型）
     */
    @PreventDuplicateSubmit(
        prefix = "ai_chat",
        expireTime = 30,
        waitTime = 0,
        message = "请勿重复发送消息，请等待当前消息处理完成",
        userLevel = true,
        sessionLevel = true,
        messageSeqLevel = true
    )
    @PostMapping(value = "/sessions/{sessionId}/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@PathVariable String sessionId, @RequestBody AiMessageReqDTO requestParam, HttpServletRequest request) {
        // 从token中获取用户名
        String username = saTokenUtil.getUsernameFromRequest(request);
        if (username != null) {
            requestParam.setUserName(username);
        }
        requestParam.setSessionId(sessionId);
        
        // 如果未指定AI ID，将使用默认的豆包配置（在service层处理）
        return aiMessageService.aiChatFlux(requestParam);
    }
    
    /**
     * 查询会话历史消息
     */
    @GetMapping("/history/{sessionId}")
    public Result<List<AiMessageHistoryRespDTO>> getConversationHistory(@PathVariable String sessionId) {
        List<AiMessageHistoryRespDTO> result = aiMessageService.getConversationHistory(sessionId);
        return Results.success(result);
    }
    
    /**
     * 分页查询历史消息
     */
    @GetMapping("/history/page")
    public Result<IPage<AiMessageHistoryRespDTO>> pageHistoryMessages(
            @RequestParam(required = false) String sessionId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        // 从token中获取用户名
        String username = saTokenUtil.getUsernameFromRequest(request);
        IPage<AiMessageHistoryRespDTO> result = aiMessageService.pageHistoryMessages(sessionId, current, size);
        return Results.success(result);
    }
    
    /**
     * 豆包对话接口
     */
    @GetMapping(value = "/doubao/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doubaoChat(@RequestParam String message) {
        log.info("收到豆包对话请求: {}", message);

        return Flux.create(sink -> {
            try {
                // 检查客户端是否已初始化
                if (!doubaoStreamClient.isInitialized()) {
                    sink.next("data: 豆包客户端未初始化\n\n");
                    sink.complete();
                    return;
                }

                // 对消息进行URL解码
                String decodedMessage = URLDecoder.decode(message, StandardCharsets.UTF_8);
                log.info("解码后的消息: {}", decodedMessage);

                // 创建输出流来捕获流式响应
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                
                // 调用豆包流式客户端
                doubaoStreamClient.chatStream(
                    decodedMessage,
                    null, // 无历史消息
                    true, // 启用流式响应
                    new OutputStream() {
                        @Override
                        public void write(int b) throws IOException {
                            outputStream.write(b);
                        }

                        @Override
                        public void write(byte[] b, int off, int len) throws IOException {
                            outputStream.write(b, off, len);
                            // 将接收到的数据块发送到SSE流
                            String chunk = new String(b, off, len, StandardCharsets.UTF_8);
                            if (!chunk.trim().isEmpty()) {
                                sink.next("data: " + chunk + "\n\n");
                            }
                        }

                        @Override
                        public void flush() throws IOException {
                            outputStream.flush();
                        }
                    },
                    data -> {
                        // 回调函数，处理接收到的数据
                        if (data != null && !data.trim().isEmpty()) {
                            sink.next("data: " + data + "\n\n");
                        }
                    },
                    null, // 使用默认API Key
                    null  // 使用默认模型
                );
                
                sink.next("data: [DONE]\n\n");
                sink.complete();
                
            } catch (Exception e) {
                log.error("豆包对话处理失败", e);
                sink.next("data: 错误: " + e.getMessage() + "\n\n");
                sink.error(e);
            }
        });
    }
}