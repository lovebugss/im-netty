package com.itrjp.im.connect.service.impl;

import com.itrjp.im.connect.service.ChannelStateService;

import com.itrjp.im.proto.ChannelRequest;
import com.itrjp.im.proto.ChannelStateResponse;
import com.itrjp.im.proto.ChannelStateServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ChannelStateServiceImpl implements ChannelStateService {

    @GrpcClient("im-stat")
    private ChannelStateServiceGrpc.ChannelStateServiceBlockingStub statServiceBlockingStub;

    @Override
    public int getChannelConnectCount(String channelId) {
        ChannelStateResponse count = statServiceBlockingStub.getChannelConnectCount(ChannelRequest.newBuilder()
                .setChannelId(channelId)
                .build());
        if (count.getCode() == 200) {
            return count.getCount();
        }
        return 0;
    }
}
