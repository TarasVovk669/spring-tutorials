package com.virtualthread.less03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class ExternalClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalClient.class);

    private static final String PRODUCT_URL = "http://localhost:7070/sec01/product/%d";
    private static final String RATING_URL = "http://localhost:7070/sec01/rating/%d";


    public static String getProduct(int id) {
        return callExternalService(PRODUCT_URL.formatted(id));
    }

    public static String getRating(int id) {
        return callExternalService(RATING_URL.formatted(id));
    }

    private static String callExternalService(String url) {
        log.info("Calling: {}", url);
        var start = System.currentTimeMillis();
        try (var stream = URI.create(url).toURL().openStream()) {
            var result = new String(stream.readAllBytes());
            var end = System.currentTimeMillis();

            //log.info("Delta time: {} ms", end - start);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
