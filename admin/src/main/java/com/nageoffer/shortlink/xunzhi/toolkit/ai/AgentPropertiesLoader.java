package com.nageoffer.shortlink.xunzhi.toolkit.ai;

import com.nageoffer.shortlink.xunzhi.dao.entity.AgentPropertiesDO;
import com.nageoffer.shortlink.xunzhi.service.AgentPropertiesService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AgentPropertiesLoader implements CommandLineRunner {

    private final AgentPropertiesService agentPropertiesService;

    @Getter
    private Map<Long, AgentPropertiesDO> agentPropertiesMap = new HashMap<>();

    @Autowired
    public AgentPropertiesLoader(AgentPropertiesService agentPropertiesService) {
        this.agentPropertiesService = agentPropertiesService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<AgentPropertiesDO> Agents = agentPropertiesService.listTop10();
            if (Agents != null) {
                for (AgentPropertiesDO agent : Agents) {
                        agentPropertiesMap.put(agent.getId(), agent);
                    }
                }
                System.out.println("Loaded " + agentPropertiesMap.size() + " agent properties.");

    }
}