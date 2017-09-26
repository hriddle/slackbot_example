package com.antheajung.xpbot.service;

import com.antheajung.xpbot.configuration.SlackConfiguration;
import com.antheajung.xpbot.domain.BotResponse;
import com.antheajung.xpbot.domain.MessageType;
import com.antheajung.xpbot.domain.XpBotResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Field;
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
public class XpBotServiceTest {
    private XpBotService xpBotService;
    private RestTemplate restTemplate;
    private SlackConfiguration slackConfiguration;

    @Before
    public void setUp() throws Exception {
        restTemplate = mock(RestTemplate.class);
        slackConfiguration = mock(SlackConfiguration.class);

        xpBotService = new XpBotService(restTemplate, slackConfiguration);

        when(restTemplate.postForLocation(any(), any())).thenReturn(URI.create(""));
    }

    @Test
    public void sendAllNames_makesAPostRequest() throws IOException {
        xpBotService.sendAllNames();
        verify(restTemplate).postForLocation(anyString(), anyString());
    }

    @Test
    public void sendAllNames_returnsXpBotResponseWithAListOfNamesWhenFileExists() throws IOException, NoSuchFieldException, IllegalAccessException {
        Field defaultFileName = BotResponse.class.getDeclaredField("defaultFileName");
        defaultFileName.set(defaultFileName, "names-test.txt");

        XpBotResponse actualResponse = xpBotService.sendAllNames();

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .message(asList("Anna", "Bob", "Chris", "Danny", "Elliot"))
                .build();

        verify(restTemplate).postForLocation(anyString(), anyString());

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void sendAllNames_returnsXpBotResponseEmptyListWhenFileDoesNotExist() throws IOException, NoSuchFieldException, IllegalAccessException {
        Field defaultFileName = BotResponse.class.getDeclaredField("defaultFileName");
        defaultFileName.set(defaultFileName, "fileDoesNotExist.txt");

        XpBotResponse actualResponse = xpBotService.sendAllNames();

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .message(Collections.emptyList())
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void sendMessageAsBot_makesAPostRequest() throws IOException {
        String message = "This is the message";
        xpBotService.sendMessageAsBot(message);
        verify(restTemplate).postForLocation(slackConfiguration.getUrl(singletonList(message)), "");
    }

    @Test
    public void sendMessageAsBot_returnsXpBotResponseWithTheMessage() throws IOException {
        String message = "This is the message";
        XpBotResponse actualResponse = xpBotService.sendMessageAsBot(message);

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .message(singletonList(message))
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void sendGreeting_makesAPostRequestWithDefaultGreeting() throws IOException {
        xpBotService.sendGreeting();
        verify(restTemplate).postForLocation(
                slackConfiguration.getUrl(singletonList(BotResponse.defaultGreetingMessage)), "");
    }

    @Test
    public void sendGreeting_returnsXpBotResponseWithDefaultGreetingAndMediaTypeGreeting() throws IOException {
        XpBotResponse actualResponse = xpBotService.sendGreeting();

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .message(singletonList(BotResponse.defaultGreetingMessage))
                .type(MessageType.GREETING)
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void sendHelp_makesAPostRequestWithDefaultHelp() throws IOException {
        xpBotService.sendHelp();
        verify(restTemplate).postForLocation(
                slackConfiguration.getUrl(singletonList(BotResponse.defaultHelpMessage)), "");
    }

    @Test
    public void sendHelp_returnsXpBotResponseWithDefaultHelpAndMediaTypeHelp() throws IOException {
        XpBotResponse actualResponse = xpBotService.sendHelp();

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .message(singletonList(BotResponse.defaultHelpMessage))
                .type(MessageType.HELP)
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
    
    @Test
    public void sendRandomNames_returnsXpBotResponseWithSameNumberOfNamesAsRequested() throws IOException, NoSuchFieldException, IllegalAccessException {
        Field defaultFileName = BotResponse.class.getDeclaredField("defaultFileName");
        defaultFileName.set(defaultFileName, "names-test.txt");

        XpBotResponse actualResponse = xpBotService.sendRandomNames(2);

        assertThat(actualResponse).isInstanceOf(XpBotResponse.class);
        assertThat(actualResponse.getMessage()).hasSize(2);
    }
}