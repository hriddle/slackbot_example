package com.antheajung.xpbot.service;

import com.antheajung.xpbot.configuration.SlackProperties;
import com.antheajung.xpbot.domain.XpBotRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.antheajung.xpbot.domain.XpBotUtil.*;

@ClientEndpoint
public class XpBotMessageHandler {
    private Logger logger = Logger.getLogger(XpBotMessageHandler.class.getName());

    private SlackProperties slackProperties;
    private XpBotService xpBotService;
    private Session session;

    @Autowired
    public XpBotMessageHandler(XpBotService xpBotService, SlackProperties slackProperties) {
        this.xpBotService = xpBotService;
        this.slackProperties = slackProperties;
        this.session = connect();

        setStandUpReminder();
    }

    @OnMessage
    public void onMessage(String message) {
        if (!message.isEmpty()) {
            JSONObject messageAsJson = new JSONObject(message);

            if (messageAsJson.has("content")) {
                String contentOfMessage = messageAsJson.get("content").toString();

                if (contentOfMessage.contains(DIRECT_MESSAGE_CMD)) {
                    logger.log(Level.INFO, "** Bot received a direct message **");

                    String channel = messageAsJson.get("channel").toString();

                    XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                            .message(contentOfMessage)
                            .channel(channel)
                            .build();

                    messageForXpBot(xpBotRequest);
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose() throws IOException {
        logger.log(Level.INFO, "** Session closed **");
        if (session != null) {
            session.close();
        }
        session = connect();
    }

    private void messageForXpBot(XpBotRequest xpBotRequest) {
        String message = xpBotRequest.getMessage().toLowerCase();

        if (message.contains(KEYWORD_HELLO)
                || message.contains(KEYWORD_HI)
                || message.contains(KEYWORD_HEY)) {

            logger.log(Level.INFO, "** Sending a greeting **");
            xpBotService.sendGreeting(xpBotRequest);

        } else if (message.contains(KEYWORD_CHOOSE)) {
            if (message.contains(KEYWORD_CHOOSE + " " + KEYWORD_TWO + " ")
                    || message.endsWith(KEYWORD_CHOOSE + " " + KEYWORD_TWO)
                    || message.contains(KEYWORD_CHOOSE + " " + KEYWORD_2 + " ")
                    || message.endsWith(KEYWORD_CHOOSE + " " + KEYWORD_2)) {

                logger.log(Level.INFO, "** Sending two random names **");
                xpBotService.sendRandomNames(2, xpBotRequest);

            } else if (message.contains(KEYWORD_CHOOSE + " " + KEYWORD_THREE + " ")
                    || message.endsWith(KEYWORD_CHOOSE + " " + KEYWORD_THREE)
                    || message.contains(KEYWORD_CHOOSE + " " + KEYWORD_3 + " ")
                    || message.endsWith(KEYWORD_CHOOSE + " " + KEYWORD_3)) {

                logger.log(Level.INFO, "** Sending three random names **");
                xpBotService.sendRandomNames(3, xpBotRequest);

            } else {

                logger.log(Level.INFO, "** Sending one random name **");
                xpBotService.sendRandomName(xpBotRequest);
            }

        } else if (message.contains(KEYWORD_HELP)) {

            logger.log(Level.INFO, "** Sending help instruction **");
            xpBotService.sendHelp(xpBotRequest);

        } else if (message.contains(KEYWORD_TACO)) {

            logger.log(Level.INFO, "** Sending an emoji **");
            xpBotService.sendRandomComment(xpBotRequest);

        } else {
            logger.log(Level.INFO, "** Keyword unknown **");
        }
    }

    private void setStandUpReminder() {
        Calendar standUpTime = Calendar.getInstance();
        standUpTime.set(Calendar.HOUR_OF_DAY, 8);
        standUpTime.set(Calendar.MINUTE, 5);
        standUpTime.set(Calendar.SECOND, 30);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Calendar calendar = Calendar.getInstance();
                               int day = calendar.get(Calendar.DAY_OF_WEEK);

                               if (day > 0 && day < 6) createStandUpReminder();
                           }
                       },
                standUpTime.getTime(),
                TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }

    private void createStandUpReminder() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .channel(slackProperties.generalChannel)
                .build();

        logger.log(Level.INFO, "** Sending stand up reminder **");
        xpBotService.sendStandUpReminder(xpBotRequest);
    }

    private Session connect() {
        RestTemplate restTemplate = new RestTemplate();
        String url = slackProperties.getRtmUrlWithToken();
        Map connectionInformation = restTemplate.postForObject(url, "", Map.class);

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
}