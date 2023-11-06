package com.graphql.tutorial.salesservice.configuration;

import com.graphql.tutorial.salesservice.utils.Const;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class DataLoaderConfiguration {

    @Bean(Const.PRODUCT_THREAD_POOL_EXECUTOR_NAME)
    Executor dataLoaderThreadPoolExecutor(){
        return Executors.newCachedThreadPool();
    }
}
