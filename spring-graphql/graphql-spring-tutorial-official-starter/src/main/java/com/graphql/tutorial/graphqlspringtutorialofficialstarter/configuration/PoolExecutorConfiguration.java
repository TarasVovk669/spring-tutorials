package com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.utils.CorrelationExecutorPropagation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class PoolExecutorConfiguration {

    @Bean(name = "SubTaskAsync")
    public Executor executor1() {
        return CorrelationExecutorPropagation.wrap(
                Executors.newFixedThreadPool(3));

    }
}
