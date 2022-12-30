package com.itrjp.im.grpc;

import com.itrjp.im.uid.UidGenerator;
import com.itrjp.im.uid.UidRequest;
import com.itrjp.im.uid.UidResponse;
import com.itrjp.im.uid.UidServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/11/17 22:57
 */
@Service
@GrpcService
public class UidProducer extends UidServiceGrpc.UidServiceImplBase {

    private final UidGenerator uidGenerator;

    public UidProducer(UidGenerator uidGenerator) {
        this.uidGenerator = uidGenerator;
    }

    @Override
    public void genUid(UidRequest request, StreamObserver<UidResponse> responseObserver) {
        long uid = uidGenerator.getUID();
        responseObserver.onNext(UidResponse.newBuilder()
                .setCode(200)
                .setMessage("success")
                .setUid(uid + "")
                .build());
        responseObserver.onCompleted();
    }
}
