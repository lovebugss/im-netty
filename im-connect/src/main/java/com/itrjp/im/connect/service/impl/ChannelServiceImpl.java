package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.ChannelService;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.connect.websocket.channel.ChannelsHub;
import com.itrjp.im.proto.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 21:30
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    private final ConsulDiscoveryProperties consulDiscoveryProperties;


    @GrpcClient("im-stat")
    private DispatchServiceGrpc.DispatchServiceBlockingStub dispatchStub;
    @GrpcClient("im-message")
    private MessageServiceGrpc.MessageServiceBlockingStub messageBlockingStub;


    public ChannelServiceImpl(ConsulDiscoveryProperties consulDiscoveryProperties, ChannelsHub channelsHub, Executor executor) {
        this.consulDiscoveryProperties = consulDiscoveryProperties;
    }

    @Override
    public void onOpen(WebSocketClient client) {
        String channelId = client.getChannelId();
        if (channelId == null) {
            return;
        }
        Map<String, List<String>> parameters = client.getParameters();
        String uid = parameters.get("uid").get(0);
        // 通知状态服务
        OnlineRequest request = OnlineRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setSessionId(client.getSession())
                .setNodeId(consulDiscoveryProperties.getInstanceId())
                .build();
        DispatchResponse online = dispatchStub.online(request);
        // 通知消息服务
        EventResponse eventResponse = messageBlockingStub.onEvent(EventRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setType(EventType.join)
                .build());
    }

    @Override
    public void onClose(WebSocketClient client) {
        // 频道ID
        String channelId = client.getChannelId();
        if (channelId == null) {
            return;
        }
        Map<String, List<String>> parameters = client.getParameters();
        String uid = parameters.get("uid").get(0);
        // 通知状态服务
        OfflineRequest request = OfflineRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setSessionId(client.getSession())
                .setNodeId(consulDiscoveryProperties.getInstanceId())
                .build();
        DispatchResponse offline = dispatchStub.offline(request);

        // 通知消息服务
        EventResponse eventResponse = messageBlockingStub.onEvent(EventRequest.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setType(EventType.leave)
                .build());
    }
}
