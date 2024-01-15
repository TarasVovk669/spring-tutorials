package com.virtualthread.less05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

import static com.virtualthread.utils.FunctionUtil.doTry;

public class StructuredTaskScopeDemo {

    private static final Logger log = LoggerFactory.getLogger(ScopedValueDemo.class);

    public static void main(String[] args) {
        //successOrFailure();
        //cancelOnFailure();
        firstSuccess();
    }

    private static void successOrFailure() {
        try (var taskScope = new StructuredTaskScope<>()) {
            var task1 = taskScope.fork(StructuredTaskScopeDemo::methodA);
            //var task2 = taskScope.fork(StructuredTaskScopeDemo::methodB);
            var task2 = taskScope.fork(StructuredTaskScopeDemo::methodError);

            taskScope.join(); //wait for results from subtasks
            //taskScope.joinUntil(Instant.now().plusMillis(1500)); // wait some time

            log.info("subtask1 state: {}", task1.state());
            log.info("subtask2 state: {}", task2.state());

            log.info("subtask1 result: {}", task1.get());
            log.info("subtask2 result: {}", task2.get());

        } catch (Exception e) {
            log.error("Error:", e);
        }
    }

    private static void cancelOnFailure() { //cancel all tasks if one of them throws error
        try (var taskScope = new StructuredTaskScope.ShutdownOnFailure()) {
            var task1 = taskScope.fork(StructuredTaskScopeDemo::methodA);

            var task2 = taskScope.fork(StructuredTaskScopeDemo::methodError);

            taskScope.join(); //wait for results from subtasks
            //taskScope.joinUntil(Instant.now().plusMillis(1500)); // wait some time

            log.info("subtask1 state: {}", task1.state());
            log.info("subtask2 state: {}", task2.state());

            log.info("subtask1 result: {}", task1.get());
            log.info("subtask2 result: {}", task2.get());

        } catch (Exception e) {
            log.error("Error:", e);
        }
    }

    private static void firstSuccess() {
        try (var taskScope = new StructuredTaskScope.ShutdownOnSuccess<>()) {
            var task1 = taskScope.fork(StructuredTaskScopeDemo::methodA);
            var task2 = taskScope.fork(StructuredTaskScopeDemo::methodB);


            taskScope.join(); //wait for results from subtasks
            //taskScope.joinUntil(Instant.now().plusMillis(1500)); // wait some time

            log.info("subtask1 state: {}", task1.state());
            log.info("subtask2 state: {}", task2.state());

            log.info("task result: {}", taskScope.result());

        } catch (Exception e) {
            log.error("Error:", e);
        }
    }

    private static String methodA() {
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("MethodA - " + random);
        doTry(() -> Thread.sleep(Duration.ofMillis(1000)));
        return "MethodA - " + random;
    }

    private static String methodB() {
        var random = ThreadLocalRandom.current().nextInt(100, 1000);
        log.info("MethodB - " + random);
        doTry(() -> Thread.sleep(Duration.ofMillis(2000)));
        return "MethodB - " + random;
    }

    private static String methodError() {
        throw new RuntimeException("Error in method!");
    }
}
