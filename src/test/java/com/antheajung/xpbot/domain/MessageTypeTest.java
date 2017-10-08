package com.antheajung.xpbot.domain;

import org.junit.Test;

import static com.antheajung.xpbot.domain.XpBotUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageTypeTest {

    @Test
    public void getMessage_returnsCorrectMessageForTypeGreeting() {
        String actualMessage = MessageType.GREETING.getMessage();

        assertThat(actualMessage).isEqualTo(DEFAULT_GREETING_MESSAGE);
    }

    @Test
    public void getMessage_returnsCorrectMessageForTypeHelp() {
        String actualMessage = MessageType.HELP.getMessage();

        assertThat(actualMessage).isEqualTo(DEFAULT_HELP_MESSAGE);
    }

    @Test
    public void getMessage_returnsCorrectMessageForTypeRandomEmoji() {
        String actualMessage = MessageType.RANDOM_EMOJI.getMessage();

        assertThat(actualMessage).contains("Thanks! Have this");
    }

    @Test
    public void getMessage_returnsCorrectMessageForTypeStandUp() {
        String actualMessage = MessageType.STAND_UP.getMessage();

        assertThat(actualMessage).isEqualTo(DEFAULT_STANDUP_MESSAGE);
    }

    @Test
    public void getMessage_returnsEmptyMessageForTypeUnKnown() {
        String actualMessage = MessageType.ERROR.getMessage();

        assertThat(actualMessage).isEmpty();
    }
}
