package com.antheajung.xpbot.controller;

import com.antheajung.xpbot.domain.XpBotRequest;
import com.antheajung.xpbot.domain.XpBotResponse;
import com.antheajung.xpbot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
    private BotService botService;

    public BotController(@Autowired BotService botService) {
        this.botService = botService;
    }

    @RequestMapping(value = "/allNames", method = RequestMethod.GET)
    public ResponseEntity listNames() {
        XpBotResponse botResponse = botService.getAllNames();
        return ResponseEntity.ok(botResponse);
    }

    @RequestMapping(value = "/sendMessageAsBot", method = RequestMethod.POST)
    public ResponseEntity sendMessageAsBot(@RequestBody XpBotRequest xpBotRequest) {
        XpBotResponse botResponse = botService.sendMessageAsBot(xpBotRequest);
        return ResponseEntity.ok(botResponse);
    }
}