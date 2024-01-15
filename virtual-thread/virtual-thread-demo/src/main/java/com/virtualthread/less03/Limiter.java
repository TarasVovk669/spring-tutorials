package com.virtualthread.less03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

public class Limiter implements AutoCloseable{

    private static final Logger log = LoggerFactory.getLogger(Limiter.class);

    private final ExecutorService executorService;
    private final Semaphore semaphore;
    private final Queue<Callable<?>> queue; // not mandatory. For order Tasks only!!!


    public Limiter(ExecutorService executorService, int threadCount) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(threadCount);
        queue = new ConcurrentLinkedQueue<>();
    }

    public <T> Future<T> submit(Callable<T> callable) {
        queue.add(callable);
        return executorService.submit(() -> {
            try {
                semaphore.acquire();
                return (T) queue.poll().call();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
            }
        });
    }

    @Override
    public void close() throws Exception {
        this.executorService.close();
    }
}
