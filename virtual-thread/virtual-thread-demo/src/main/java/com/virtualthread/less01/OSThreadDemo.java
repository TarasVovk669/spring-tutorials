package com.virtualthread.less01;

import java.util.concurrent.CountDownLatch;

import static com.virtualthread.utils.FunctionUtil.doTry;

public class OSThreadDemo {

    private static final int MAX_PLATFORM = 100;
    private static final int MAX_VIRTUAL = 1_000_000;


    public static void main(String[] args) {
        //createPlatformThreads1();
        createVirtualThreads1();
    }

    public static void createPlatformThreads1() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            var thread = new Thread(() -> Task.doWork(j));
            thread.start();
        }
    }

    public static void createPlatformThreads2() {
        var builder = Thread.ofPlatform().name("vt-common-", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            var thread = builder.unstarted(() -> Task.doWork(j));
            thread.start();
        }
    }

    public static void createPlatformThreads3() {
        var latch = new CountDownLatch(MAX_PLATFORM);
        var builder = Thread.ofPlatform().daemon().name("daemon-", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            var thread = builder.unstarted(() -> {
                Task.doWork(j);
                latch.countDown();
            });
            thread.start();
        }
        doTry(latch::await);
    }

    public static void createVirtualThreads1() {
        var latch = new CountDownLatch(MAX_VIRTUAL);
        var builder = Thread.ofVirtual().name("virtual-", 1);
        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int j = i;
            var thread = builder.unstarted(() -> {
                Task.doWork(j);
                latch.countDown();
            });
            thread.start();
        }
        doTry(latch::await);
    }
}
