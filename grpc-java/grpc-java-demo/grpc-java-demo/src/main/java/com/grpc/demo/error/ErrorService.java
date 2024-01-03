package com.grpc.demo.error;

import com.grpc.demo.gen.Error;
import com.grpc.demo.gen.*;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;

public class ErrorService extends ErrorServiceGrpc.ErrorServiceImplBase {
    @Override
    public void doActionWithOneOf(SimpleRequest request, StreamObserver<SimpleOneOfResponse> responseObserver) {
        System.out.println("do some action with request: " + request);

        if (0 == request.getAction()) {
            responseObserver.onNext(SimpleOneOfResponse.newBuilder()
                    .setStatusCode("00")
                    .build());

        } else {
            if (1 == request.getAction()) {
               responseObserver.onNext(
                       SimpleOneOfResponse.newBuilder()
                               .setError(ErrorResponse.newBuilder()
                                       .setErrorMessage("Conflict in DB")
                                       .setError(Error.CONFLICT)
                                       .build())
                               .build());

            } else if (2 == request.getAction()) {
                responseObserver.onNext(
                        SimpleOneOfResponse.newBuilder()
                                .setError(ErrorResponse.newBuilder()
                                        .setErrorMessage("Internal Server Error")
                                        .setError(Error.INTERNAL)
                                        .build())
                                .build());
            }
        }

        responseObserver.onCompleted();
    }

    @Override
    public void doAction(SimpleRequest request, StreamObserver<SimpleResponse> responseObserver) {
        System.out.println("do some action with request: " + request);

        if (0 == request.getAction()) {
            responseObserver.onNext(SimpleResponse.newBuilder()
                    .setStatusCode("00")
                    .build());
            responseObserver.onCompleted();
        } else {
            Metadata metadata = new Metadata();
            Metadata.Key<ErrorResponse> errorResponseKey = ProtoUtils.keyForProto(ErrorResponse.getDefaultInstance());

            if (1 == request.getAction()) {
                metadata.put(errorResponseKey, ErrorResponse.newBuilder()
                        .setErrorMessage("Conflict in DB")
                        .setError(Error.CONFLICT)
                        .build());
            } else if (2 == request.getAction()) {
                metadata.put(errorResponseKey, ErrorResponse.newBuilder()
                        .setErrorMessage("Internal Server Error")
                        .setError(Error.INTERNAL)
                        .build());
            }

            responseObserver.onError(Status.FAILED_PRECONDITION.asRuntimeException(metadata));
        }

    }
}
