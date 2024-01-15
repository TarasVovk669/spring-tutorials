package com.virtualthread.less03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class LimitSemaphoreVTDemo {

    private static final Logger log = LoggerFactory.getLogger(LimitSemaphoreVTDemo.class);

    public static void main(String[] args) throws Exception {
        execute(
                new Limiter(Executors.newVirtualThreadPerTaskExecutor(), 3),
                30);
    }

    private static void execute(Limiter limiter, int taskCount) throws Exception {
        try (limiter) {
            for (int i = 1; i < taskCount; i++) {
                int finalI = i;
                limiter.submit(() -> {
                    var prodName = getProductName(finalI);
                    log.info("Product name: {}", prodName);
                    return prodName;
                });
            }
        }
    }

    private static String getProductName(int id) {
        return ExternalClient.getProduct(id);
    }


}
