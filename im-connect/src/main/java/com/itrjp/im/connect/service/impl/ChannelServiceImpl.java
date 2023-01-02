package com.itrjp.im.connect.service.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.itrjp.im.connect.service.ChannelService;
import com.itrjp.im.connect.websocket.WebSocketClient;
import com.itrjp.im.proto.*;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/24 21:30
 */
@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ConsulDiscoveryProperties consulDiscoveryProperties;


    @GrpcClient("im-stat")
    private DispatchServiceGrpc.DispatchServiceFutureStub dispatchStub;
    @GrpcClient("im-message")
    private MessageServiceGrpc.MessageServiceFutureStub messageBlockingStub;


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
        ListenableFuture<DispatchResponse> online = dispatchStub.online(request);
        online.addListener(() -> {
            try {
                DispatchResponse response = online.get();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }, Runnable::run);
        // 通知消息服务
        ListenableFuture<EventResponse> onEvent = messageBlockingStub.onEvent(Event.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setType(EventType.JOIN)
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
        ListenableFuture<DispatchResponse> offline = dispatchStub.offline(request);

        // 通知消息服务
        ListenableFuture<EventResponse> onEvent = messageBlockingStub.onEvent(Event.newBuilder()
                .setChannelId(channelId)
                .setUserId(uid)
                .setType(EventType.LEAVE)
                .build());
    }
}
