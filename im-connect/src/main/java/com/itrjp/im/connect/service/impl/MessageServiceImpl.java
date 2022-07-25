package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.MessageService;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.connect.websocket.channel.ChannelsHub;
import com.itrjp.im.connect.websocket.channel.WebsocketChannel;
import com.itrjp.im.proto.Packet;
import com.itrjp.im.proto.message.MessageGrpc;
import com.itrjp.im.proto.message.MessageProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/21 19:05
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final ChannelsHub channelsHub;

    @GrpcClient("im-message")
    private MessageGrpc.MessageBlockingStub messageBlockingStub;

    public MessageServiceImpl(ChannelsHub channelsHub) {
        this.channelsHub = channelsHub;
    }

    @Override
    public void onMessage(WebSocketClient channel, Packet.Data data) {
        String content = data.getContent();
        // 直接交给im-message 服务进行处理
        Map<String, List<String>> parameters = channel.getParameters();
        MessageProto.MessageRequest messageRequest = MessageProto.MessageRequest.newBuilder()
                .setChannelId(channel.getChannelId())
                .setUserId(parameters.get("uid").get(0))
                .setContent(content)
                .setFrom(parameters.get("uid").get(0))
                .setTo(channel.getChannelId())
                .build();
        MessageProto.ApiResponse apiResponse = messageBlockingStub.onMessage(messageRequest);
    }

    @Override
    public void broadcastMessage(String channelId, String content) {
        WebsocketChannel websocketChannel = channelsHub.get(channelId);
        if (websocketChannel == null) {
            return;
        }
        for (WebSocketClient webSocketClient : websocketChannel.getAllClient()) {
            webSocketClient.sendMessage(Packet.Data.newBuilder()
                    .setContent(content)
                    .build());
        }

    }


    @Override
    public void broadcastJoinLeaveMessage(String channelId, String userId, int type) {

        broadcastMessage(channelId, type + "#" + userId);
    }
}
