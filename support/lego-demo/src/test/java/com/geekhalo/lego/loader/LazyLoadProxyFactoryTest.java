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
    void createProxyFor() {
        CreateOrderCmd cmd = new CreateOrderCmd();
        cmd.setUserId(1L);
        cmd.setProductId(2L);
        cmd.setCount(2);

        CreateOrderContext context = new CreateOrderContext();
        context.setCmd(cmd);

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