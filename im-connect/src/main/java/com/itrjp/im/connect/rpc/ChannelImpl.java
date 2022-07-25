package com.itrjp.im.connect.rpc;

import com.itrjp.common.grpc.GrpcService;
import com.itrjp.im.proto.connect.ConnectGrpc;
import com.itrjp.im.proto.connect.ConnectProto;
import io.grpc.stub.StreamObserver;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/22 17:32
 */
@GrpcService
public class ChannelImpl extends ConnectGrpc.ConnectImplBase {
    @Override
    public void push(ConnectProto.PushRequest request, StreamObserver<ConnectProto.ApiResponse> responseObserver) {
        // 消息广播
        String channelId = request.getChannelId();
        String content = request.getContent();
        ConnectProto.ApiResponse response = ConnectProto.ApiResponse.newBuilder()
                .setCode(200)
                .setMessage("success")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
