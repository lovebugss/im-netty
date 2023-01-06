package com.itrjp.im.api.service;

import com.itrjp.im.api.entity.MessageParam;
import com.itrjp.im.proto.message.Message;
import com.itrjp.im.proto.message.MessageResponse;
import com.itrjp.im.proto.message.MessageServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/19 21:33
 */
@Service
public class MessageService {
    @GrpcClient("im-message")
    private MessageServiceGrpc.MessageServiceBlockingStub messageBlockingStub;

    public String sendMessage(MessageParam param) {
        Message messageRequest = Message.newBuilder()
                .setChannelId(param.getTo())
                .setContent(param.getMessage().getContent())
                .setTimestamp(System.currentTimeMillis())
                .setUserId(param.getFrom())
                .build();
        // todo 消息投递失败
        MessageResponse response = messageBlockingStub.onMessage(messageRequest);
        return response.getMessageId();
    }
}
