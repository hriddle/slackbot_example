package com.antheajung.xpbot.controller;

import com.antheajung.xpbot.configuration.SlackConfigurationService;
import com.antheajung.xpbot.domain.XpBotResponse;
import com.antheajung.xpbot.service.XpBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class XpBotController {
    private Logger logger = Logger.getLogger(XpBotController.class.getName());

    private XpBotService xpBotService;

    public XpBotController(@Autowired XpBotService xpBotService) {
        this.xpBotService = xpBotService;
    }

    @RequestMapping(value = "/listAllNames", method = RequestMethod.GET)
    public XpBotResponse listNames() {
        logger.log(Level.INFO, "** Getting list of Names **");
        return xpBotService.sendAllNamesFrom(SlackConfigurationService.defaultFileName);
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void sendMessageAsBot(@RequestParam(value = "message") String message) {
        logger.log(Level.INFO, "** Sending a message: " + message + " **");
        xpBotService.sendMessageAsBot(message);
    }
}