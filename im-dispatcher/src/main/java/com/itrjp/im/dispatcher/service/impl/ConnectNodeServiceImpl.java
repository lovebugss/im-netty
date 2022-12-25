package com.itrjp.im.dispatcher.service.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.itrjp.im.dispatcher.entity.ConnectNode;
import com.itrjp.im.dispatcher.service.ConnectNodeService;
import com.itrjp.im.proto.ConnectNodeRequest;
import com.itrjp.im.proto.ConnectNodeResponse;
import com.itrjp.im.proto.ConnectNodeServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/25 15:55
 */
@Slf4j
@Service
public class ConnectNodeServiceImpl implements ConnectNodeService {

    private final Set<ConnectNode> connectNodes = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @GrpcClient("im-stat")
    private ConnectNodeServiceGrpc.ConnectNodeServiceFutureStub futureStub;

    @Override

    public void online(ConnectNode connectNode) {
        log.info("online: {}", connectNode);
        connectNodes.add(connectNode);

        ListenableFuture<ConnectNodeResponse> future = futureStub.startUp(ConnectNodeRequest.newBuilder()
                .setId(connectNode.getId())
                .build());
        future.addListener(() -> {
            try {
                ConnectNodeResponse response = future.get();
                log.info("online response: {}", response);
            } catch (InterruptedException | ExecutionException e) {
                log.error("online error: {}", e.getMessage());
            }
        }, Runnable::run);
    }

    @Override
    public void offline(ConnectNode connectNode) {
        log.info("offline: {}", connectNode);
        connectNodes.remove(connectNode);
        ListenableFuture<ConnectNodeResponse> future = futureStub.stop(ConnectNodeRequest.newBuilder().setId(connectNode.getId()).build());
        future.addListener(() -> {
            try {
                ConnectNodeResponse response = future.get();
                log.info("offline response: {}", response);
            } catch (Exception e) {
                log.error("offline error: {}", e.getMessage());
            }
        }, Runnable::run);
    }

    @Override
    public List<ConnectNode> getAvailableList() {
        return connectNodes.stream().toList();
    }
}
