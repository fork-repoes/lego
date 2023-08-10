package com.geekhalo.lego.core.threadpool;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TTLTest {
    private static final TransmittableThreadLocal<Long> THREAD_LOCAL = new TransmittableThreadLocal<>();
    private ExecutorService executorService;

    @SneakyThrows
    @BeforeEach
    public void init(){
        this.executorService = Executors.newSingleThreadExecutor();
        Thread thread = new Thread(){
            @SneakyThrows
            @Override
            public void run(){
                executorService.submit(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        return null;
                    }
                }).get();
            }
        };

        thread.start();
        thread.join();
    }

    @Test
    public void test1() throws Exception {
        THREAD_LOCAL.set(RandomUtils.nextLong());

        this.executorService.submit(new Task());

        TimeUnit.SECONDS.sleep(100);
    }

    @Test
    public void test2() throws Exception {
        THREAD_LOCAL.set(RandomUtils.nextLong());

        this.executorService.submit(TtlRunnable.get(new Task()));
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

}
