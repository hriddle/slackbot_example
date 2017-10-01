package com.antheajung.xpbot.service;

import com.antheajung.xpbot.configuration.SlackProperties;
import com.antheajung.xpbot.domain.XpBotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BotClient {
    private Logger logger = Logger.getLogger(BotClient.class.getName());

    private RestTemplate restTemplate;
    private SlackProperties slackProperties;

    @Autowired
    public BotClient(RestTemplate restTemplate, SlackProperties slackProperties) {
        this.restTemplate = restTemplate;
        this.slackProperties = slackProperties;
    }

    public ResponseEntity sendMessage(XpBotRequest xpBotRequest) {
        restTemplate.postForObject(
                slackProperties.getPostMessageUrl(xpBotRequest),
                xpBotRequest,
                Void.class);

        return ResponseEntity.ok().body(xpBotRequest.getMessage());
    }
}
