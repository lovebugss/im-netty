package com.itrjp.im.stat.grpc;

import com.itrjp.im.proto.stat.ChannelRequest;
import com.itrjp.im.proto.stat.ChannelStateResponse;
import com.itrjp.im.proto.stat.ChannelStateServiceGrpc;
import com.itrjp.im.stat.service.ChannelStatService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@GrpcService
@RequiredArgsConstructor
public class ChannelStateImpl extends ChannelStateServiceGrpc.ChannelStateServiceImplBase {
    private final ChannelStatService channelStatService;

    @Override
    public void getChannelConnectCount(ChannelRequest request, StreamObserver<ChannelStateResponse> responseObserver) {
        Integer count = channelStatService.getChannelConnectCount(request.getChannelId()).orElse(0);
        responseObserver.onNext(ChannelStateResponse.newBuilder()
                .setCode(200)
                .setCount(count)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getChannelUserCount(ChannelRequest request, StreamObserver<ChannelStateResponse> responseObserver) {
        Integer count = channelStatService.getChannelUserCount(request.getChannelId()).orElse(0);
        responseObserver.onNext(ChannelStateResponse.newBuilder()
                .setCode(200)
                .setCount(count)
                .build());
        responseObserver.onCompleted();
    }
}
