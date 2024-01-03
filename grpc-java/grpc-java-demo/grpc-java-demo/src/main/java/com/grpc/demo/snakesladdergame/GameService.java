package com.grpc.demo.snakesladdergame;

import com.grpc.demo.gen.GameServiceGrpc;
import com.grpc.demo.gen.HeroRequest;
import com.grpc.demo.gen.HeroResponse;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {

    @Override
    public StreamObserver<HeroRequest> game(StreamObserver<HeroResponse> responseObserver) {
        return new GameStreamObserver(responseObserver);
    }
}
