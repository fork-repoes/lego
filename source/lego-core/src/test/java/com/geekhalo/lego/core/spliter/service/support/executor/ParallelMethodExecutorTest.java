package com.geekhalo.lego.core.spliter.service.support.executor;

import com.geekhalo.lego.core.spliter.service.MethodExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class ParallelMethodExecutorTest {
    private MethodExecutor methodExecutor;

    @BeforeEach
    public void setUp() throws Exception {
        ParallelMethodExecutor methodExecutor = new ParallelMethodExecutor(Executors.newFixedThreadPool(3), 2);
        this.methodExecutor = methodExecutor;
    }

    @Test
    public void execute_null() {
        {
            List<Long> execute = this.methodExecutor.execute(l -> 1L, null);
            Assertions.assertTrue(execute.isEmpty());
        }

        {
            List<Long> execute = this.methodExecutor.execute(l -> l + 1L, new ArrayList<Long>());
            Assertions.assertTrue(execute.isEmpty());
        }
    }

    @Timeout(3)
    @Test
    public void execute_time() {
        {
            List<Long> result = this.methodExecutor.execute(l -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return l + 1L;
            }, Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L ));
            Assertions.assertEquals(7, result.size());
            Assertions.assertEquals(2L, result.get(0).longValue());
            Assertions.assertEquals(3L, result.get(1).longValue());
            Assertions.assertEquals(4L, result.get(2).longValue());
            Assertions.assertEquals(5L, result.get(3).longValue());
            Assertions.assertEquals(6L, result.get(4).longValue());
            Assertions.assertEquals(7L, result.get(5).longValue());
            Assertions.assertEquals(8L, result.get(6).longValue());
        }
    }

    @Test
    public void execute() {
        {
            List<Long> result = this.methodExecutor.execute(l -> l + 1L, Arrays.asList(1L));
            Assertions.assertEquals(1, result.size());
            Assertions.assertEquals(2L, result.get(0).longValue());
        }
        {
            List<Long> result = this.methodExecutor.execute(l -> l + 1L, Arrays.asList(1L, 2L));
            Assertions.assertEquals(2, result.size());
            Assertions.assertEquals(2L, result.get(0).longValue());
            Assertions.assertEquals(3L, result.get(1).longValue());
        }
        {
            List<Long> result = this.methodExecutor.execute(l -> l + 1L, Arrays.asList(1L, 2L, 3L));
            Assertions.assertEquals(3, result.size());
            Assertions.assertEquals(2L, result.get(0).longValue());
            Assertions.assertEquals(3L, result.get(1).longValue());
            Assertions.assertEquals(4L, result.get(2).longValue());
        }
    }

}