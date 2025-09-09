package com.hewei.hzyjy.xunzhi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hewei.hzyjy.xunzhi.dao.entity.InterviewRecordDO;
import com.hewei.hzyjy.xunzhi.dto.req.interview.InterviewRecordPageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.interview.InterviewRecordSaveReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.interview.InterviewRecordRespDTO;

import java.util.Map;

/**
 * 面试记录服务接口
 */
public interface InterviewRecordService extends IService<InterviewRecordDO> {

    /**
     * 保存面试记录
     * @param sessionId 会话ID
     * @param requestParam 保存请求参数
     */
    void saveInterviewRecord(String sessionId, InterviewRecordSaveReqDTO requestParam);

    /**
     * 分页查询用户面试记录
     * @param username 用户名
     * @param requestParam 分页查询参数
     * @return 分页结果
     */
    IPage<InterviewRecordRespDTO> pageInterviewRecords(String username, InterviewRecordPageReqDTO requestParam);

    /**
     * 根据会话ID获取面试记录
     * @param sessionId 会话ID
     * @return 面试记录
     */
    InterviewRecordRespDTO getBySessionId(String sessionId);
    
    /**
     * 从Redis保存面试记录
     * @param sessionId 会话ID
     */
    void saveInterviewRecordFromRedis(String sessionId);
    
    /**
     * 解析面试建议字符串为Map格式
     * @param suggestionsString 面试建议字符串（分号分隔）
     * @return 解析后的建议Map，key为编号，value为建议内容
     */
    Map<String, String> parseInterviewSuggestions(String suggestionsString);
}