package com.hewei.hzyjy.xunzhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hewei.hzyjy.xunzhi.common.convention.result.Result;
import com.hewei.hzyjy.xunzhi.common.convention.result.Results;
import com.hewei.hzyjy.xunzhi.dto.req.agent.AgentConversationPageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.agent.AgentSessionCreateReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.agent.InterviewQuestionReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.agent.InterviewAnswerReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.agent.InterviewQuestionRespDTO;
import com.hewei.hzyjy.xunzhi.dto.req.user.UserMessageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.agent.AgentConversationRespDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.agent.AgentMessageHistoryRespDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.agent.AgentSessionCreateRespDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.agent.InterviewAnswerRespDTO;
import com.hewei.hzyjy.xunzhi.dto.req.agent.DemeanorEvaluationReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.agent.RadarChartDTO;
import com.hewei.hzyjy.xunzhi.dto.req.interview.InterviewRecordSaveReqDTO;
import com.hewei.hzyjy.xunzhi.dto.req.interview.InterviewRecordPageReqDTO;
import com.hewei.hzyjy.xunzhi.dto.resp.interview.InterviewRecordRespDTO;

import com.hewei.hzyjy.xunzhi.service.AgentConversationService;
import com.hewei.hzyjy.xunzhi.service.AgentMessageService;
import com.hewei.hzyjy.xunzhi.service.InterviewQuestionCacheService;
import com.hewei.hzyjy.xunzhi.service.InterviewRecordService;
import com.hewei.hzyjy.xunzhi.common.util.SaTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.util.List;
import java.util.Map;

/**
 *Agent文字聊天接口
 * @author nageoffer
 * @date 2023/9/27
 */
@Slf4j
@RestController
@RequestMapping("/api/xunzhi/v1/agents")
@RequiredArgsConstructor
public class AgentController {
    
    private final SaTokenUtil saTokenUtil;
    private final AgentMessageService agentMessageService;
    private final AgentConversationService agentConversationService;
    private final InterviewQuestionCacheService interviewQuestionCacheService;
    private final InterviewRecordService interviewRecordService;


    /**
     * 创建Agent会话
     * @param requestParam 会话创建请求参数
     * @return 会话ID和标题
     */
    @PostMapping("/sessions")
    public Result<AgentSessionCreateRespDTO> createSession(@RequestBody AgentSessionCreateReqDTO requestParam) {
        AgentSessionCreateRespDTO result = agentConversationService.createConversationWithTitle(
                requestParam.getUserName(), 
                requestParam.getAgentId(), 
                requestParam.getFirstMessage()
        );
        
        return Results.success(result);
    }

    /**
     * Agent文字聊天SSE接口
     * @return SSE流
     */
    @PostMapping("/sessions/{sessionId}/chat")
    public SseEmitter chat(@PathVariable String sessionId, @RequestBody UserMessageReqDTO requestParam, HttpServletRequest request) {
        // 从token中获取用户名
        String username = saTokenUtil.getUsernameFromRequest(request);
        if (username != null) {
            requestParam.setUserName(username);
        }
        requestParam.setSessionId(sessionId);
        return agentMessageService.agentChatSse(requestParam);
    }


    /**
     * 分页查询用户会话列表
     */
    @GetMapping("/conversations")
    public Result<IPage<AgentConversationRespDTO>> pageConversations(
            AgentConversationPageReqDTO requestParam,
            HttpServletRequest request) {
        // 从token中获取用户名
        String username = saTokenUtil.getUsernameFromRequest(request);
        return Results.success(agentConversationService.pageConversations(username, requestParam));
    }

    /**
     * 查询会话历史消息
     */
    @GetMapping("/conversations/{sessionId}/messages")
    public Result<List<AgentMessageHistoryRespDTO>> getConversationHistory(@PathVariable String sessionId) {
        return Results.success(agentMessageService.getConversationHistory(sessionId));
    }

    /**
     * 分页查询历史消息
     */
    @GetMapping("/messages/history")
    public Result<IPage<AgentMessageHistoryRespDTO>> pageHistoryMessages(
            @RequestParam(required = false) String sessionId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        // 从token中获取用户名
        String username = saTokenUtil.getUsernameFromRequest(request);
        return Results.success(agentMessageService.pageHistoryMessages(sessionId, current, size));
    }

