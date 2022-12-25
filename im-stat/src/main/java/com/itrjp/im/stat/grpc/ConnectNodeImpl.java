package com.itrjp.im.stat.grpc;

import com.itrjp.im.proto.ConnectNodeRequest;
import com.itrjp.im.proto.ConnectNodeResponse;
import com.itrjp.im.proto.ConnectNodeServiceGrpc;
import com.itrjp.im.stat.service.NodeStatService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/25 21:09
 */
@Service
@GrpcService
@RequiredArgsConstructor
public class ConnectNodeImpl extends ConnectNodeServiceGrpc.ConnectNodeServiceImplBase {
    private final NodeStatService connectNodeService;

    @Override
    public void startUp(ConnectNodeRequest request, StreamObserver<ConnectNodeResponse> responseObserver) {
        connectNodeService.start(request.getId());
        responseObserver.onNext(ConnectNodeResponse.newBuilder().setCode(200).build());
        responseObserver.onCompleted();
    }

    @Override
    public void stop(ConnectNodeRequest request, StreamObserver<ConnectNodeResponse> responseObserver) {
        connectNodeService.stop(request.getId());
        responseObserver.onNext(ConnectNodeResponse.newBuilder().setCode(200).build());
        responseObserver.onCompleted();
    }
}
