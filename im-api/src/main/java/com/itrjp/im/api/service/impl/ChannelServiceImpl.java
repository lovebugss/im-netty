package com.itrjp.im.api.service.impl;

import com.itrjp.common.exception.ChannelNotFoundException;
import com.itrjp.im.api.service.ChannelService;
import com.itrjp.im.proto.ChannelGrpc;
import com.itrjp.im.proto.ChannelProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {

    @GrpcClient("im-message")
    private ChannelGrpc.ChannelBlockingStub channelBlockingStub;

    @Override
    public void checkChannelId(String channelId) {
        getChannel(channelId)
                .orElseThrow(ChannelNotFoundException::new);
    }

    @Override
    public Optional<ChannelProto.ChannelInfo> getChannel(String channelId) {
        ChannelProto.GetResponse response = channelBlockingStub.getChannelInfo(ChannelProto.GetRequest
                .newBuilder()
                .setChannelId(channelId)
                .build());
        if (response.getCode() == 200) {
            return Optional.of(response.getChannelInfo());
        }
        return Optional.empty();
    }
}
