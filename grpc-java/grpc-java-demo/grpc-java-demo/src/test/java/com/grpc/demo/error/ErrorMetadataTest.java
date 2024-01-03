package com.grpc.demo.error;

import com.grpc.demo.gen.*;
import io.grpc.*;
import io.grpc.protobuf.ProtoUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.grpc.demo.gen.Error.CONFLICT;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ErrorMetadataTest {

    private ErrorServiceGrpc.ErrorServiceBlockingStub errorServiceBlockingStub;
    private ErrorServiceGrpc.ErrorServiceStub errorServiceStub;

    @BeforeAll
    public void setup() {
        ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext().build();

        this.errorServiceBlockingStub = ErrorServiceGrpc.newBlockingStub(localhost);
        this.errorServiceStub = ErrorServiceGrpc.newStub(localhost);
    }

    @Test
    public void errorSuccessTest() {
        SimpleResponse simpleResponse = errorServiceBlockingStub.doAction(SimpleRequest.newBuilder()
                .setAction(0)
                .build());

        Assertions.assertEquals("00", simpleResponse.getStatusCode());
    }

    @Test
    public void errorFailTest() {
        try {
            SimpleResponse simpleResponse = errorServiceBlockingStub.doAction(SimpleRequest.newBuilder()
                    .setAction(1)
                    .build());
        } catch (StatusRuntimeException e) {
            Metadata metadata = Status.trailersFromThrowable(e);
            ErrorResponse errorResponse = metadata.get(
                    ProtoUtils.keyForProto(ErrorResponse.getDefaultInstance()));
            Assertions.assertEquals(CONFLICT, errorResponse.getError());
        }
    }
}
