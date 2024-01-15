package com.virtualthread.less04;

import com.virtualthread.less03.ExternalClient;
import com.virtualthread.less03.ExternalServiceDemo;
import com.virtualthread.less03.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CompletableFutureDemo {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureDemo.class);

    private final static ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var start = System.currentTimeMillis();

        var futures = IntStream.rangeClosed(1, 51)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> getProductInfo(i)))
                .toList();

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));

        var list = futures
                .stream().map(CompletableFuture::join)
                .toList();
        var end = System.currentTimeMillis();
        log.info("Result time: {} ms", end - start);
        log.info("List result: {}", list);

    }


    private static ProductDto getProductInfo(int id) {
        CompletableFuture<String> product = CompletableFuture
                .supplyAsync(() -> ExternalClient.getProduct(id), executor)
                .exceptionally(v -> null);
        CompletableFuture<String> rating = CompletableFuture
                .supplyAsync(() -> ExternalClient.getRating(id), executor)
                .exceptionally(v -> null);

        var productDto = new ProductDto(id, product.join(), rating.join());
        log.info("Result: {}", productDto);
        return productDto;
    }
}
