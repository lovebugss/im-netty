package com.itrjp.im.message.grpc;

import com.itrjp.im.message.service.MessageService;
import com.itrjp.im.proto.service.MessageGrpc;
import com.itrjp.im.proto.service.MessageRpcService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MessageGrpcImpl extends MessageGrpc.MessageImplBase {

    private final MessageService messageService;

    public MessageGrpcImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onMessage(MessageRpcService.MessageRequest request, StreamObserver<MessageRpcService.OnMessageResponse> responseObserver) {
        try {
            // TODO 使用实体
            String messageId = messageService.handlerMessage(request.getChannelId(), request.getUserId(), request.getContent());
            responseObserver.onNext(MessageRpcService.OnMessageResponse.newBuilder()
                    .setMessage("success")
                    .setCode(200)
                    .setMessageId(messageId)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("处理消息异常, message: {}", e.getMessage(), e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void onNotice(MessageRpcService.EventRequest request, StreamObserver<MessageRpcService.OnNoticeResponse> responseObserver) {
        try {
            messageService.handlerJoinLeave(request.getChannelId(), request.getUserId(), request.getType());
            responseObserver.onNext(MessageRpcService.OnNoticeResponse.newBuilder()
                    .setMessage("success")
                    .setCode(200)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
