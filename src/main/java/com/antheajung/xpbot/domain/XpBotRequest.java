package com.antheajung.xpbot.domain;

public class XpBotRequest {
    private String channel;
    private String message;

    public XpBotRequest() {
    }

    private XpBotRequest(Builder builder) {
        this.channel = builder.channel;
        this.message = builder.message;
    }

    public static Builder newXpBotRequest() {
        return new Builder();
    }

    public static final class Builder {
        private String channel;
        private String message;

        private Builder() {
        }

        public XpBotRequest build() {
            return new XpBotRequest(this);
        }

        public Builder channel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XpBotRequest)) return false;

        XpBotRequest that = (XpBotRequest) o;

        if (channel != null ? !channel.equals(that.channel) : that.channel != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = channel != null ? channel.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "XpBotRequest{" +
                "channel='" + channel + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
