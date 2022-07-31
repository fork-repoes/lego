package com.geekhalo.lego.joininmemory.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
abstract class AbstractOrderDetailServiceTest {
    protected abstract OrderDetailService getOrderDetailService();

    @Test
    void getByUserId() {
        StopWatch stopWatch = StopWatch.createStarted();
        List<? extends BaseOrderDetailVO> orderDetailVOS = this.getOrderDetailService().getByUserId(100L);
        stopWatch.stop();
        System.out.println("cost " + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");
        Assertions.assertTrue(CollectionUtils.isNotEmpty(orderDetailVOS));
        Assertions.assertEquals(100, orderDetailVOS.size());
        orderDetailVOS.forEach(orderDetailVO -> {
            Assertions.assertNotNull(orderDetailVO.getOrder());
            Assertions.assertNotNull(orderDetailVO.getUser());
            Assertions.assertNotNull(orderDetailVO.getAddress());
            Assertions.assertNotNull(orderDetailVO.getProduct());
        });
    }
}
