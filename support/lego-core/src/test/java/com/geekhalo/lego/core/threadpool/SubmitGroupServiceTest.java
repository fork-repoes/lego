package com.geekhalo.lego.core.threadpool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

@Slf4j
class SubmitGroupServiceTest {
    private BatchTaskBuilder batchTaskBuilder;
    private SubmitGroupService<String> stringSubmitGroupService;

    @BeforeEach
    void setUp() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0 , TimeUnit.SECONDS, new SynchronousQueue<>(), WaitPolicy.getInstance());
        this.batchTaskBuilder = new BatchTaskBuilder();
        this.stringSubmitGroupService = new SubmitGroupService<>("Test",
                executorService, this.batchTaskBuilder);
        this.stringSubmitGroupService.start();
    }

    @AfterEach
    void tearDown() {
        this.stringSubmitGroupService.shutdown();
    }

    @SneakyThrows
    @Test
    void submitTask() {
        for (int i = 0; i < 1000; i++){
            Long number = RandomUtils.nextLong();
            this.stringSubmitGroupService.submitTask(String.valueOf(number));
            TimeUnit.MILLISECONDS.sleep(1L);
        }

        TimeUnit.SECONDS.sleep(2);

        List<String> datas = this.batchTaskBuilder.datas;
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

    class BatchTaskBuilder implements Function<List<String>, Runnable>{
        private final List<String> datas = Lists.newArrayList();
        private int i = 0;
        @Override
        public Runnable apply(List<String> strings) {
            log.info("Batch {}, size {}", ++i, strings.size());
            return () ->{
                datas.addAll(strings);
                log.info("deal {}", strings);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }
    }
}