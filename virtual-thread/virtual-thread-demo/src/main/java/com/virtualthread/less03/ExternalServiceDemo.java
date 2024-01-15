package com.virtualthread.less03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExternalServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(ExternalServiceDemo.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var start = System.currentTimeMillis();

            Future<String> product1 = executor.submit(() -> ExternalClient.getProduct(1));
            Future<String> product2 = executor.submit(() -> ExternalClient.getProduct(2));
            log.info("Product-1 name: {}", product1.get());
            log.info("Product-1 name: {}", product2.get());

            var end = System.currentTimeMillis();
            log.info("Result time: {} ms", end - start);
        }


    }


}
