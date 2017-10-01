package com.antheajung.xpbot.domain;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public final class XpBotUtil {

    public final static String DIRECT_MESSAGE_CMD = "@xpbot";

    public final static String KEYWORD_HI = "hi";
    public final static String KEYWORD_HELLO = "hello";
    public final static String KEYWORD_HEY = "hey";

    public final static String KEYWORD_CHOOSE = "choose";
    public final static String KEYWORD_TWO = "two";
    public final static String KEYWORD_2 = "2";
    public final static String KEYWORD_THREE = "three";
    public final static String KEYWORD_3 = "3";

    public final static String KEYWORD_HELP = "help";

    public final static String KEYWORD_TACO = ":taco:";

    public final static String EMOJI_TACO = ":taco:";
    public final static String EMOJI_PINEAPPLE = ":pineapple:";
    public final static String EMOJI_CHEESE = ":cheese_wedge:";
    public final static String EMOJI_BABYBOTTLE = ":baby_bottle:";
    public final static String EMOJI_ROSE = ":rose:";
    public final static String EMOJI_MONEYBAG = ":moneybag:";
    public final static String EMOJI_BLUEHEART = ":blue_heart:";

    public final static String DEFAULT_STANDUP_MESSAGE = "<!here> It's stand up time!";
    public final static String DEFAULT_GREETING_MESSAGE = "Hello. I'm XpBot";
    public final static String DEFAULT_HELP_MESSAGE =
            "Usage: `@xpbot keyword [keyword options]`\n\n" +
                    "Keywords:\n" +
                    "*choose*\tPick a random name, takes (2 or 3) as an option\n" +
                    "`@xpbot choose 2`";

    private XpBotUtil() {
    }

    public static String getRandomEmoji() {
        List<String> randomEmojis = asList(
                EMOJI_TACO,
                EMOJI_PINEAPPLE,
                EMOJI_CHEESE,
                EMOJI_BABYBOTTLE,
                EMOJI_ROSE,
                EMOJI_MONEYBAG,
                EMOJI_BLUEHEART
        );

        Collections.shuffle(randomEmojis);
        return "Thanks! Have this " + randomEmojis.get(0);
    }
}
