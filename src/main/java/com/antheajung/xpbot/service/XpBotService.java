package com.antheajung.xpbot.service;

import com.antheajung.xpbot.configuration.SlackConfigurationService;
import com.antheajung.xpbot.domain.XpBotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Service
public class XpBotService {
    private Logger logger = Logger.getLogger(XpBotService.class.getName());

    private final RestTemplate restTemplate;
    private final SlackConfigurationService slackConfigurationService;

    @Autowired
    public XpBotService(RestTemplate restTemplate,
                        SlackConfigurationService slackConfigurationService) {
        this.restTemplate = restTemplate;
        this.slackConfigurationService = slackConfigurationService;
    }

    public XpBotResponse sendAllNames() {
        List<String> listOfNamesFrom = getListOfNames();
        List<String> names = emptyList();
        if (!listOfNamesFrom.isEmpty()) {
            names = listOfNamesFrom.subList(1, listOfNamesFrom.size());
        }
        sendMessage(listOfNamesFrom);
        return XpBotResponse.newXpBotResponse().message(names).build();
    }

    public XpBotResponse sendMessageAsBot(String message) {
        return sendAndCreateXpBotResponse(singletonList(message));
    }

    public XpBotResponse sendGreeting() {
        return sendAndCreateXpBotResponse(singletonList(SlackConfigurationService.defaultGreetingMessage));
    }

    public XpBotResponse sendRandomName() {
        List<String> listOfNames = getListOfNames();
        String randomName = listOfNames.get(new Random().nextInt(listOfNames.size()));
        return sendAndCreateXpBotResponse(singletonList(randomName));
    }

    public XpBotResponse sendHelp() {
        return sendAndCreateXpBotResponse(singletonList(SlackConfigurationService.defaultHelpMessage));
    }

    public XpBotResponse sendTaco() {
        return sendAndCreateXpBotResponse(singletonList(SlackConfigurationService.defaultRandomComment));
    }

    private List<String> getListOfNames() {
        List<String> listOfNames = emptyList();

        URL url = getClass().getClassLoader().getResource(slackConfigurationService.getFileName());
        if (url != null) {
            File file = new File(url.getFile());
            if (file.exists()) {
                try {
                    String[] names = ("*Here is a list of names* \n"
                            + new String(Files.readAllBytes(file.toPath()), "UTF-8"))
                            .split("\n");

                    listOfNames = Arrays.asList(names);
                } catch (IOException ignored) {
                    logger.log(Level.INFO, "** Error reading file **");
                }
            } else {
                logger.log(Level.INFO, "** File does not exist **");
            }
        }
        return listOfNames;
    }

    private void sendMessage(List<String> message) {
        String url = slackConfigurationService.getUrl(message);
        restTemplate.postForLocation(url, "");
    }

    private XpBotResponse sendAndCreateXpBotResponse(List<String> response) {
        sendMessage(response);
        return XpBotResponse.newXpBotResponse().message(response).build();
    }
}
