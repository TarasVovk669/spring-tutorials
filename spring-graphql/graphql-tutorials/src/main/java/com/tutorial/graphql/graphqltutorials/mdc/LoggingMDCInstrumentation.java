package com.tutorial.graphql.graphqltutorials.mdc;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimplePerformantInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationCreateStateParameters;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class LoggingMDCInstrumentation extends SimplePerformantInstrumentation {

    static class TracingState implements InstrumentationState {
        long startTime;
    }

    @Override
    public @Nullable InstrumentationState createState(InstrumentationCreateStateParameters parameters) {

        return new TracingState();
    }

    @Override
    public @Nullable InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters, InstrumentationState state) {

        TracingState tracingState = (TracingState)state;
        tracingState.startTime=System.currentTimeMillis();

        var corId = UUID.randomUUID().toString();
        MDC.put("correlation_id", corId);
        log.info("Get corr id: {}", corId);

        return super.beginExecution(parameters, tracingState);
    }

    @NotNull
    @Override
    public CompletableFuture<ExecutionResult> instrumentExecutionResult(ExecutionResult executionResult,
                                                                        InstrumentationExecutionParameters parameters,
                                                                        InstrumentationState state) {

        TracingState tracingState = (TracingState) state;
        long totalTime = System.currentTimeMillis() - tracingState.startTime;
        log.info("Total execution time: {}ms", totalTime);

        log.info("clear corr id");
        MDC.clear();

        return super.instrumentExecutionResult(executionResult, parameters,state);
    }
}
