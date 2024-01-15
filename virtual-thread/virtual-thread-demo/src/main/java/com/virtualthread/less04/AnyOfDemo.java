package com.virtualthread.less04;

import com.virtualthread.less03.ExternalServiceDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import static com.virtualthread.utils.FunctionUtil.doTry;

public class AnyOfDemo {

    private static final Logger log = LoggerFactory.getLogger(AnyOfDemo.class);


    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var cfA = methodA(executor);
            var cfB = methodB(executor);

            var result = CompletableFuture.anyOf(cfA, cfB).join();
            log.info("Result: {}", result);
        }
    }

    private static CompletableFuture<String> methodA(ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            log.info("A");
            doTry(() -> Thread.sleep(Duration.ofMillis(random)));
            return "MethodA - " + random;
        }, executor);
    }

    private static CompletableFuture<String> methodB(ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            log.info("B");
            doTry(() -> Thread.sleep(Duration.ofMillis(random)));
            return "MethodB - " + random;
        }, executor);
    }
}
