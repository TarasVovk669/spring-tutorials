package com.grpc.demo.service.stream;

import com.grpc.demo.gen.ActionEventAggregateResponse;
import com.grpc.demo.gen.ActionEventRequest;
import com.grpc.demo.gen.HistoryFlow;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ActionEventStreamRequest implements StreamObserver<ActionEventRequest> {

    private StreamObserver<ActionEventAggregateResponse> responseStreamObserver;
    private final List<ActionEventRequest> requests = new ArrayList<>();

    public ActionEventStreamRequest(StreamObserver<ActionEventAggregateResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
    }


    @Override
    public void onNext(ActionEventRequest value) {
        System.out.println("Add event to list: "+ value);
        requests.add(value);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Error: " + t);
        responseStreamObserver.onError(Status.INTERNAL.asRuntimeException());
    }

    @Override
    public void onCompleted() {
        AtomicInteger order = new AtomicInteger(1);
        Map<String, Long> actions = requests.stream()
                .collect(Collectors.groupingBy(
                        x -> x.getType().name(),
                        Collectors.counting()));
        List<HistoryFlow> flows = requests.stream()
                .map(x -> HistoryFlow.newBuilder()
                        .setOrder(order.getAndIncrement())
                        .setPage(x.getCurrentPage())
                        .build())
                .collect(Collectors.toList());

        var result = ActionEventAggregateResponse.newBuilder()
                .putAllActions(actions)
                .addAllHistoryFlow(flows)
                .build();

        responseStreamObserver.onNext(result);
        responseStreamObserver.onCompleted();
    }
}
