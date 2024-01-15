package com.virtualthread.less02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.virtualthread.utils.FunctionUtil.doTry;

public class ExecutorsDemo {

    private final static Logger log = LoggerFactory.getLogger(ExecutorsDemo.class);

    public static void main(String[] args) {
        //executor(Executors.newSingleThreadExecutor(),10);
        //executor(Executors.newFixedThreadPool(10),40);
        //executor(Executors.newCachedThreadPool(),200);
        //executor(Executors.newCachedThreadPool(),10_000);   -- we will have error OOME(unable to create native thread)
        //executor(Executors.newVirtualThreadPerTaskExecutor(),10_000);
        scheduled();
    }

    private static void scheduled(){
        try(var executor = Executors.newSingleThreadScheduledExecutor()) {
            executor.scheduleAtFixedRate(() -> {
                log.info("Executing Tasks");
            }, 0,1, TimeUnit.SECONDS);

            doTry(() -> Thread.sleep(Duration.ofSeconds(2)));
        }
    }

    private static void executor(ExecutorService executor, int taskCount) {
        try (executor) {
            for (int i = 0; i < taskCount; i++) {
                int j = i;
                executor.submit(() -> task(j));
            }
        }
    }


    private static void task(int i) {
        log.info("Task started with i: {} Thread: {}", i, Thread.currentThread());
        doTry(() -> Thread.sleep(Duration.ofSeconds(2)));
        log.info("Task ended with i: {} Thread: {}", i, Thread.currentThread());

    }
}
