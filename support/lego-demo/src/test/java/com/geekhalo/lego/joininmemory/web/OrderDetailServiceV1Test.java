package com.geekhalo.lego.joininmemory.web;

import com.geekhalo.lego.DemoApplication;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class OrderDetailServiceV1Test extends AbstractOrderDetailServiceTest{
    @Autowired
    private OrderDetailServiceV1 orderDetailService;

    @Override
    protected OrderDetailService getOrderDetailService() {
        return this.orderDetailService;
    }
}