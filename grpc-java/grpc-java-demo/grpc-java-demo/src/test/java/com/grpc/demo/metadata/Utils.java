package com.grpc.demo.metadata;

import io.grpc.Metadata;

public class Utils {

    private final static Metadata METADATA = new Metadata();

    static {
        METADATA.put(Metadata.Key.of("auth-token", Metadata.ASCII_STRING_MARSHALLER),
                "auth-token-value-qwwerty123");
    }

    public static Metadata getMetadata(){
        return METADATA;
    }

}
