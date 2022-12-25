package com.itrjp.im.api.entity;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/19 21:22
 */
public class MessageParam {
    @NotBlank(message = "发送人不能为空")
    String from;

    @NotBlank(message = "接收人不能为空")
    String to;
    @NotNull(message = "消息体不能为空")
    Message message;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
