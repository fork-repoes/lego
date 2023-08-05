package com.geekhalo.lego.core.threadpool;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TTLTest {
    private static final TransmittableThreadLocal<Long> THREAD_LOCAL = new TransmittableThreadLocal<>();
    private static final ThreadLocal<Long> LONG_THREAD_LOCAL = new ThreadLocal<>();
    private ExecutorService executorService;

    @Test
    public void test1() throws InterruptedException {
        this.executorService = Executors.newSingleThreadExecutor();
        THREAD_LOCAL.set(RandomUtils.nextLong());
        this.executorService.submit(new Task());
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test2() throws InterruptedException {
        this.executorService = Executors.newSingleThreadExecutor();
        LONG_THREAD_LOCAL.set(RandomUtils.nextLong());
        this.executorService.submit(TtlRunnable.get(new Task2()));
        TimeUnit.SECONDS.sleep(1);
    }


    private class Task implements Runnable{

        @Override
        public void run() {
            Long data = THREAD_LOCAL.get();
            System.out.println(data);
            Assertions.assertNotNull(data);
        }
    }

    private class Task2 implements Runnable{

        @Override
        public void run() {
            Long data = LONG_THREAD_LOCAL.get();
            System.out.println(data);
            Assertions.assertNotNull(data);
        }
    }
}
