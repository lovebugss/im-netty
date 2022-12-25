package com.itrjp.im.api.service.impl;

import com.itrjp.common.exception.ChannelNotFoundException;
import com.itrjp.im.api.service.ChannelService;
import com.itrjp.im.proto.ChannelInfo;
import com.itrjp.im.proto.ChannelServiceGrpc;
import com.itrjp.im.proto.GetRequest;
import com.itrjp.im.proto.GetResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {

    @GrpcClient("im-message")
    private ChannelServiceGrpc.ChannelServiceBlockingStub channelBlockingStub;

    @Override
    public void checkChannelId(String channelId) {
        getChannel(channelId)
                .orElseThrow(ChannelNotFoundException::new);
    }

    @Override
    public Optional<ChannelInfo> getChannel(String channelId) {
        GetResponse response = channelBlockingStub.getChannelInfo(GetRequest
                .newBuilder()
                .setChannelId(channelId)
                .build());
        if (response.getCode() == 200) {
            return Optional.of(response.getChannelInfo());
        }
        return Optional.empty();
    }
}
