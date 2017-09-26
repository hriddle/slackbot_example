package com.antheajung.xpbot.client;

import com.antheajung.xpbot.configuration.SlackConfiguration;
import com.antheajung.xpbot.service.XpBotService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ClientEndpoint
public class XpBotMessageHandler {
    private Logger logger = Logger.getLogger(XpBotMessageHandler.class.getName());

    private XpBotService xpBotService;
    private final RestTemplate restTemplate;
    private final SlackConfiguration slackConfiguration;

    private Session session;
    private String directMessageCommand;

    @Autowired
    public XpBotMessageHandler(RestTemplate restTemplate, SlackConfiguration slackConfiguration) {
        this.restTemplate = restTemplate;
        this.slackConfiguration = slackConfiguration;
        this.xpBotService = new XpBotService(restTemplate, slackConfiguration);

        this.directMessageCommand = "@" + slackConfiguration.getName().toLowerCase() + " ";
        this.session = connect();
    }

    @OnMessage
    public void onMessage(String message) throws IOException, JSONException {
        if (!message.isEmpty()) {
            JSONObject messageAsJson = new JSONObject(message);
            if (messageAsJson.has("content")) {
                String contentOfMessage = messageAsJson.get("content").toString();
                if (contentOfMessage.contains(directMessageCommand)) {
                    logger.log(Level.INFO, "** Bot received a direct message **");
                    messageForXpBot(contentOfMessage);
                }
            }
        }
    }

    @OnClose
    public void onClose() throws IOException {
        logger.log(Level.INFO, "** Session closed **");
        this.session.close();
        this.session = connect();
    }

    private Session connect() {
        Map connectionInformation = this.restTemplate.postForObject(
                slackConfiguration.getRtmUrlWithToken(), "", Map.class);

        try {
            URI uri = new URI(connectionInformation.get("url").toString());
            try {
                session = ContainerProvider
                        .getWebSocketContainer()
                        .connectToServer(this, uri);
                logger.log(Level.INFO, "** Made connection **");
                return session;
            } catch (DeploymentException | IOException e) {
                logger.log(Level.WARNING, "** Unable to make connection: WebSocket Error **");
            }
        } catch (URISyntaxException e) {
            logger.log(Level.WARNING, "** Unable to make connection: URI Error **");
        }
        return null;
    }

    private void messageForXpBot(String contentOfMessage) {
        String message = contentOfMessage.toLowerCase();
        if (message.contains(directMessageCommand + "hello")
                || message.contains(directMessageCommand + "hey")
                || message.contains(directMessageCommand + "hi")) {
            logger.log(Level.INFO, "** Sending a greeting **");
            xpBotService.sendGreeting();

        } else if (message.contains(directMessageCommand + "choose")) {
            if(message.contains("choose two ")
                    || message.endsWith("choose two")
                    || message.contains("choose 2 ")
                    || message.endsWith("choose 2")) {
                logger.log(Level.INFO, "** Sending two random names **");
                xpBotService.sendRandomNames(2);
            } else if(message.contains("choose three ")
                    || message.endsWith("choose three")
                    || message.contains("choose 3 ")
                    || message.endsWith("choose 3")) {
                logger.log(Level.INFO, "** Sending three random names **");
                xpBotService.sendRandomNames(3);
            } else {
                logger.log(Level.INFO, "** Sending one random name **");
                xpBotService.sendRandomName();
            }

        } else if (message.contains(directMessageCommand + "help")) {
            logger.log(Level.INFO, "** Sending help instruction **");
            xpBotService.sendHelp();

        } else if (message.contains(directMessageCommand)
                && message.contains("you")
                && message.contains("re")
                && (message.contains("great")
                        || message.contains("awesome")
                        || message.contains("amazing")
                        || message.contains("best"))) {
            logger.log(Level.INFO, "** Sending an emoji **");
            xpBotService.sendRandomComment();
        } else {
            logger.log(Level.INFO, "** Keyword unknown **");
        }
    }
}