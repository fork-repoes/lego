package com.geekhalo.lego.query;

import com.geekhalo.lego.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
@Transactional
class OrderQueryServiceProxyTest extends BaseOrderQueryServiceTest{
    @Autowired
    private OrderQueryServiceProxy queryService;

    @Override
    OrderQueryService getQueryService() {
        return this.queryService;
    }
}