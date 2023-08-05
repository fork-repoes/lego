package com.geekhalo.lego.core.threadpool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
class GroupSubmitAndReturnServiceTest {
    private BatchTaskRunner batchTaskRunner;
    private GroupSubmitAndReturnService<Long, String> stringGroupSubmitService;

    @BeforeEach
    void setUp() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0 , TimeUnit.SECONDS, new SynchronousQueue<>(), WaitPolicy.getInstance());
        this.batchTaskRunner = new BatchTaskRunner();
        this.stringGroupSubmitService = new GroupSubmitAndReturnService<>("Test",
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
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<SubmitTask> submitTasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            Long number = RandomUtils.nextLong();
            SubmitTask submitTask = new SubmitTask(number);
            submitTasks.add(submitTask);
            TimeUnit.MILLISECONDS.sleep(1L);
            executorService.submit(submitTask);
        }

        TimeUnit.SECONDS.sleep(3);

        List<Long> datas = this.batchTaskRunner.datas;
        Assertions.assertEquals(1000, datas.size());
        submitTasks.forEach(submitTask -> Assertions.assertNotNull(submitTask.result));
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

    class SubmitTask implements Runnable{
        private final Long value;
        private String result;
        SubmitTask(Long value) {
            this.value = value;
        }

        @SneakyThrows
        @Override
        public void run() {
            this.result = stringGroupSubmitService.submitTask(this.value).get();
        }
    }

    class BatchTaskRunner implements Function<List<Long>, List<Pair<Long, String>>> {
        private final List<Long> datas = Lists.newArrayList();
        private int i = 0;

        @Override
        public List<Pair<Long,String>> apply(List<Long> longs) {
            log.info("Batch {}, size {}", ++i, longs.size());
            datas.addAll(longs);
            log.info("deal {}", longs);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return longs.stream().
                    map(l -> ImmutablePair.of(l, String.valueOf(l)))
                    .collect(Collectors.toList());
        }
    }
}