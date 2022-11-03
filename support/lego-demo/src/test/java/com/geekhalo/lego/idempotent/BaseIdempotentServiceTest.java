package com.geekhalo.lego.idempotent;

import com.geekhalo.lego.common.idempotent.ConcurrentRequestException;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
            boolean error = false;
            try {
                Long valueNew = RandomUtils.nextLong();
                idempotentService.putForError(key, valueNew);
            }catch (Exception e){
                error = true;
            }

            Assertions.assertTrue(error);
            Assertions.assertEquals(value, idempotentService.getValue(key));
        }
    }

    @Test
    void putWaitForResult(){
        String key = String.valueOf(RandomUtils.nextLong());
        Long value = RandomUtils.nextLong();
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

    private void testForConcurrent(Consumer<BaseIdempotentService> consumer){

        Thread thread = new Thread(() -> consumer.accept(getIdempotentService()));
        thread.start();

        consumer.accept(getIdempotentService());
    }

}