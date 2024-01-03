package com.grpc.demo.error;

import io.grpc.ServerBuilder;

import java.io.IOException;

public class ErrorServerMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Server starting...");
        var server = ServerBuilder.forPort(8081)
                .addService(new ErrorService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
