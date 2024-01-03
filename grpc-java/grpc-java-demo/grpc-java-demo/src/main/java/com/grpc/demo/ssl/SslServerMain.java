package com.grpc.demo.ssl;

import com.grpc.demo.error.ErrorService;

import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;

import java.io.File;
import java.io.IOException;

public class SslServerMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Server starting...");
        SslContext sslContext = GrpcSslContexts.configure(
                SslContextBuilder.forServer(
                        new File("path to file/localhost.crt"), // cert
                        new File("path to file/localhost.pem") //key

                )
        ).build();

        var server = NettyServerBuilder.forPort(8081)
                .sslContext(sslContext)
                .addService(new ErrorService())//only for test
                .build();

        server.start();
        server.awaitTermination();
    }
}
