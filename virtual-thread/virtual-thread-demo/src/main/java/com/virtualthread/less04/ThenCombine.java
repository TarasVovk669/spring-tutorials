package com.virtualthread.less04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import static com.virtualthread.utils.FunctionUtil.doTry;

public class ThenCombine {

    private static final Logger log = LoggerFactory.getLogger(ThenCombine.class);


    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var cfA = methodA(executor);
            var cfB = methodB(executor);

            var result = cfA.thenCombine(
                    cfB,
                    (Integer::sum)).join();
            log.info("Result: {}", result);
        }
    }

    private static CompletableFuture<Integer> methodA(ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            log.info("MethodA - " + random);
            doTry(() -> Thread.sleep(Duration.ofMillis(random)));
            return random;
        }, executor);
    }

    private static CompletableFuture<Integer> methodB(ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(100, 1000);
            log.info("MethodB - " + random);
            doTry(() -> Thread.sleep(Duration.ofMillis(random)));
            return random;
        }, executor);
    }
}
