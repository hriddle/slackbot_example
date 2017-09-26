package com.antheajung.xpbot.configuration;

import com.antheajung.xpbot.client.XpBotMessageHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

@Configuration
public class XpBotConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SlackConfiguration slackConfigurationService() {
        return new SlackConfiguration();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void makeRTMConnection() {
        new XpBotMessageHandler(restTemplate(), slackConfigurationService());
    }
}