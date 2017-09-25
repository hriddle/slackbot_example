package com.antheajung.xpbot.configuration;

import com.antheajung.xpbot.client.XpBotMessageHandler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
public class XpBotConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SlackConfigurationService slackConfigurationService() {
        return new SlackConfigurationService();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void makeRTMConnection() throws DeploymentException, IOException, URISyntaxException {
        new XpBotMessageHandler(restTemplate(), slackConfigurationService());
    }

}