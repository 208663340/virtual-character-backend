package com.nageoffer.shortlink.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.admin.dao.entity.AgentConversation;
import org.apache.ibatis.annotations.Param;

/**
 * 智能体会话表数据库操作Mapper
 */
public interface AgentConversationMapper extends BaseMapper<AgentConversation> {

    /**
     * 分页查询用户会话列表
     */
    IPage<AgentConversation> selectConversationPage(
            Page<AgentConversation> page,
            @Param("userId") Long userId,
            @Param("agentId") Long agentId,
            @Param("status") Integer status,
            @Param("keyword") String keyword
    );
}