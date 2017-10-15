package com.antheajung.xpbot.configuration;

import com.antheajung.xpbot.domain.XpBotRequest;
import com.antheajung.xpbot.service.XpBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private SlackProperties slackProperties;

    @Autowired
    private XpBotService xpBotService;

    @Scheduled(cron = "30 5 8 * * MON-FRI", zone = "CST")
    private void sendScheduledStandUpReminder() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .channel(slackProperties.generalChannel)
                .build();

        xpBotService.sendStandUpReminder(xpBotRequest);
    }
}
