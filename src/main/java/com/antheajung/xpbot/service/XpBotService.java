package com.antheajung.xpbot.service;

import com.antheajung.xpbot.domain.MessageType;
import com.antheajung.xpbot.domain.XpBotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
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

@Service
public class XpBotService {
    private Logger logger = Logger.getLogger(XpBotService.class.getName());
    private BotClient botClient;

    @Autowired
    public XpBotService(BotClient botClient) {
        this.botClient = botClient;
    }

    public String sendAllNames() {
        return getListOfNames().toString()
                .replace("[", "")
                .replace("]", "");
    }

    public String sendMessageAsBot(XpBotRequest xpBotRequest) {
        botClient.sendMessage(xpBotRequest);
        return xpBotRequest.getMessage();
    }

    public void sendGreeting(XpBotRequest xpBotRequest) {
        xpBotRequest.setMessage(MessageType.GREETING.getMessage());
        botClient.sendMessage(xpBotRequest);
    }

    public void sendHelp(XpBotRequest xpBotRequest) {
        xpBotRequest.setMessage(MessageType.HELP.getMessage());
        botClient.sendMessage(xpBotRequest);
    }

    public void sendRandomComment(XpBotRequest xpBotRequest) {
        xpBotRequest.setMessage(MessageType.RANDOM_EMOJI.getMessage());
        botClient.sendMessage(xpBotRequest);
    }

    public void sendRandomName(XpBotRequest xpBotRequest) {
        List<String> listOfNames = getListOfNames();
        String randomName = listOfNames.get(
                new Random().nextInt(listOfNames.size()));

        xpBotRequest.setMessage(randomName);
        botClient.sendMessage(xpBotRequest);
    }

    public void sendRandomNames(int number, XpBotRequest xpBotRequest) {
        List<String> listOfNames = new ArrayList<>(getListOfNames());

        List<String> chosenNames = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int randomNumber = 0;
            if (number != 1) {
                randomNumber = new Random().nextInt(listOfNames.size());
            }
            String randomName = listOfNames.get(randomNumber);
            listOfNames.remove(randomNumber);
            chosenNames.add(randomName);
        }

        xpBotRequest.setMessage(chosenNames.toString()
                .replace("[", "")
                .replace("]", ""));

        botClient.sendMessage(xpBotRequest);
    }

    private List<String> getListOfNames() {
        List<String> listOfNames = emptyList();

        URL url = getClass().getClassLoader().getResource("names.txt");
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
}
