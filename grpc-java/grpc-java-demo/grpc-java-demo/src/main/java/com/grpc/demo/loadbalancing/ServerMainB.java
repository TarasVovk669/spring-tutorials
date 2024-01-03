package com.grpc.demo.loadbalancing;

import com.grpc.demo.service.ActionService;
import com.grpc.demo.service.BankService;
import com.grpc.demo.snakesladdergame.GameService;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServerMainB {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("ServerB starting...");
        var server = ServerBuilder.forPort(8082)
                .addService(new BankService())
                .addService(new ActionService())
                .addService(new GameService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
