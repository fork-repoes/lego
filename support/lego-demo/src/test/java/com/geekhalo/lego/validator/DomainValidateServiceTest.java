package com.geekhalo.lego.validator;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.loader.CreateOrderCmd;
import com.geekhalo.lego.service.product.Product;
import com.geekhalo.lego.service.stock.Stock;
import com.geekhalo.lego.service.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class DomainValidateServiceTest {
    @Autowired
    private DomainValidateService domainValidateService;

    @Test
    void createOrder_error1() {
        Assertions.assertThrows(Exception.class, ()->{
            this.domainValidateService.createOrder((CreateOrderContext) null);
        });
    }

    @Test
    void createOrder_error2() {
        Assertions.assertThrows(Exception.class, ()->{
            CreateOrderContext context = new CreateOrderContext();
            this.domainValidateService.createOrder(context);
        });
    }

    @Test
    void createOrder_error3() {
        Assertions.assertThrows(Exception.class, ()->{
            CreateOrderContext context = new CreateOrderContext();
            context.setUser(User.builder()
                    .build());
            this.domainValidateService.createOrder(context);
        });
    }

    @Test
    void createOrder_error4() {
        Assertions.assertThrows(Exception.class, ()->{
            CreateOrderContext context = new CreateOrderContext();
            context.setUser(User.builder()
                    .build());
            context.setProduct(Product.builder()
                    .build());
            this.domainValidateService.createOrder(context);
        });
    }

    @Test
    void createOrder_error5() {
        Assertions.assertThrows(Exception.class, ()->{
            CreateOrderContext context = new CreateOrderContext();
            context.setUser(User.builder()
                    .build());
            context.setProduct(Product.builder()
                    .build());
            context.setStock(Stock.builder()
                            .count(0)
                    .build());
            context.setCount(1);
            this.domainValidateService.createOrder(context);
        });
    }

    @Test
    void createOrder() {
        CreateOrderContext context = new CreateOrderContext();
        context.setUser(User.builder()
                .build());
        context.setProduct(Product.builder()
                .build());
        context.setStock(Stock.builder()
                .count(10)
                .build());
        context.setCount(1);
        this.domainValidateService.createOrder(context);
    }



    @Test
    void createOrderCmd_error1() {
        Assertions.assertThrows(Exception.class, ()->{
            this.domainValidateService.createOrder((CreateOrderCmd) null);
        });
    }

    @Test
    void createOrderCmd_error2() {
        Assertions.assertThrows(Exception.class, ()->{
            CreateOrderCmd cmd = new CreateOrderCmd();
            cmd.setCount(10000);
            cmd.setProductId(100L);
            cmd.setUserId(100L);
            this.domainValidateService.createOrder(cmd);
        });
    }

    @Test
    void createOrderCmd() {
        CreateOrderCmd cmd = new CreateOrderCmd();
        cmd.setCount(1);
        cmd.setProductId(100L);
        cmd.setUserId(100L);
        this.domainValidateService.createOrder(cmd);
    }

}