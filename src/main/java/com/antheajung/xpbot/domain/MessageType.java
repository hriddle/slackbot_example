package com.antheajung.xpbot.domain;

import static com.antheajung.xpbot.domain.XpBotUtil.*;

public enum MessageType {
    GREETING,
    HELP,
    RANDOM_EMOJI,
    LIST_OF_NAMES,
    MESSAGE,
    ERROR,
    STAND_UP;

    public String getMessage() {
        String message = "";
        switch (this) {
            case GREETING:
                message = DEFAULT_GREETING_MESSAGE;
                break;
            case HELP:
                message = DEFAULT_HELP_MESSAGE;
                break;
            case RANDOM_EMOJI:
                message = getRandomEmoji();
                break;
            case STAND_UP:
                message = DEFAULT_STANDUP_MESSAGE;
                break;
        }
        return message;
    }
}
