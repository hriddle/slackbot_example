package com.antheajung.xpbot.service;

import com.antheajung.xpbot.domain.XpBotRequest;
import com.antheajung.xpbot.domain.MessageType;
import com.antheajung.xpbot.domain.XpBotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BotService {
    private Logger logger = Logger.getLogger(BotService.class.getName());
    private XpBotService xpBotService;

    @Autowired
    public BotService(XpBotService xpBotService) {
        this.xpBotService = xpBotService;
    }

    public XpBotResponse getAllNames() {
        logger.log(Level.INFO, "** Getting all names **");

        try {
            String names = xpBotService.sendAllNames();

            return XpBotResponse.newXpBotResponse()
                    .type(MessageType.LIST_OF_NAMES)
                    .message(names)
                    .build();
        } catch(RuntimeException e) {
            return XpBotResponse.newXpBotResponse()
                    .type(MessageType.ERROR)
                    .build();
        }
    }

    public XpBotResponse sendMessageAsBot(XpBotRequest xpBotRequest) {
        logger.log(Level.INFO, "** Sending a message as a bot **");

        try {
            String messageSent = xpBotService.sendMessageAsBot(xpBotRequest);
            return XpBotResponse.newXpBotResponse()
                    .type(MessageType.MESSAGE)
                    .message(messageSent)
                    .build();
        } catch(RuntimeException e) {
            return XpBotResponse.newXpBotResponse()
                    .type(MessageType.ERROR)
                    .build();
        }
    }
}
