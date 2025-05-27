package com.nageoffer.shortlink.admin.dto.req.agent;

import lombok.Data;

@Data
public class AgentPropertiesReqDTO {

    private Long id;

    private String agentName;

    private String apiSecret;

    private String apiKey;

    private String apiFlowId;

    /**
     * 当前页码
     */
    private Integer pageNum ;

    /**
     * 每页数量
     */
    private Integer pageSize;
}