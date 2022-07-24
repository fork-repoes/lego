package com.geekhalo.lego.core.spliter.service.support.executor;

import com.geekhalo.lego.core.spliter.service.MethodExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
class SerialMethodExecutorTest {
    private final MethodExecutor methodExecutor = new SerialMethodExecutor();


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


    @Test
    public void execute() {
        List<Long> result = this.methodExecutor.execute(l -> l + 1L, Arrays.asList(1L, 2L, 3L));
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(2L, result.get(0).longValue());
        Assertions.assertEquals(3L, result.get(1).longValue());
        Assertions.assertEquals(4L, result.get(2).longValue());
    }

}