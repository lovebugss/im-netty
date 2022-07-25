package com.itrjp.im.message.grpc;

import com.itrjp.im.message.service.MessageService;
import com.itrjp.im.proto.message.MessageGrpc;
import com.itrjp.im.proto.message.MessageProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/25 13:45
 */
@GrpcService
@Service
public class MessageGrpcImpl extends MessageGrpc.MessageImplBase {

    private final MessageService messageService;

    public MessageGrpcImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onMessage(MessageProto.MessageRequest request, StreamObserver<MessageProto.ApiResponse> responseObserver) {
        try {
            messageService.handlerMessage(request.getChannelId(), request.getUserId(), request.getContent());
            responseObserver.onNext(MessageProto.ApiResponse.newBuilder()
                    .setMessage("success")
                    .setCode(200)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }

    @Override
    public void onJoinLeave(MessageProto.JoinLeaveRequest request, StreamObserver<MessageProto.ApiResponse> responseObserver) {
        try {
            messageService.handlerJoinLeave(request.getChannelId(), request.getUserId(), request.getType().getNumber());
            responseObserver.onNext(MessageProto.ApiResponse.newBuilder()
                    .setMessage("success")
                    .setCode(200)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
