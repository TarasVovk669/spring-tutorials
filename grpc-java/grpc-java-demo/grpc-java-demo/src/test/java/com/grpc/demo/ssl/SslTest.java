package com.grpc.demo.ssl;

import com.grpc.demo.gen.ErrorServiceGrpc;
import com.grpc.demo.gen.SimpleRequest;
import com.grpc.demo.gen.SimpleResponse;
import io.grpc.*;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.net.ssl.SSLException;
import java.io.File;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SslTest {

    private ErrorServiceGrpc.ErrorServiceBlockingStub errorServiceBlockingStub;

    @BeforeAll
    public void setup() throws SSLException {
        var sslContext = GrpcSslContexts.forClient()
                .trustManager(
                        new File("path to file")
                ).build();


        ManagedChannel localhost = NettyChannelBuilder
                .forAddress("localhost", 8081)
                .sslContext(sslContext)
                .build();

        this.errorServiceBlockingStub = ErrorServiceGrpc.newBlockingStub(localhost);
    }

    @Test
    public void errorSuccessTest() {
        SimpleResponse simpleResponse = errorServiceBlockingStub.doAction(SimpleRequest.newBuilder()
                .setAction(0)
                .build());

        Assertions.assertEquals("00", simpleResponse.getStatusCode());
    }

}
