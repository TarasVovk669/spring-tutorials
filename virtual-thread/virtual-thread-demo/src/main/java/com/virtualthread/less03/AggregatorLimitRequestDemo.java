package com.virtualthread.less03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorLimitRequestDemo {

    private static final Logger log = LoggerFactory.getLogger(ExternalServiceDemo.class);

    public static void main(String[] args) {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var start = System.currentTimeMillis();
            var futures = IntStream.rangeClosed(1, 50)
                    .mapToObj(i -> executor.submit(() -> getProductInfo(i, executor)))
                    .toList();

            var list = futures.stream().map(f -> {
                        try {
                            return f.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();

            var end = System.currentTimeMillis();
            log.info("Result time: {} ms", end - start);
            log.info("List result: {}", list);
        }
    }


    private static ProductDto getProductInfo(int id, ExecutorService executor) throws ExecutionException, InterruptedException {
        Future<String> product = executor.submit(() -> ExternalClient.getProduct(5));
        Future<String> rating = executor.submit(() -> ExternalClient.getRating(5));

        var productDto = new ProductDto(id, product.get(), rating.get());

        log.info("Result: {}", productDto);

        return productDto;
    }
}
