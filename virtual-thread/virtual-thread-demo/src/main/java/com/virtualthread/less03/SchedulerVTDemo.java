package com.virtualthread.less03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.virtualthread.utils.FunctionUtil.doTry;

public class SchedulerVTDemo {

    private static final Logger log = LoggerFactory.getLogger(SchedulerVTDemo.class);


    public static void main(String[] args) {
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executorVT = Executors.newVirtualThreadPerTaskExecutor();

        try (scheduler; executorVT) {
            scheduler.scheduleAtFixedRate(() -> {
                executorVT.submit(() -> getProdName(1));
            }, 0, 3, TimeUnit.SECONDS);

            doTry(() -> Thread.sleep(Duration.ofSeconds(15)));
        }


    }

    private static String getProdName(int id) {
        var product = ExternalClient.getProduct(id);
        log.info("Product name: {}", product);
        return product;
    }
}
