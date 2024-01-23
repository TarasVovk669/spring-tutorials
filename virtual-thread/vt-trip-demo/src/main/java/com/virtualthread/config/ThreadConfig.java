package com.virtualthread.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnThreading;
import org.springframework.boot.autoconfigure.thread.Threading;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfig {

  private static final Logger log = LoggerFactory.getLogger(ThreadConfig.class);

  @Bean
  @ConditionalOnThreading(Threading.VIRTUAL)
  public ExecutorService virtualThreadExecutor() {
    log.info("Create virtual executor");
    return Executors.newVirtualThreadPerTaskExecutor();
  }

  @Bean
  @ConditionalOnThreading(Threading.PLATFORM)
  public ExecutorService platformThreadExecutor() {
    log.info("Create platform executor");
    return Executors.newFixedThreadPool(100);
  }
}
