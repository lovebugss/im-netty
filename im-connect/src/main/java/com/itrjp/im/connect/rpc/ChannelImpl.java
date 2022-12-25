package com.itrjp.im.connect.rpc;

import com.itrjp.common.grpc.GrpcService;
import com.itrjp.im.proto.ConnectServiceGrpc;
import com.itrjp.im.proto.PushRequest;
import com.itrjp.im.proto.PushResponse;
import io.grpc.stub.StreamObserver;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/22 17:32
 */
@GrpcService
public class ChannelImpl extends ConnectServiceGrpc.ConnectServiceImplBase {
    @Override
    public void push(PushRequest request, StreamObserver<PushResponse> responseObserver) {
        // 消息广播
        String channelId = request.getChannelId();
        String content = request.getContent();
        PushResponse response = PushResponse.newBuilder()
                .setCode(200)
                .setMessage("success")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
