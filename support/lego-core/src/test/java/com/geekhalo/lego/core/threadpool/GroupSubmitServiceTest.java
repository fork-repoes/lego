package com.geekhalo.lego.core.threadpool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
class GroupSubmitServiceTest {
    private BatchTaskRunner batchTaskRunner;
    private GroupSubmitService<String> stringGroupSubmitService;

    @BeforeEach
    void setUp() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0 , TimeUnit.SECONDS, new SynchronousQueue<>(), WaitPolicy.getInstance());
        this.batchTaskRunner = new BatchTaskRunner();
        this.stringGroupSubmitService = new GroupSubmitService<>("Test",
                executorService, this.batchTaskRunner);
        this.stringGroupSubmitService.start();
    }

    @AfterEach
    void tearDown() {
        this.stringGroupSubmitService.shutdown();
    }

    @SneakyThrows
    @Test
    void submitTask() {
        for (int i = 0; i < 1000; i++){
            Long number = RandomUtils.nextLong();
            this.stringGroupSubmitService.submitTask(String.valueOf(number));
            TimeUnit.MILLISECONDS.sleep(1L);
        }

        TimeUnit.SECONDS.sleep(2);

        List<String> datas = this.batchTaskRunner.datas;
        Assertions.assertEquals(1000, datas.size());

    }

    @Test
    void testSubmitTask() {
    }

    @Test
    void start() {
    }

    @Test
    void shutdown() {
    }

    class BatchTaskRunner implements Function<List<String>, Void>{
        private final List<String> datas = Lists.newArrayList();
        private int i = 0;
        @Override
        public Void apply(List<String> strings) {
            log.info("Batch {}, size {}", ++i, strings.size());
            datas.addAll(strings);
            log.info("deal {}", strings);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}