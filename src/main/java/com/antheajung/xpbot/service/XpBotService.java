package com.antheajung.xpbot.service;

import com.antheajung.xpbot.domain.MessageType;
import com.antheajung.xpbot.domain.XpBotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
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

    public void sendStandUpReminder(XpBotRequest xpBotRequest) {
        String currentDay = LocalDate.now().getDayOfWeek().name().toLowerCase();
        String day = currentDay.substring(0, 1).toUpperCase() + currentDay.substring(1, currentDay.length());
        String[] names = getNames(3).split(",");

        String firstUsername = names[0].split(" = ")[1];
        String secondUsername = names[1].split(" = ")[0];
        String thirdUsername = names[2].split(" = ")[0];

        String standUpMessageWithNames = MessageType.STAND_UP.getMessage() + "\n"
                + "<@" + firstUsername + "> " + "You have been chosen to run stand up! Happy " + day + "\n"
                + "Backup: <@" + secondUsername + "> , <@" + thirdUsername + ">";

        xpBotRequest.setMessage(standUpMessageWithNames);
        botClient.sendMessage(xpBotRequest);
    }

    public void sendRandomNames(int number, XpBotRequest xpBotRequest) {
        String chosenNames = getNames(number);
        xpBotRequest.setMessage(chosenNames);
        botClient.sendMessage(xpBotRequest);
    }

    private String getNames(int number) {
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

        return chosenNames.toString()
                .replace("[", "")
                .replace("]", "");
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
