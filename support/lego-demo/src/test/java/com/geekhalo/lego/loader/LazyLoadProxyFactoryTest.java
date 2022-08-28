package com.geekhalo.lego.loader;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
@Slf4j
class LazyLoadProxyFactoryTest {
    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createProxyForV1() {
        CreateOrderCmd cmd = new CreateOrderCmd();
        cmd.setUserId(1L);
        cmd.setProductId(2L);
        cmd.setCount(2);

        CreateOrderContextV1 context = new CreateOrderContextV1();
        context.setCmd(cmd);
        checkOrderContext(context);

    }

    @Test
    void createProxyForV2() {
        CreateOrderCmd cmd = new CreateOrderCmd();
        cmd.setUserId(1L);
        cmd.setProductId(2L);
        cmd.setCount(2);

        CreateOrderContextV2 context = new CreateOrderContextV2();
        context.setCmd(cmd);
        checkOrderContext(context);

    }

    private void checkOrderContext(CreateOrderContext context) {
        CreateOrderContext proxyContext = this.lazyLoadProxyFactory.createProxyFor(context);

        Assertions.assertNotNull(proxyContext);
        log.info("Get Command");
        Assertions.assertNotNull(proxyContext.getCmd());

        log.info("Get Price");
        Assertions.assertNotNull(proxyContext.getPrice());

        log.info("Get Stock");
        Assertions.assertNotNull(proxyContext.getStock());

        log.info("Get Default Address");
        Assertions.assertNotNull(proxyContext.getDefAddress());

        log.info("Get Product");
        Assertions.assertNotNull(proxyContext.getProduct());

        log.info("Get User");
        Assertions.assertNotNull(proxyContext.getUser());
    }
}