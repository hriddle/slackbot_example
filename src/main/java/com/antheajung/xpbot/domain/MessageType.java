package com.antheajung.xpbot.domain;

import static com.antheajung.xpbot.domain.XpBotUtil.DEFAULT_GREETING_MESSAGE;
import static com.antheajung.xpbot.domain.XpBotUtil.DEFAULT_HELP_MESSAGE;
import static com.antheajung.xpbot.domain.XpBotUtil.getRandomEmoji;

public enum MessageType {
    GREETING,
    HELP,
    RANDOM_EMOJI,
    LIST_OF_NAMES,
    MESSAGE,
    ERROR;

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
        }
        return message;
    }
}