    /**
     * 结束会话
     */
    @PutMapping("/conversations/{sessionId}/end")
    public Result<Void> endConversation(@PathVariable String sessionId) {
        agentConversationService.endConversation(sessionId);
        return Results.success();
    }

    /**
     * 面试题获取接口
     */
    @PostMapping("/sessions/{sessionId}/interview-questions")
    public Result<InterviewQuestionRespDTO> extractInterviewQuestions(
            @PathVariable String sessionId,
            @RequestParam("agentId") Long agentId,
            @RequestParam("resumePdf") MultipartFile resumePdf,
            HttpServletRequest request) {
        
        // 从token中获取用户名
        String username = saTokenUtil.getUsernameFromRequest(request);
        
        // 构建请求DTO
        InterviewQuestionReqDTO reqDTO = new InterviewQuestionReqDTO();
        reqDTO.setUserName(username);
        reqDTO.setAgentId(agentId);
        reqDTO.setSessionId(sessionId);
        reqDTO.setResumePdf(resumePdf);
        
        InterviewQuestionRespDTO result = agentMessageService.extractInterviewQuestions(reqDTO);
        return Results.success(result);
    }
    
    /**
     * 回答面试题接口
     */
    @PostMapping("/sessions/{sessionId}/interview/answer")
    public Result<InterviewAnswerRespDTO> answerInterviewQuestion(
            @RequestParam("questionNumber") String questionNumber,
            @RequestParam(value = "answerContent", required = false) String answerContent,
            @RequestPart(value = "audioFile", required = false) MultipartFile audioFile,
            @RequestParam(value = "sessionId", required = false) String sessionId,
            @RequestParam(value = "agentId", required = false) Long agentId,
            HttpServletRequest request) {
        
        // 从token中获取用户名
        String username = saTokenUtil.getUsernameFromRequest(request);
        
        // 构建请求DTO
        InterviewAnswerReqDTO requestParam = new InterviewAnswerReqDTO();
        requestParam.setQuestionNumber(questionNumber);
        requestParam.setAnswerContent(answerContent);
        requestParam.setAudioFile(audioFile);
        requestParam.setSessionId(sessionId);
        requestParam.setAgentId(agentId);
        
        InterviewAnswerRespDTO result = agentMessageService.answerInterviewQuestion(sessionId, requestParam);
         return Results.success(result);
     }
     
     /**
      * 获取用户面试题列表
      */
    @GetMapping("/sessions/{sessionId}/interview/questions")
    public Result<Map<String, String>> getSessionInterviewQuestions(
            @PathVariable String sessionId,
            HttpServletRequest request) {
         
        Map<String, String> questions = interviewQuestionCacheService.getSessionInterviewQuestions(sessionId);
        
        // 如果缓存中没有数据，尝试从数据库加载
        if (questions == null || questions.isEmpty()) {
            interviewQuestionCacheService.loadInterviewQuestionsFromDatabase(sessionId);
            questions = interviewQuestionCacheService.getSessionInterviewQuestions(sessionId);
        }
        
        return Results.success(questions);
    }
     
     /**
      * 获取用户当前总分
      */
     @GetMapping("/sessions/{sessionId}/interview/score")
     public Result<Integer> getSessionTotalScore(
             @PathVariable String sessionId,
             HttpServletRequest request) {
         
         Integer totalScore = interviewQuestionCacheService.getSessionTotalScore(sessionId);
         return Results.success(totalScore);
     }
     
     /**
      * 获取用户面试建议列表
      */
     @GetMapping("/sessions/{sessionId}/interview/suggestions")
     public Result<Map<String, String>> getSessionInterviewSuggestions(
             @PathVariable String sessionId,
             HttpServletRequest request) {
         
         Map<String, String> suggestions = interviewQuestionCacheService.getSessionInterviewSuggestions(sessionId);
         
         // 如果缓存中没有数据，尝试从数据库加载
         if (suggestions == null || suggestions.isEmpty()) {
             interviewQuestionCacheService.loadInterviewSuggestionsFromDatabase(sessionId);
             suggestions = interviewQuestionCacheService.getSessionInterviewSuggestions(sessionId);
         }
         
         return Results.success(suggestions);
     }

