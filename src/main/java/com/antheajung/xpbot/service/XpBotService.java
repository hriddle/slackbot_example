package com.antheajung.xpbot.service;

import com.antheajung.xpbot.configuration.SlackConfiguration;
import com.antheajung.xpbot.domain.BotResponse;
import com.antheajung.xpbot.domain.MessageType;
import com.antheajung.xpbot.domain.XpBotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Service
public class XpBotService {
    private Logger logger = Logger.getLogger(XpBotService.class.getName());

    private final RestTemplate restTemplate;
    private final SlackConfiguration slackConfiguration;

    @Autowired
    public XpBotService(RestTemplate restTemplate,
                        SlackConfiguration slackConfiguration) {
        this.restTemplate = restTemplate;
        this.slackConfiguration = slackConfiguration;
    }

    public XpBotResponse sendAllNames() {
        return sendAndCreateXpBotResponse(getListOfNames());
    }

    public XpBotResponse sendMessageAsBot(String message) {
        return sendAndCreateXpBotResponse(singletonList(message));
    }

    public XpBotResponse sendGreeting() {
        return sendAndCreateXpBotResponse(MessageType.GREETING);
    }

    public XpBotResponse sendHelp() {
        return sendAndCreateXpBotResponse(MessageType.HELP);
    }

    public XpBotResponse sendRandomComment() {
        return sendAndCreateXpBotResponse(MessageType.RANDOM_EMOJI);
    }

    public XpBotResponse sendRandomName() {
        List<String> listOfNames = getListOfNames();
        String randomName = listOfNames.get(new Random().nextInt(listOfNames.size()));
        return sendAndCreateXpBotResponse(singletonList(randomName));
    }

    public XpBotResponse sendRandomNames(int number) {
        List<String> listOfNames = new ArrayList<>(getListOfNames());

        List<String> chosenNames = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int randomNumber = new Random().nextInt(listOfNames.size());
            String randomName = listOfNames.get(randomNumber);
            listOfNames.remove(randomNumber);
            chosenNames.add(randomName);
        }
        return sendAndCreateXpBotResponse(chosenNames);
    }

    private List<String> getListOfNames() {
        List<String> listOfNames = emptyList();

        URL url = getClass().getClassLoader().getResource(BotResponse.defaultFileName);
        if (url != null) {
            File file = new File(url.getFile());
            if (file.exists()) {
                try {
                    String[] names = new String(Files.readAllBytes(file.toPath()), "UTF-8").split("\n");
                    listOfNames = asList(names);
                    logger.log(Level.INFO, "** Read File: " + listOfNames + " **");
                } catch (IOException ignored) {
                    logger.log(Level.INFO, "** Error reading file **");
                }
            } else {
                logger.log(Level.INFO, "** File does not exist **");
            }
        }
        return listOfNames;
    }

    private void sendMessage(XpBotResponse xpBotResponse) {
        String url = slackConfiguration.getUrl(xpBotResponse.getMessage());
        restTemplate.postForLocation(url, "");
    }

    private XpBotResponse sendAndCreateXpBotResponse(List<String> response) {
        XpBotResponse xpBotResponse = XpBotResponse.newXpBotResponse()
                .message(response).build();
        sendMessage(xpBotResponse);
        return xpBotResponse;
    }

    private XpBotResponse sendAndCreateXpBotResponse(MessageType type) {
        XpBotResponse xpBotResponse = type.getMessage();
        sendMessage(xpBotResponse);
        return xpBotResponse;
    }
}
