package com.geekhalo.lego.idempotent;

import com.geekhalo.lego.common.idempotent.ConcurrentRequestException;
import com.geekhalo.lego.common.idempotent.RepeatedSubmitException;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by taoli on 2022/11/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
abstract class BaseIdempotentServiceTest {
    abstract BaseIdempotentService getIdempotentService();

    @BeforeEach
    void setUp() {
        this.getIdempotentService().clean();
    }

    @AfterEach
    void tearDown() {
        this.getIdempotentService().clean();
    }

    @Test
    void putForResult() {
        BaseIdempotentService idempotentService = getIdempotentService();
        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        {   // 第一次操作，返回值和最终结果符合预期
            Long result = idempotentService.putForResult(key, value);
            Assertions.assertEquals(value, result);
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

        {   // 第二次操作，返回值和最终结果 与第一一致（直接获取返回值，没有执行业务逻辑）
            Long valueNew = RandomUtils.nextLong();
            Long result = idempotentService.putForResult(key, valueNew);

            Assertions.assertEquals(value, result);
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

    }

    @Test
    void putForError() {
        BaseIdempotentService idempotentService = getIdempotentService();
        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        { // 第一次操作，返回值和最终结果符合预期
            Long result = idempotentService.putForError(key, value);
            Assertions.assertEquals(value, result);
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

        { // 第二次操作，直接抛出异常，结果与第一次一致
            Assertions.assertThrows(RepeatedSubmitException.class, () ->{
                Long valueNew = RandomUtils.nextLong();
                idempotentService.putForError(key, valueNew);
            });
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }
    }

    @Test
    void putExceptionForResult(){
        BaseIdempotentService idempotentService = getIdempotentService();
        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        {   // 第一次操作，抛出异常
            Assertions.assertThrows(IdempotentTestException.class,
                    ()->idempotentService.putExceptionForResult(key, value));
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }

        {   // 第二次操作，返回值和最终结果 与第一一致（直接获取返回值，没有执行业务逻辑）
            Long valueNew = RandomUtils.nextLong();
            Assertions.assertThrows(IdempotentTestException.class,
                    ()->idempotentService.putExceptionForResult(key, valueNew));
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }
    }

    @Test
    public void putExceptionForError(){
        BaseIdempotentService idempotentService = getIdempotentService();
        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        {   // 第一次操作，抛出异常
            Assertions.assertThrows(IdempotentTestException.class,
                    ()->idempotentService.putExceptionForResult(key, value));
        }

        {   // 第二次操作，返回值和最终结果 与第一一致（直接获取返回值，没有执行业务逻辑）
            Long valueNew = RandomUtils.nextLong();
            Assertions.assertThrows(RepeatedSubmitException.class,
                    ()->idempotentService.putExceptionForError(key, valueNew));
        }
    }

    @Test
    void putWaitForResult(){
        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();

        // 主线程抛出 ConcurrentRequestException
        Assertions.assertThrows(ConcurrentRequestException.class, () ->
            testForConcurrent(baseIdempotentService ->
                baseIdempotentService.putWaitForResult(key, value))
        );
    }

    @Test
    public void putWaitForError(){
        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();
        Assertions.assertThrows(ConcurrentRequestException.class, ()->
            testForConcurrent(baseIdempotentService ->
                baseIdempotentService.putWaitForError(key, value))
        );
    }

    private void testForConcurrent(Consumer<BaseIdempotentService> consumer) throws InterruptedException {
        // 启动一个线程执行任务，模拟并发场景
        Thread thread = new Thread(() -> consumer.accept(getIdempotentService()));
        thread.start();

        // 主线程 sleep 1 秒，与异步线程并行执行任务
        TimeUnit.SECONDS.sleep(1);
        consumer.accept(getIdempotentService());
    }

}