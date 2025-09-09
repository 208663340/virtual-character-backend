package com.hewei.hzyjy.xunzhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hewei.hzyjy.xunzhi.dao.entity.InterviewRecordDO;
import com.hewei.hzyjy.xunzhi.dao.entity.UserDO;
import com.hewei.hzyjy.xunzhi.dao.entity.AgentConversation;
import com.hewei.hzyjy.xunzhi.dao.mapper.InterviewRecordMapper;
import com.hewei.hzyjy.xunzhi.dao.repository.AgentConversationRepository;
import com.hewei.hzyjy.xunzhi.dto.req.interview.InterviewRecordPageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.interview.InterviewRecordSaveReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.interview.InterviewRecordRespDTO;
import com.hewei.hzyjy.xunzhi.service.InterviewRecordService;
import com.hewei.hzyjy.xunzhi.service.UserService;
import com.hewei.hzyjy.xunzhi.service.InterviewQuestionCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 面试记录服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewRecordServiceImpl extends ServiceImpl<InterviewRecordMapper, InterviewRecordDO> implements InterviewRecordService {

    private final UserService userService;
    private final InterviewQuestionCacheService interviewQuestionCacheService;
    private final AgentConversationRepository agentConversationRepository;

    @Override
    public void saveInterviewRecord(String sessionId, InterviewRecordSaveReqDTO requestParam) {
        // 从请求参数中获取用户名，然后获取用户信息
        String username = requestParam.getUsername();
        if (StrUtil.isBlank(username)) {
            throw new RuntimeException("用户名不能为空");
        }
        
        LambdaQueryWrapper<UserDO> userQueryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = userService.getOne(userQueryWrapper);
        if (userDO == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 从Redis获取面试数据
        Integer totalScore = null;
        String suggestions = null;
        
        // 如果请求参数中没有提供评分和建议，则从Redis获取
        if (requestParam.getInterviewScore() == null || StrUtil.isBlank(requestParam.getInterviewSuggestions())) {
            try {
                // 获取总分
                if (requestParam.getInterviewScore() == null) {
                    totalScore = interviewQuestionCacheService.getSessionTotalScore(sessionId);
                    log.info("从Redis获取会话 {} 的总分: {}", sessionId, totalScore);
                } else {
                    totalScore = requestParam.getInterviewScore();
                }
                
                // 获取面试建议
                if (StrUtil.isBlank(requestParam.getInterviewSuggestions())) {
                    Map<String, String> suggestionsMap = interviewQuestionCacheService.getSessionInterviewSuggestions(sessionId);
                    if (!suggestionsMap.isEmpty()) {
                        // 将建议按编号顺序拼接
                        suggestions = suggestionsMap.entrySet().stream()
                                .sorted((e1, e2) -> {
                                    try {
                                        return Integer.compare(Integer.parseInt(e1.getKey()), Integer.parseInt(e2.getKey()));
                                    } catch (NumberFormatException ex) {
                                        return e1.getKey().compareTo(e2.getKey());
                                    }
                                })
                                .map(Map.Entry::getValue)
                                .collect(Collectors.joining("; "));
                        log.info("从Redis获取会话 {} 的面试建议: {}", sessionId, suggestions);
                    }
                } else {
                    suggestions = requestParam.getInterviewSuggestions();
                }
            } catch (Exception e) {
                log.error("从Redis获取面试数据失败，会话: {}, 错误: {}", sessionId, e.getMessage(), e);
                // 如果Redis获取失败，使用请求参数中的数据
                totalScore = requestParam.getInterviewScore();
                suggestions = requestParam.getInterviewSuggestions();
            }
        } else {
            totalScore = requestParam.getInterviewScore();
            suggestions = requestParam.getInterviewSuggestions();
        }

        // 检查是否已存在该会话的面试记录
        LambdaQueryWrapper<InterviewRecordDO> queryWrapper = Wrappers.lambdaQuery(InterviewRecordDO.class)
                .eq(InterviewRecordDO::getUserId, userDO.getId())
                .eq(InterviewRecordDO::getSessionId, sessionId)
                .eq(InterviewRecordDO::getDelFlag, 0);
        InterviewRecordDO existingRecord = baseMapper.selectOne(queryWrapper);

        if (existingRecord != null) {
            // 更新现有记录
            existingRecord.setInterviewScore(totalScore);
            existingRecord.setInterviewSuggestions(suggestions);
            if (StrUtil.isNotBlank(requestParam.getInterviewDirection())) {
                existingRecord.setInterviewDirection(requestParam.getInterviewDirection());
            }
            existingRecord.setUpdateTime(new Date());
            baseMapper.updateById(existingRecord);
            log.info("更新面试记录成功，用户: {}, sessionId: {}", username, sessionId);
        } else {
            // 创建新记录
            InterviewRecordDO interviewRecord = new InterviewRecordDO();
            interviewRecord.setUserId(userDO.getId());
            interviewRecord.setSessionId(sessionId);
            interviewRecord.setInterviewScore(totalScore);
            interviewRecord.setInterviewSuggestions(suggestions);
            interviewRecord.setInterviewDirection(requestParam.getInterviewDirection());
            interviewRecord.setCreateTime(new Date());
            interviewRecord.setUpdateTime(new Date());
            interviewRecord.setDelFlag(0);
            baseMapper.insert(interviewRecord);
            log.info("创建面试记录成功，用户: {}, sessionId: {}", username, sessionId);
        }
    }

    @Override
    public IPage<InterviewRecordRespDTO> pageInterviewRecords(String username, InterviewRecordPageReqDTO requestParam) {
        // 根据用户名获取用户信息
        LambdaQueryWrapper<UserDO> userQueryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = userService.getOne(userQueryWrapper);
        if (userDO == null) {
            throw new RuntimeException("用户不存在");
        }

        // 构建分页查询
        Page<InterviewRecordDO> page = new Page<>(requestParam.getPageNum(), requestParam.getPageSize());
        LambdaQueryWrapper<InterviewRecordDO> queryWrapper = Wrappers.lambdaQuery(InterviewRecordDO.class)
                .eq(InterviewRecordDO::getUserId, userDO.getId())
                .eq(InterviewRecordDO::getDelFlag, 0);

        // 添加筛选条件
        if (StringUtils.hasText(requestParam.getSessionId())) {
            queryWrapper.eq(InterviewRecordDO::getSessionId, requestParam.getSessionId());
        }
        if (requestParam.getMinScore() != null) {
            queryWrapper.ge(InterviewRecordDO::getInterviewScore, requestParam.getMinScore());
        }
        if (requestParam.getMaxScore() != null) {
            queryWrapper.le(InterviewRecordDO::getInterviewScore, requestParam.getMaxScore());
        }
        if (StringUtils.hasText(requestParam.getInterviewDirection())) {
            queryWrapper.eq(InterviewRecordDO::getInterviewDirection, requestParam.getInterviewDirection());
        }

        // 按创建时间倒序排列
        queryWrapper.orderByDesc(InterviewRecordDO::getCreateTime);

        Page<InterviewRecordDO> recordPage = baseMapper.selectPage(page, queryWrapper);

        // 转换为响应DTO
        List<InterviewRecordRespDTO> resultList = recordPage.getRecords().stream()
                .map(record -> {
                    InterviewRecordRespDTO respDTO = new InterviewRecordRespDTO();
                    BeanUtils.copyProperties(record, respDTO);
                    // 解析面试建议字符串为Map格式
                    if (StrUtil.isNotBlank(record.getInterviewSuggestions())) {
                        respDTO.setInterviewSuggestionsMap(parseInterviewSuggestions(record.getInterviewSuggestions()));
                    }
                    return respDTO;
                })
                .collect(Collectors.toList());

        // 构建分页结果
        Page<InterviewRecordRespDTO> resultPage = new Page<>(recordPage.getCurrent(), recordPage.getSize(), recordPage.getTotal());
        resultPage.setRecords(resultList);
        return resultPage;
    }

    @Override
    public InterviewRecordRespDTO getBySessionId(String sessionId) {
        LambdaQueryWrapper<InterviewRecordDO> queryWrapper = Wrappers.lambdaQuery(InterviewRecordDO.class)
                .eq(InterviewRecordDO::getSessionId, sessionId)
                .eq(InterviewRecordDO::getDelFlag, 0);
        InterviewRecordDO record = baseMapper.selectOne(queryWrapper);
        
        if (record == null) {
            return null;
        }
        
        InterviewRecordRespDTO respDTO = new InterviewRecordRespDTO();
        BeanUtils.copyProperties(record, respDTO);
        // 解析面试建议字符串为Map格式
        if (StrUtil.isNotBlank(record.getInterviewSuggestions())) {
            respDTO.setInterviewSuggestionsMap(parseInterviewSuggestions(record.getInterviewSuggestions()));
        }
        return respDTO;
    }
    
    @Override
    public void saveInterviewRecordFromRedis(String sessionId) {
        // 通过sessionId获取会话信息，进而获取用户信息
        Optional<AgentConversation> conversationOpt = agentConversationRepository.findBySessionIdAndDelFlag(sessionId, 0);
        if (conversationOpt.isEmpty()) {
            throw new RuntimeException("会话不存在: " + sessionId);
        }
        
        AgentConversation conversation = conversationOpt.get();
        Long userId = conversation.getUserId();
        
        // 通过userId获取用户信息
        UserDO userDO = userService.getById(userId);
        if (userDO == null) {
            throw new RuntimeException("用户不存在: " + userId);
        }
        
        // 创建请求DTO，不设置评分和建议，让saveInterviewRecord方法从Redis获取
        InterviewRecordSaveReqDTO requestParam = new InterviewRecordSaveReqDTO();
        requestParam.setUsername(userDO.getUsername()); // 设置用户名
        // 不设置interviewScore和interviewSuggestions，让方法自动从Redis获取
        
        saveInterviewRecord(sessionId, requestParam);
        log.info("从Redis保存面试记录完成，sessionId: {}", sessionId);
    }
    
    @Override
    public Map<String, String> parseInterviewSuggestions(String suggestionsString) {
        Map<String, String> suggestionsMap = new java.util.LinkedHashMap<>();
        
        if (StrUtil.isBlank(suggestionsString)) {
            return suggestionsMap;
        }
        
        // 按分号分割建议字符串
        String[] suggestions = suggestionsString.split(";");
        
        for (int i = 0; i < suggestions.length; i++) {
            String suggestion = suggestions[i].trim();
            if (StrUtil.isNotBlank(suggestion)) {
                // 使用编号作为key，建议内容作为value
                suggestionsMap.put(String.valueOf(i + 1), suggestion);
            }
        }
        
        log.debug("解析面试建议字符串完成，原始字符串长度: {}, 解析出建议数量: {}", 
                suggestionsString.length(), suggestionsMap.size());
        
        return suggestionsMap;
    }
}