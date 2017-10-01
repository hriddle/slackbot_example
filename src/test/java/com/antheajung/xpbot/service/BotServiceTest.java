package com.antheajung.xpbot.service;

import com.antheajung.xpbot.domain.XpBotRequest;
import com.antheajung.xpbot.domain.MessageType;
import com.antheajung.xpbot.domain.XpBotResponse;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BotServiceTest {
    private BotService botService;
    private XpBotService xpBotService;

    @Before
    public void setUp() throws Exception {
        xpBotService = mock(XpBotService.class);
        botService = new BotService(xpBotService);
    }

    @Test
    public void getAllNames_callsXpBotService() {
        botService.getAllNames();

        verify(xpBotService).sendAllNames();
    }

    @Test
    public void getAllNames_returnsXpBotResponseWithAllNames() {
        String names = "test-name1, test-name2";

        when(xpBotService.sendAllNames()).thenReturn(names);

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .type(MessageType.LIST_OF_NAMES)
                .message(names)
                .build();

        XpBotResponse actualResponse = botService.getAllNames();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void getAllNames_returnsXpBotResponseWithErrorMessageTypeWhenXpBotServiceThrowsAnError() {
        when(xpBotService.sendAllNames()).thenThrow(new RuntimeException("BOOM!"));

        XpBotResponse actualResponse = botService.getAllNames();

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .type(MessageType.ERROR)
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void sendMessageAsBot_callsXpBotService() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest().build();

        botService.sendMessageAsBot(xpBotRequest);

        verify(xpBotService).sendMessageAsBot(xpBotRequest);
    }

    @Test
    public void sendMessageAsBot_returnsXpBotResponseWithAllNames() {
        String message = "test message";

        when(xpBotService.sendMessageAsBot(any())).thenReturn(message);

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .type(MessageType.MESSAGE)
                .message(message)
                .build();

        XpBotResponse actualResponse = botService.sendMessageAsBot(
                XpBotRequest.newXpBotRequest()
                        .message(message)
                        .build());

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void sendMessageAsBot_returnsXpBotResponseWithErrorMessageTypeWhenXpBotServiceThrowsAnError() {
        when(xpBotService.sendMessageAsBot(any())).thenThrow(new RuntimeException("BOOM!"));

        XpBotResponse actualResponse = botService.sendMessageAsBot(
                XpBotRequest.newXpBotRequest()
                        .build());

        XpBotResponse expectedResponse = XpBotResponse.newXpBotResponse()
                .type(MessageType.ERROR)
                .build();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}