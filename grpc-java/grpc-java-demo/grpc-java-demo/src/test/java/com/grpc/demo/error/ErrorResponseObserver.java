package com.grpc.demo.error;

import com.grpc.demo.gen.ErrorResponse;
import com.grpc.demo.gen.SimpleResponse;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;

public class ErrorResponseObserver implements StreamObserver<SimpleResponse> {
    private SimpleResponse simpleResponse;

    @Override
    public void onNext(SimpleResponse value) {
        System.out.println(value);
        this.simpleResponse = value;
    }

    @Override
    public void onError(Throwable t) {
        Metadata metadata = Status.trailersFromThrowable(t);
        ErrorResponse errorResponse = metadata.get(
                ProtoUtils.keyForProto(ErrorResponse.getDefaultInstance()));

        System.out.println("Error:" + errorResponse);

    }

    @Override
    public void onCompleted() {
        System.out.println("Finished request");
    }


    public SimpleResponse getSimpleResponse() {
        return simpleResponse;
    }
}
