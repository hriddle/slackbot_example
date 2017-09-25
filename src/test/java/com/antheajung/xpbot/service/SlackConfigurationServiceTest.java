package com.antheajung.xpbot.service;

import com.antheajung.xpbot.configuration.SlackConfigurationService;
import com.antheajung.xpbot.domain.XpBotResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Profile("test")
public class SlackConfigurationServiceTest {
    private XpBotService xpBotService;
    private RestTemplate restTemplate;
    private SlackConfigurationService slackConfigurationService;

    @Before
    public void setUp() throws Exception {
        restTemplate = mock(RestTemplate.class);
        slackConfigurationService = mock(SlackConfigurationService.class);

        xpBotService = new XpBotService(restTemplate, slackConfigurationService);

        when(restTemplate.postForLocation(any(), any())).thenReturn(URI.create(""));
    }

    @Test
    public void sendAllNames_makesAPostRequest() throws IOException {
        xpBotService.sendAllNamesFrom("listOfNamesTest.txt");
        verify(restTemplate).postForLocation(anyString(), anyString());
    }

    @Test
    public void sendAllNames_returnsListOfNamesWhenFileExists() throws IOException {
        XpBotResponse actualResponse = xpBotService.sendAllNamesFrom("listOfNamesTest.txt");

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .message(asList("Anna", "Bob", "Chris", "Danny", "Elliot"))
                .build();

        verify(restTemplate).postForLocation(anyString(), anyString());

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void sendAllNames_returnsEmptyListWhenFileDoesNotExist() throws IOException {
        XpBotResponse actualResponse = xpBotService.sendAllNamesFrom("fileDoesNotExist.txt");

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .message(Collections.emptyList())
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void sendMessageAsBot_makesAPostRequest() throws IOException {
        String message = "This is the message";
        xpBotService.sendMessageAsBot(message);
        verify(restTemplate).postForLocation(slackConfigurationService.getUrl(singletonList(message)), "");
    }
}