     /**
      * 获取用户简历评分
      */
     @GetMapping("/sessions/{sessionId}/resume/score")
     public Result<Integer> getSessionResumeScore(
             @PathVariable String sessionId,
             HttpServletRequest request) {
         
         Integer resumeScore = interviewQuestionCacheService.getSessionResumeScore(sessionId);
         
         // 如果缓存中没有数据，尝试从数据库加载
         if (resumeScore == null) {
             interviewQuestionCacheService.loadResumeScoreFromDatabase(sessionId);
             resumeScore = interviewQuestionCacheService.getSessionResumeScore(sessionId);
         }
         
         return Results.success(resumeScore);
     }
     
     /**
      * 获取雷达图数据
      */
     @GetMapping("/sessions/{sessionId}/radar-chart")
     public Result<RadarChartDTO> getRadarChartData(
             @PathVariable String sessionId,
             HttpServletRequest request) {
         
         RadarChartDTO radarChart = interviewQuestionCacheService.getRadarChartData(sessionId);
         
         // 同时存储面试记录
         try {
             interviewRecordService.saveInterviewRecordFromRedis(sessionId);
         } catch (Exception e) {
             // 记录日志但不影响雷达图数据的返回
             log.error("保存面试记录失败，sessionId: {}, 错误: {}", sessionId, e.getMessage(), e);
         }
         
         return Results.success(radarChart);
     }
     
     /**
       * 神态评分接口
       */
     @PostMapping("/sessions/{sessionId}/demeanor-evaluation")
     public Result<String> evaluateDemeanor(
             @RequestPart("userPhoto") MultipartFile userPhoto,
             @RequestParam("agentId") Long agentId,
             @RequestParam("sessionId") String sessionId,
             HttpServletRequest request) {
              // 从token中获取用户名
              String username = saTokenUtil.getUsernameFromRequest(request);
              
              // 构建请求DTO
              DemeanorEvaluationReqDTO reqDTO = new DemeanorEvaluationReqDTO();
              reqDTO.setUserName(username);
              reqDTO.setAgentId(agentId);
              reqDTO.setSessionId(sessionId);
              reqDTO.setUserPhoto(userPhoto);
              
              // 调用智能体进行神态评分
              String result = agentMessageService.evaluateDemeanor(reqDTO);
              return Results.success(result);
     }

     /**
      * 保存面试记录
      */
     @PostMapping("/interview/record")
     public Result<Void> saveInterviewRecord(
             @RequestBody InterviewRecordSaveReqDTO requestParam,
             HttpServletRequest request) {
         // 从token中获取用户名
         String username = saTokenUtil.getUsernameFromRequest(request);
         
         // 设置用户名
         requestParam.setUsername(username);
         
         interviewRecordService.saveInterviewRecord(requestParam.getSessionId(), requestParam);
         return Results.success();
     }

     /**
      * 分页查询面试记录
      */
     @GetMapping("/interview/records")
     public Result<IPage<InterviewRecordRespDTO>> pageInterviewRecords(
             InterviewRecordPageReqDTO requestParam,
             HttpServletRequest request) {
         // 从token中获取用户名
         String username = saTokenUtil.getUsernameFromRequest(request);


         return Results.success(interviewRecordService.pageInterviewRecords(username,requestParam));
     }

     /**
      * 根据会话ID获取面试记录
      */
     @GetMapping("/interview/record/{sessionId}")
     public Result<InterviewRecordRespDTO> getInterviewRecordBySessionId(@PathVariable String sessionId) {
         return Results.success(interviewRecordService.getBySessionId(sessionId));
     }


     /**
      * 从Redis保存面试记录
      */
     @PostMapping("/interview/record/save-from-redis/{sessionId}")
     public Result<Void> saveInterviewRecordFromRedis(
             @PathVariable String sessionId,
             HttpServletRequest request) {
         interviewRecordService.saveInterviewRecordFromRedis(sessionId);
         return Results.success();
     }


}
