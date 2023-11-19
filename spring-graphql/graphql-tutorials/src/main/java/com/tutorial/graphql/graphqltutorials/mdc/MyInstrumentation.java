package com.tutorial.graphql.graphqltutorials.mdc;

import com.netflix.graphql.dgs.DgsExecutionResult;
import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimplePerformantInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class MyInstrumentation extends SimplePerformantInstrumentation {
    @NotNull
    @Override
    public CompletableFuture<ExecutionResult> instrumentExecutionResult(ExecutionResult executionResult,
                                                                        InstrumentationExecutionParameters parameters,
                                                                        InstrumentationState state) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("myHeader", "hello");

        return super.instrumentExecutionResult(
                DgsExecutionResult.builder().executionResult(executionResult).headers(responseHeaders).build(),
                parameters,
                state
        );
    }
}
