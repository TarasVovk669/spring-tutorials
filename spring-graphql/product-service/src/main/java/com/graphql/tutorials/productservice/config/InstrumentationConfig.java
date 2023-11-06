package com.graphql.tutorials.productservice.config;

import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import org.springframework.context.annotation.Bean;

//@Configuration
public class InstrumentationConfig {

    @Bean
    Instrumentation tracingInstrumentation() {
        return new TracingInstrumentation();
    }

    @Bean
    Instrumentation maxQueryComplexityInstrumentation() {
        return new MaxQueryComplexityInstrumentation(20);
    }

    @Bean
    Instrumentation maxQueryDepthInstrumentation() {
        return new MaxQueryDepthInstrumentation(10);
    }

}
