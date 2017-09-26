package com.antheajung.xpbot.domain;

import java.util.Collections;
import java.util.List;

public enum MessageType {
    GREETING,
    HELP,
    RANDOM_EMOJI;

    public XpBotResponse getMessage() {
        List<String> message = Collections.emptyList();
        switch (this) {
            case GREETING:
                message = Collections.singletonList(BotResponse.defaultGreetingMessage);
                break;
            case HELP:
                message = Collections.singletonList(BotResponse.defaultHelpMessage);
                break;
            case RANDOM_EMOJI:
                message = Collections.singletonList(BotResponse.getRandomEmoji());
                break;
        }

        return XpBotResponse.newXpBotResponse()
                .message(message)
                .type(this)
                .build();
    }
}
