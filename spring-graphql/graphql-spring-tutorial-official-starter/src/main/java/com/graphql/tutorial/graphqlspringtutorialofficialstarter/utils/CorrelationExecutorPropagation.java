package com.graphql.tutorial.graphqlspringtutorialofficialstarter.utils;

import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;

import java.util.concurrent.Executor;



public class CorrelationExecutorPropagation implements Executor {

    public static final String CORRELATION_ID = "correlation_id";
    private final Executor executor;

    public CorrelationExecutorPropagation(Executor executor) {
        this.executor = executor;
    }

    public static Executor wrap(Executor executor) {
        return new CorrelationExecutorPropagation(executor);
    }

    @Override
    public void execute(@NotNull Runnable command) {
        var correlationId = MDC.get(CORRELATION_ID);

        executor.execute(() -> {
            try {
                MDC.put(CORRELATION_ID, correlationId);
                command.run();
            } finally {
                MDC.remove(CORRELATION_ID);
            }
        });
    }
}
