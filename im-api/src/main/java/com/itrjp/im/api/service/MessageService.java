package com.itrjp.im.api.service;

import com.itrjp.im.api.entity.MessageParam;
import com.itrjp.im.proto.MessageRequest;
import com.itrjp.im.proto.MessageResponse;
import com.itrjp.im.proto.MessageServiceGrpc;
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
        MessageRequest messageRequest = MessageRequest.newBuilder()
                .setChannelId(param.getTo())
                .setContent(param.getMessage().getContent())
                .setTimestamp(System.currentTimeMillis())
                .setTo(param.getTo())
                .setFrom(param.getFrom())
                .setUserId(param.getFrom())
                .build();
        // todo 消息投递失败
        MessageResponse response = messageBlockingStub.onMessage(messageRequest);
        return response.getMessageId();
    }
}
