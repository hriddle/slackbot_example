package com.antheajung.xpbot.client;

import com.antheajung.xpbot.configuration.SlackProperties;
import com.antheajung.xpbot.domain.XpBotRequest;
import com.antheajung.xpbot.service.XpBotMessageHandler;
import com.antheajung.xpbot.service.XpBotService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static com.antheajung.xpbot.domain.XpBotUtil.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyZeroInteractions;

//TODO FIX TEST!
@Ignore
@ActiveProfiles("test")
public class XpBotMessageHandlerTest {
    private XpBotService xpBotService;
    private SlackProperties slackProperties;

    private XpBotMessageHandler subject;
    private String defaultChannel = "";

    @Before
    public void setUp() throws Exception {
        xpBotService = mock(XpBotService.class);
        slackProperties = mock(SlackProperties.class);

        subject = new XpBotMessageHandler(xpBotService, slackProperties);

        when(slackProperties.getRtmUrlWithToken()).thenReturn("test");
    }

    @Test
    public void onMessage_callsXpBotServiceIfAMessageContainsDirectMessageCommandAndAKeywordHi()
            throws IOException {

        String messageWithKeyword = createDirectMessageWith(KEYWORD_HI);

        subject.onMessage(messageWithKeyword);

        verify(xpBotService).sendGreeting(XpBotRequest.newXpBotRequest()
                .channel(defaultChannel)
                .message(getContent(messageWithKeyword))
                .build());
    }

    @Test
    public void onMessage_callsXpBotServiceIfAMessageContainsDirectMessageCommandAndAKeywordChoose()
            throws IOException {

        String messageWithKeyword = createDirectMessageWith(KEYWORD_CHOOSE);

        subject.onMessage(messageWithKeyword);

        verify(xpBotService).sendRandomName(XpBotRequest.newXpBotRequest()
                .channel(defaultChannel)
                .message(getContent(messageWithKeyword))
                .build());
    }

    @Test
    public void onMessage_callsXpBotServiceIfAMessageContainsDirectMessageCommandAndAKeywordChooseWithNumber2()
            throws IOException {

        String messageWithKeyword = createDirectMessageWith(KEYWORD_CHOOSE + " " + KEYWORD_TWO);

        subject.onMessage(messageWithKeyword);

        verify(xpBotService).sendRandomNames(2,
                XpBotRequest.newXpBotRequest()
                        .channel(defaultChannel)
                        .message(getContent(messageWithKeyword))
                        .build());
    }

    @Test
    public void onMessage_callsXpBotServiceIfAMessageContainsDirectMessageCommandAndAKeywordChooseWithNumber3()
            throws IOException {

        String messageWithKeyword = createDirectMessageWith(KEYWORD_CHOOSE + " " + KEYWORD_3);

        subject.onMessage(messageWithKeyword);

        verify(xpBotService).sendRandomNames(3,
                XpBotRequest.newXpBotRequest()
                        .channel(defaultChannel)
                        .message(getContent(messageWithKeyword))
                        .build());
    }

    @Test
    public void onMessage_callsXpBotServiceIfAMessageContainsDirectMessageCommandAndAKeywordHelp()
            throws IOException {

        String messageWithKeyword = createDirectMessageWith(KEYWORD_HELP);

        subject.onMessage(messageWithKeyword);

        verify(xpBotService).sendHelp(XpBotRequest.newXpBotRequest()
                .channel(defaultChannel)
                .message(getContent(messageWithKeyword))
                .build());
    }

    @Test
    public void onMessage_callsXpBotServiceIfAMessageContainsDirectMessageCommandAndAKeywordTaco()
            throws IOException {

        String messageWithKeyword = createDirectMessageWith(KEYWORD_TACO);

        subject.onMessage(messageWithKeyword);

        verify(xpBotService).sendRandomComment(XpBotRequest.newXpBotRequest()
                .channel(defaultChannel)
                .message(getContent(messageWithKeyword))
                .build());
    }

    @Test
    public void onMessage_doesNotCallXpBotServiceIfAMessageDoesNotContainsAKnownKeyword()
            throws IOException {
        String messageWithDirectMessageCommand = createDirectMessageWith("Unknown Keyword");
        subject.onMessage(messageWithDirectMessageCommand);

        verifyZeroInteractions(xpBotService);
    }

    @Test
    public void onClose_makeANewConnection() throws IOException {
        subject.onClose();

        verifyZeroInteractions(xpBotService);
    }

    private String createDirectMessageWith(String keyword) {
        return "{\"content\": \"" + DIRECT_MESSAGE_CMD + " "
                + keyword + "\", \"channel\": \"\"}";
    }

    private String getContent(String message) {
        JSONObject messageAsJson = new JSONObject(message);
        return messageAsJson.get("content").toString();
    }
}