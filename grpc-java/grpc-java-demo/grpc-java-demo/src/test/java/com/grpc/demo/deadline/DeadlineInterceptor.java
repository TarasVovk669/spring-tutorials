package com.grpc.demo.deadline;

import io.grpc.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DeadlineInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        var deadline = callOptions.getDeadline();

        if (Objects.isNull(deadline)) {
            deadline = Deadline.after(2, TimeUnit.SECONDS);
        }
        return next.newCall(method, callOptions.withDeadline(deadline));
    }
}
