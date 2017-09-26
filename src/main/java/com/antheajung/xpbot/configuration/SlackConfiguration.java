package com.antheajung.xpbot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SlackConfiguration {
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


    public String getUrl(List<String> message) {
        String messageToSend = getStringFromList(message);

        return messageUrl + "token=" + token + "&channel=" + channel
                + "&icon_url=" + iconUrl + "&text=" + messageToSend;
    }

    public String getRtmUrlWithToken() {
        return rtmUrl + token;
    }

    private String getStringFromList(List<String> message) {
        return String.join(", ", message);
    }

    public String getName() {
        return name;
    }
}
