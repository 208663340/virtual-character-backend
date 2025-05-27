package com.nageoffer.shortlink.admin.controller;

import com.nageoffer.shortlink.admin.dto.req.agent.AgentMessageReqDTO;
import com.nageoffer.shortlink.admin.dto.req.user.UserMessageReqDTO;
import com.nageoffer.shortlink.admin.service.AgentMessageService;
import com.nageoffer.shortlink.admin.toolkit.ai.XingChenAIClient;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executors;


@RestController
@RequiredArgsConstructor
public class AgentController {

    private final XingChenAIClient xingChenAIClient;
//    private final AgentMessageService agentMessageService;


    @GetMapping("/api/short-link/admin/v1/agent/chat")
    public SseEmitter chat(@RequestBody UserMessageReqDTO requestParam) {
        // 1. 创建 SSE 发射器（设置超时时间，例如 30 分钟）
        SseEmitter emitter = new SseEmitter(1800000L);

        // 2. 异步执行 AI 调用，避免阻塞主线程
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                // 3. 调用 AI 服务，并将输出流绑定到 SseEmitter
                xingChenAIClient.chat(
                        requestParam.getInputMessage(),
                        true,
                        new OutputStream() {
                            @Override
                            public void write(int b) throws IOException {
                                // 不需要实现（已通过 flush 发送）
                            }

                            @Override
                            public void write(byte[] b, int off, int len) throws IOException {
                                // 将数据发送到前端
                                emitter.send(SseEmitter.event().data(new String(b, off, len)));
                            }

                            @Override
                            public void flush() {
                                // 确保数据立即发送
                            }
                        },
                        data -> System.out.println("[AI 回调] " + data)
                );

                // 4. 发送完成事件
                emitter.send(SseEmitter.event().name("end").data(""));
                emitter.complete();

            } catch (Exception e) {
                // 5. 处理异常
                emitter.completeWithError(e);
                System.err.println("SSE 通信异常: " + e.getMessage());
            }
        });

        // 6. 设置超时和完成回调
        emitter.onTimeout(() -> {
            System.out.println("SSE 连接超时");
            emitter.complete();
        });

        emitter.onCompletion(() -> System.out.println("SSE 连接完成"));

        return emitter;
    }
}
