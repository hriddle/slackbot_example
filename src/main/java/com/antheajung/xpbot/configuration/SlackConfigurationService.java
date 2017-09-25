package com.antheajung.xpbot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlackConfigurationService {
    @Value("${slack.name}")
    public String name;

    @Value("${slack.token}")
    public String token;

    @Value("${slack.channel}")
    public String channel;

    @Value("${slack.emoji}")
    public String emoji;

    @Value("${slack.iconUrl}")
    public String iconUrl;

    @Value("${slack.messageUrl}")
    public String messageUrl;

    @Value("${slack.rtmUrl}")
    public String rtmUrl;

    public static String defaultGreetingMessage = "Hello! I'm XpBot";  //TODO

    public static String defaultFileName = "names.txt";

    public static String defaultHelpMessage =
            "Usage: `@xpbot KEYWORD`\n\n" +
                    "Keywords:\n" +
                    "*choose* \t _Pick a random name_";

    public static String defaultRandomComment =
            "Here is a :taco: for you.";

    public String getUrl(List<String> message) {
        String messageToSend = getStringFromList(message);

        return messageUrl + "token=" + token + "&channel=" + channel
                + "&icon_url=" + iconUrl + "&text=" + messageToSend;
    }

    private String getStringFromList(List<String> message) {
        return String.join(", ", message);
    }

    public String getFileName() {
        return defaultFileName;
    }
}
