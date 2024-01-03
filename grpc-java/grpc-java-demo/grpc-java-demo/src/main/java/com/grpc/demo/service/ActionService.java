package com.grpc.demo.service;

import com.grpc.demo.gen.ActionEventAggregateResponse;
import com.grpc.demo.gen.ActionEventRequest;
import com.grpc.demo.gen.ActionEventResponse;
import com.grpc.demo.gen.ActionServiceGrpc;
import com.grpc.demo.service.stream.ActionEventBiDiStreamRequest;
import com.grpc.demo.service.stream.ActionEventStreamRequest;
import io.grpc.stub.StreamObserver;

public class ActionService extends ActionServiceGrpc.ActionServiceImplBase {

    @Override
    public StreamObserver<ActionEventRequest> actions(StreamObserver<ActionEventAggregateResponse> responseObserver) {
        System.out.println("receive request");
        return new ActionEventStreamRequest(responseObserver);
    }

    @Override
    public StreamObserver<ActionEventRequest> actionsStream(StreamObserver<ActionEventResponse> responseObserver) {
        System.out.println("receive request stream");
        return new ActionEventBiDiStreamRequest(responseObserver);
    }
}
