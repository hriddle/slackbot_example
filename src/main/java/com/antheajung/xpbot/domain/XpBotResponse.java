package com.antheajung.xpbot.domain;

import java.util.List;

public class XpBotResponse {
    private List<String> message;

    private XpBotResponse(Builder builder) {
        this.message = builder.message;
    }

    public static Builder newXpBotResponse() {
        return new Builder();
    }

    public static final class Builder {
        private List<String> message;

        private Builder() {
        }

        public XpBotResponse build() {
            return new XpBotResponse(this);
        }

        public Builder message(List<String> message) {
            this.message = message;
            return this;
        }
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XpBotResponse)) return false;

        XpBotResponse that = (XpBotResponse) o;

        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "XpBotResponse{" +
                "message=" + message +
                '}';
    }
}
