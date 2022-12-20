package com.itrjp.im.api.entity;

import org.hibernate.validator.constraints.Length;

public class Message {

    private MessageType type;
    @Length(max = 100)
    private String content;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
