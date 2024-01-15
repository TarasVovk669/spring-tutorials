package com.virtualthread.less05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

import static com.virtualthread.utils.FunctionUtil.doTry;

public class ScopedValueDemo {

    private static final Logger log = LoggerFactory.getLogger(ScopedValueDemo.class);
    private static final ScopedValue<String> tokens = ScopedValue.newInstance();

    public static void main(String[] args) {
        Thread.ofVirtual().start(() -> callExternalService());
        Thread.ofVirtual().start(() -> callExternalService());

        doTry(() -> Thread.sleep(Duration.ofSeconds(1)));
    }

    private static String getToken() {
        var token = UUID.randomUUID().toString();
        log.info("Token: {}", token);
        return token;
    }

    private static void callExternalService() {
        var token = getToken();
        log.info("Call another methods with token: {}", token);

        ScopedValue.runWhere(tokens, token, ScopedValueDemo::serviceA);
        ScopedValue.runWhere(tokens, token, ScopedValueDemo::serviceB);

    }

    private static void serviceA() {
        var token = tokens.get();
        log.info("Call service A: {}", token);
    }

    private static void serviceB() {
        var token = tokens.get();
        Thread.ofVirtual().start(() -> ScopedValue.runWhere(tokens, token, () -> {
            log.info("Get token B: {}", tokens.get());
        }));

    }
}
