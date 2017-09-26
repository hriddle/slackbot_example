package com.antheajung.xpbot.domain;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class BotResponse {
    public static String defaultGreetingMessage = "Hello! I'm XpBot";

    public static String defaultFileName = "names.txt";

    public static String defaultHelpMessage =
            "Usage: `@xpbot keyword [keyword options]`\n\n" +
                    "Keywords:\n" +
                    "*choose*\tPick a random name, takes (2 or 3) as an option\n" +
                    "`@xpbot choose 2`";

    public static String taco = ":taco:";
    public static String pineapple = ":pineapple:";
    public static String cheese = ":cheese_wedge:";
    public static String babybottle = ":baby_bottle:";
    public static String rose = ":rose:";
    public static String moneybag = ":moneybag:";
    public static String blueheart = ":blue_heart:";

    public static String getRandomEmoji() {
        List<String> randomEmojis = asList(taco, pineapple, cheese, babybottle, rose, moneybag, blueheart);
        Collections.shuffle(randomEmojis);
        return "Thanks! Here is a " + randomEmojis.get(0);
    }
}
