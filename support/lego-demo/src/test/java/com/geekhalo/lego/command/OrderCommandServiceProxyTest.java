package com.geekhalo.lego.command;

import com.geekhalo.lego.DemoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
@Transactional
class OrderCommandServiceProxyTest extends BaseOrderCommandServiceTest{
    @Autowired
    private OrderCommandServiceProxy orderCommandService;

    @Override
    OrderCommandService orderCommandService() {
        return orderCommandService;
    }
}