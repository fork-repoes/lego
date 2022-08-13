package com.geekhalo.lego.joininmemory.web;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.joininmemory.web.v5.OrderDetailServiceV5;
import com.geekhalo.lego.joininmemory.web.v6.OrderDetailServiceV6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class OrderDetailServiceV6Test extends AbstractOrderDetailServiceTest{
    @Autowired
    private OrderDetailServiceV6 orderDetailService;

    @Override
    protected OrderDetailService getOrderDetailService() {
        return orderDetailService;
    }
}