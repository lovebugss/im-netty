package com.itrjp.im.api.service;

import com.itrjp.im.api.entity.MessageParam;
import com.itrjp.im.proto.service.MessageGrpc;
import com.itrjp.im.proto.service.MessageRpcService;
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
    private MessageGrpc.MessageBlockingStub messageBlockingStub;

    public String sendMessage(MessageParam param) {
        MessageRpcService.MessageRequest messageRequest = MessageRpcService.MessageRequest.newBuilder()
                .setChannelId(param.getTo())
                .setContent(param.getMessage().getContent())
                .setTimestamp(System.currentTimeMillis())
                .setTo(param.getTo())
                .setFrom(param.getFrom())
                .setUserId(param.getFrom())
                .build();
        // todo 消息投递失败
        MessageRpcService.OnMessageResponse response = messageBlockingStub.onMessage(messageRequest);
        return response.getMessageId();
    }
}
