package com.virtualthread.less01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.virtualthread.utils.FunctionUtil.doTry;

public class Task {
    private final static Logger log = LoggerFactory.getLogger(Task.class);

    public static void doWork(int i) {
        log.info("start working with i: {}", i);

        doTry(() -> Thread.sleep(Duration.ofSeconds(2)));

        log.info("end working with i: {}", i);
    }


}
