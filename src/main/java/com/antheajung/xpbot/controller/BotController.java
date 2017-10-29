package com.antheajung.xpbot.controller;

import com.antheajung.xpbot.domain.XpBotRequest;
import com.antheajung.xpbot.domain.XpBotResponse;
import com.antheajung.xpbot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BotController {
    private BotService botService;

    public BotController(@Autowired BotService botService) {
        this.botService = botService;
    }

    @GetMapping(value = "/allNames")
    public ResponseEntity listNames() {
        XpBotResponse botResponse = botService.getAllNames();
        return ResponseEntity.ok(botResponse);
    }

    @PostMapping(value = "/sendMessageAsBot")
    public ResponseEntity sendMessageAsBot(@RequestBody XpBotRequest xpBotRequest) {
        XpBotResponse botResponse = botService.sendMessageAsBot(xpBotRequest);
        return ResponseEntity.ok(botResponse);
    }
}