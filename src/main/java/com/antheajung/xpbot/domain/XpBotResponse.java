package com.antheajung.xpbot.domain;

import java.util.List;

public class XpBotResponse extends BotResponse {
    private List<String> message;
    private MessageType type;

    private XpBotResponse(Builder builder) {
        this.message = builder.message;
        this.type = builder.type;
    }

    public static Builder newXpBotResponse() {
        return new Builder();
    }

    public static final class Builder {
        private List<String> message;
        private MessageType type;

        private Builder() {
        }

        public XpBotResponse build() {
            return new XpBotResponse(this);
        }

        public Builder message(List<String> message) {
            this.message = message;
            return this;
        }

        public Builder type(MessageType type) {
            this.type = type;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XpBotResponse)) return false;

        XpBotResponse that = (XpBotResponse) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "XpBotResponse{" +
                "message=" + message +
                ", type=" + type +
                '}';
    }

    public List<String> getMessage() {
        return message;
    }

    public MessageType getType() {
        return type;
    }
}
