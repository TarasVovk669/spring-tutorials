package com.graphql.tutorial.graphqlspringtutorialofficialstarter.instrumentation;


import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingInstrumentation extends SimpleInstrumentation {

    @Override
    public @NotNull InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        var start = System.currentTimeMillis();
        var executionId = parameters.getExecutionInput().getExecutionId();
        MDC.put("correlation_id", executionId.toString());
        //log.info("query: {} with variables: {}", parameters.getQuery(), parameters.getVariables());

        return SimpleInstrumentationContext.whenCompleted(((executionResult, throwable) -> {
            var delta = System.currentTimeMillis() - start;

            if (null == throwable) {
                log.info("request is success delta: {}", delta);
            } else {
                log.info("request is fail with delta: {}", delta);
            }

            MDC.clear();
        }));
    }
}
