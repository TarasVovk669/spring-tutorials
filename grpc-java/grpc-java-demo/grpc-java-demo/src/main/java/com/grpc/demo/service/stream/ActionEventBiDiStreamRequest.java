package com.grpc.demo.service.stream;

import com.grpc.demo.gen.ActionEventRequest;
import com.grpc.demo.gen.ActionEventResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.security.SecureRandom;

public class ActionEventBiDiStreamRequest implements StreamObserver<ActionEventRequest> {

    private final StreamObserver<ActionEventResponse> responseStreamObserver;

    public ActionEventBiDiStreamRequest(StreamObserver<ActionEventResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
    }


    @Override
    public void onNext(ActionEventRequest value) {
        System.out.println("val-Serv");
        var random = new SecureRandom();
        for (int i = 0; i < 3; i++) {
            responseStreamObserver.onNext(ActionEventResponse.newBuilder()
                            .setId(random.nextLong(0,500))
                            .setAllow(random.nextBoolean())
                            .setTimeProcess(random.nextLong(0,500))
                    .build());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Error: " + t);
        responseStreamObserver.onError(Status.INTERNAL.asRuntimeException());
    }

    @Override
    public void onCompleted() {
        System.out.println("End stream...");
        responseStreamObserver.onCompleted();
    }
}
