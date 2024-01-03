package com.grpc.demo.metadata;

import io.grpc.CallCredentials;
import io.grpc.Metadata;

import java.util.concurrent.Executor;

public class JWTCredentials extends CallCredentials {

    String jwt;

    public JWTCredentials(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(() -> {
            Metadata metadata = new Metadata();
            metadata.put(Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER), jwt);
            applier.apply(metadata);
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        //change in future
    }
}
