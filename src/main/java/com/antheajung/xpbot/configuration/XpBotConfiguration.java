package com.antheajung.xpbot.configuration;

import com.antheajung.xpbot.service.BotClient;
import com.antheajung.xpbot.service.XpBotMessageHandler;
import com.antheajung.xpbot.service.XpBotService;
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
    public SlackProperties slackProperties() {
        return new SlackProperties();
    }

    @Bean
    public BotClient botClient() {
        return new BotClient(restTemplate(), slackProperties());
    }

    @Bean
    public XpBotService xpBotService(BotClient botClient) {
        return new XpBotService(botClient);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void makeRTMConnection() {
        new XpBotMessageHandler(xpBotService(botClient()), slackProperties());
    }
}