package com.antheajung.xpbot.client;

import com.antheajung.xpbot.configuration.SlackConfigurationService;
import com.antheajung.xpbot.service.XpBotService;
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
    private final SlackConfigurationService slackConfigurationService;

    @Autowired
    public XpBotMessageHandler(
            RestTemplate restTemplate,
            SlackConfigurationService slackConfigurationService
    ) throws URISyntaxException, DeploymentException, IOException {
        this.restTemplate = restTemplate;
        this.slackConfigurationService = slackConfigurationService;
        this.xpBotService = new XpBotService(restTemplate, slackConfigurationService);

        connect();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        if (!message.isEmpty()) {
            JSONObject messageAsJson = new JSONObject(message);
            if (messageAsJson.has("content")) {
                String contentOfMessage = messageAsJson.get("content").toString();
                if (contentOfMessage.contains("@xpbot")) { //TODO
                    logger.log(Level.INFO, "** Message contains @xpbot **");
                    messageForXpBot(contentOfMessage);
                }
            }
        }
    }

    private void connect() throws DeploymentException, IOException, URISyntaxException {
        Map connectionInformation = this.restTemplate.postForObject(
                slackConfigurationService.rtmUrl + slackConfigurationService.token,
                "", Map.class);

        URI uri = new URI(connectionInformation.get("url").toString());

        ContainerProvider
                .getWebSocketContainer()
                .connectToServer(this, uri);

        logger.log(Level.INFO, "** Made connection **");
    }

    private void messageForXpBot(String contentOfMessage) {
        String message = contentOfMessage.toLowerCase();

        if (message.contains("hello") || message.contains("hey") || message.contains("hi")) {
            logger.log(Level.INFO, "** Sending a greeting **");
            xpBotService.sendGreeting();

        } else if (message.contains("choose")) {
            logger.log(Level.INFO, "** Sending a random name **");
            xpBotService.sendRandomName();

        } else if (message.contains("help")) {
            logger.log(Level.INFO, "** Sending help instruction **");
            xpBotService.sendHelp();

        } else {
            logger.log(Level.INFO, "** Sending taco **");
            xpBotService.sendTaco();
        }
    }
}