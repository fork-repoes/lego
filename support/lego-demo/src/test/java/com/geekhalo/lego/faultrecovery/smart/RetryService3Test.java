package com.geekhalo.lego.faultrecovery.smart;

import com.geekhalo.lego.DemoApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by taoli on 2022/11/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
public class RetryService3Test {
    @Autowired
    private RetryService3 retryService;

    @BeforeEach
    public void setup(){
        retryService.clean();
    }

    @Test
    public void retry() throws Throwable{
        for (int i = 0; i < 100; i++){
            retryService.retry(i + 0L);
        }

        Assertions.assertTrue(retryService.getRetryCount() > 0);
        Assertions.assertTrue(retryService.getRecoverCount() == 0);
        Assertions.assertTrue(retryService.getFallbackCount() == 0);
    }

    @Test
    public void fallback() throws Throwable{
        for (int i = 0; i < 100; i++){
            retryService.fallback(i + 0L);
        }

        Assertions.assertTrue(retryService.getRetryCount() == 0);
        Assertions.assertTrue(retryService.getRecoverCount() > 0);
        Assertions.assertTrue(retryService.getFallbackCount() > 0);
    }
}