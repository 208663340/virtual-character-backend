package com.nageoffer.shortlink.admin.dto.resp.agent;

import lombok.Data;

@Data
public class AgentPropertiesRespDTO {

    private Long id;

    private String agentName;

    private String apiSecret;

    private String apiKey;

    private String apiFlowId;

}