package com.geekhalo.lego.delay;

import com.geekhalo.lego.DemoApplication;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/9/4.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class DelayServiceTest {
    @Autowired
    private DelayService delayService;

    @BeforeEach
    void setUp() {
        this.delayService.clean();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void delayCancelOrder() throws Exception{
        Long orderId = RandomUtils.nextLong();
        String reason = "超时自动取消";

        this.delayService.delayCancelOrder(orderId, reason);

        Assertions.assertFalse(CollectionUtils.isNotEmpty(this.delayService.getTasks()));

        TimeUnit.SECONDS.sleep(4);

        Assertions.assertFalse(CollectionUtils.isNotEmpty(this.delayService.getTasks()));

        TimeUnit.SECONDS.sleep(6);

        Assertions.assertTrue(CollectionUtils.isNotEmpty(this.delayService.getTasks()));

    }

    @Test
    void delayCancelOrder_DelayTime() throws Exception{
        Long orderId = RandomUtils.nextLong();
        String reason = "超时自动取消";


        this.delayService.delayCancelOrderForTimeout(orderId, reason, 3);

        Assertions.assertFalse(CollectionUtils.isNotEmpty(this.delayService.getTasks()));

        TimeUnit.SECONDS.sleep(9);

        Assertions.assertFalse(CollectionUtils.isNotEmpty(this.delayService.getTasks()));

        TimeUnit.SECONDS.sleep(11);

        Assertions.assertTrue(CollectionUtils.isNotEmpty(this.delayService.getTasks()));

    }


}