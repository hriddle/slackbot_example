package com.antheajung.xpbot.service;

import com.antheajung.xpbot.domain.XpBotRequest;
import com.antheajung.xpbot.domain.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class XpBotServiceTest {
    private XpBotService xpBotService;
    private BotClient botClient;

    @Before
    public void setUp() throws Exception {
        botClient = mock(BotClient.class);
        xpBotService = new XpBotService(botClient);
    }

    @Test
    public void sendAllNames_returnsAListOfNamesFromAFile() {
        String names = xpBotService.sendAllNames();
        assertThat(names).isEqualTo("TestName1, TestName2, TestName3, TestName4");
    }

    @Test
    public void sendMessageAsBot_callsBotClient() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest().build();

        xpBotService.sendMessageAsBot(xpBotRequest);
        verify(botClient).sendMessage(xpBotRequest);
    }

    @Test
    public void sendMessageAsBot_returnMessageSentToBot() {
        String expectedMessage = "test message";

        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .message(expectedMessage)
                .build();

        String actualMessage = xpBotService.sendMessageAsBot(xpBotRequest);

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void sendGreeting_callsBotClientWithGreetingMessage() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .message(MessageType.GREETING.getMessage())
                .build();

        xpBotService.sendGreeting(xpBotRequest);
        verify(botClient).sendMessage(xpBotRequest);
    }

    @Test
    public void sendHelp_callsBotClientWithHelpMessage() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .message(MessageType.HELP.getMessage())
                .build();

        xpBotService.sendHelp(xpBotRequest);
        verify(botClient).sendMessage(xpBotRequest);
    }

    @Test
    public void sendRandomComment_callsBotClientWithRandomEmojiMessage() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .message(MessageType.RANDOM_EMOJI.getMessage())
                .build();

        xpBotService.sendRandomComment(xpBotRequest);
        verify(botClient).sendMessage(xpBotRequest);
    }

    @Test
    public void sendRandomName_callsBotClientWithARandomName() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .message("random-name")
                .build();

        xpBotService.sendRandomName(xpBotRequest);
        verify(botClient).sendMessage(xpBotRequest);
    }

    @Test
    public void sendRandomNames_callsBotClientWithRandomNames() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .message("random-name-1, random-name-2")
                .build();

        xpBotService.sendRandomNames(2, xpBotRequest);
        verify(botClient).sendMessage(xpBotRequest);
    }

    @Test
    public void sendStandUpReminder_callsBotClientWithStandUpMessage() {
        XpBotRequest xpBotRequest = XpBotRequest.newXpBotRequest()
                .message(MessageType.STAND_UP.getMessage())
                .build();

        xpBotService.sendStandUpReminder(xpBotRequest);
        verify(botClient).sendMessage(xpBotRequest);
    }
}