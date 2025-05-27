package com.nageoffer.shortlink.admin.service;

import com.nageoffer.shortlink.admin.dao.entity.AgentMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 20866
* @description 针对表【agent_message_0】的数据库操作Service
* @createDate 2025-05-27 13:33:37
*/
public interface AgentMessageService extends IService<AgentMessage> {

    /**
     * 保存用户消息
     */
    void saveUserMessage(String message);

    /**
     * 保存AI响应消息
     */
    void saveAIMessage(String message);

}
