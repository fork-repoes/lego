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
public class RetryService1Test {
    @Autowired
    private RetryService1 retryService1;

    @BeforeEach
    void setup(){
        retryService1.clean();
    }

    @Test
    public void retry() {
        for (int i = 0; i < 100; i++){
            retryService1.retry(i + 0L);
        }

        Assertions.assertTrue(retryService1.getRetryCount() > 0);
        Assertions.assertTrue(retryService1.getRecoverCount() == 0);
        Assertions.assertTrue(retryService1.getFallbackCount() == 0);
    }

    @Test
    public void fallback() {
        for (int i = 0; i < 100; i++){
            retryService1.fallback(i + 0L);
        }

        Assertions.assertTrue(retryService1.getRetryCount() == 0);
        Assertions.assertTrue(retryService1.getRecoverCount() > 0);
        Assertions.assertTrue(retryService1.getFallbackCount() > 0);
    }
}