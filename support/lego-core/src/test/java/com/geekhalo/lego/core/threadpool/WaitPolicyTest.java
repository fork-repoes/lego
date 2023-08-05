package com.geekhalo.lego.core.threadpool;

import lombok.SneakyThrows;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class WaitPolicyTest {
    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        BasicThreadFactory threadFactory = new BasicThreadFactory.Builder()
                .namingPattern("executor")
                .build();
        executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                threadFactory,
                WaitPolicy.getInstance()
                );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void rejectedExecution() {
        List<String> threadNames = new ArrayList<>();
        for (int i = 0; i< 10; i++){
            this.executorService.submit(new Task(threadNames));
        }

        Assertions.assertEquals(10, threadNames.size());
        Assertions.assertEquals(1, new HashSet<>(threadNames).size());
    }

    static class Task implements Runnable{
        private final List<String> names;

        Task(List<String> names) {
            this.names = names;
        }

        @SneakyThrows
        @Override
        public void run() {
            this.names.add(Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(1);
        }
    }


}