package com.antheajung.xpbot.service;

import com.antheajung.xpbot.configuration.SlackProperties;
import com.antheajung.xpbot.domain.XpBotRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class BotClientTest {
    private BotClient botClient;

    private RestTemplate restTemplate;
    private SlackProperties slackProperties;

    private XpBotRequest defaultXpBotRequest;
    private RequestEntity defaultRequestEntity;

    @Before
    public void setUp() throws Exception {
        slackProperties = mock(SlackProperties.class);
        restTemplate = mock(RestTemplate.class);

        botClient = new BotClient(restTemplate, slackProperties);

        when(slackProperties.getPostMessageUrl(any())).thenReturn("");

        defaultXpBotRequest = XpBotRequest.newXpBotRequest().build();

        defaultRequestEntity = RequestEntity
                .method(HttpMethod.POST, URI.create(""))
                .body(defaultXpBotRequest);
    }

    @Test
    public void sendMessage_makesAPostRequestWithBotRequest() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .channel("test-channel")
                .message("test-message")
                .build();

        when(slackProperties.getPostMessageUrl(any())).thenReturn("test-slack-message-url");

        botClient.sendMessage(xpBotRequest);

        verify(restTemplate).postForObject("test-slack-message-url", xpBotRequest, Void.class);
    }

    @Test
    public void sendMessage_returnsStatusOKWhenSuccessful() {
        ResponseEntity expectedResponseEntity = ResponseEntity.ok(defaultXpBotRequest.getMessage());

        ResponseEntity actualResponseEntity = botClient.sendMessage(defaultXpBotRequest);

        assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
        assertThat(actualResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}