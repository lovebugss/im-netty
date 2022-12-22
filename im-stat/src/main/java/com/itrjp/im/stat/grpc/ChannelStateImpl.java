package com.itrjp.im.stat.grpc;

import com.itrjp.im.proto.service.ChannelStateGrpc;
import com.itrjp.im.proto.service.ChannelStateRpcService;
import com.itrjp.im.stat.service.ChannelStatService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@GrpcService
@RequiredArgsConstructor
public class ChannelStateImpl extends ChannelStateGrpc.ChannelStateImplBase {
    private final ChannelStatService channelStatService;

    @Override
    public void getChannelConnectCount(ChannelStateRpcService.ChannelRequest request, StreamObserver<ChannelStateRpcService.ChannelStateResponse> responseObserver) {
        Integer count = channelStatService.getChannelConnectCount(request.getChannelId()).orElse(0);
        responseObserver.onNext(ChannelStateRpcService.ChannelStateResponse.newBuilder()
                .setCode(200)
                .setCount(count)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getChannelUserCount(ChannelStateRpcService.ChannelRequest request, StreamObserver<ChannelStateRpcService.ChannelStateResponse> responseObserver) {
        Integer count = channelStatService.getChannelUserCount(request.getChannelId()).orElse(0);
        responseObserver.onNext(ChannelStateRpcService.ChannelStateResponse.newBuilder()
                .setCode(200)
                .setCount(count)
                .build());
        responseObserver.onCompleted();
    }
}
