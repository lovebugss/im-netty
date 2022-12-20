package com.itrjp.im.api.entity;

/**
 * SendMessageVo
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/19 21:20
 */
public class SendMessageVo {
    private String messageId;

    public SendMessageVo() {
    }

    public SendMessageVo(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "SendMessageVo{" +
                "messageId='" + messageId + '\'' +
                '}';
    }
}
