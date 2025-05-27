package com.nageoffer.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.dao.entity.AgentMessage;
import com.nageoffer.shortlink.admin.service.AgentMessageService;
import com.nageoffer.shortlink.admin.dao.mapper.AgentMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 20866
* @description 针对表【agent_message_0】的数据库操作Service实现
* @createDate 2025-05-27 13:33:37
*/
@Service
public class AgentMessageServiceImpl extends ServiceImpl<AgentMessageMapper, AgentMessage>
    implements AgentMessageService{

    @Override
    public void saveUserMessage(String message) {

    }

    @Override
    public void saveAIMessage(String message) {

    }
}




