package com.itrjp.im.dispatcher.grpc;

import com.itrjp.im.dispatcher.entity.ConnectNode;
import com.itrjp.im.dispatcher.service.ConnectNodeService;
import com.itrjp.im.proto.ChannelNodeInfo;
import com.itrjp.im.proto.ChannelNodeRequest;
import com.itrjp.im.proto.ChannelNodeResponse;
import com.itrjp.im.proto.ChannelNodeServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/28 22:25
 */
@Service
@GrpcService
@RequiredArgsConstructor
public class ChannelNodeProvider extends ChannelNodeServiceGrpc.ChannelNodeServiceImplBase {
    private final ConnectNodeService connectNodeService;

    @Override
    public void getBestChannelNode(ChannelNodeRequest request, StreamObserver<ChannelNodeResponse> responseObserver) {
        String channelId = request.getChannelId();
        if (channelId == null) {
            responseObserver.onNext(ChannelNodeResponse.newBuilder()
                    .setCode(400)
                    .setMessage("channelId不能为空")
                    .build());
            responseObserver.onCompleted();
            return;
        }
        List<ConnectNode> availableList = connectNodeService.getAvailableList();
        if (availableList.isEmpty()) {
            responseObserver.onNext(ChannelNodeResponse.newBuilder()
                    .setCode(400)
                    .setMessage("没有可用的服务")
                    .build());
            responseObserver.onCompleted();
            return;
        }
        // 获取最佳节点
        ConnectNode connectNode = availableList.get(0);
        responseObserver.onNext(ChannelNodeResponse.newBuilder()
                .setCode(200)
                .setChannelNode(0, ChannelNodeInfo.newBuilder()
                        .setAddress(connectNode.getAddress())
                        .setPort(connectNode.getWsPort())
                        .setProtocol("ws")
                        .build())
                .build());
    }
}
