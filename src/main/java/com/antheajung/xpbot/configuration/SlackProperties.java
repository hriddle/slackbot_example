package com.antheajung.xpbot.configuration;

import com.antheajung.xpbot.domain.XpBotRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "slack")
public class SlackProperties {
    public String name;
    public String token;
    public String iconUrl;
    public String messageUrl;
    public String rtmUrl;
    public String generalChannel;

    public String getPostMessageUrl(XpBotRequest xpBotRequest) {
        return messageUrl + "token=" + token + "&channel=" + xpBotRequest.getChannel()
                + "&username=" + name + "&icon_url=" + iconUrl + "&text=" + xpBotRequest.getMessage();
    }

    public String getRtmUrlWithToken() {
        return rtmUrl + token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public String getRtmUrl() {
        return rtmUrl;
    }

    public void setRtmUrl(String rtmUrl) {
        this.rtmUrl = rtmUrl;
    }

    public String getGeneralChannel() {
        return generalChannel;
    }

    public void setGeneralChannel(String generalChannel) {
        this.generalChannel = generalChannel;
    }
}
