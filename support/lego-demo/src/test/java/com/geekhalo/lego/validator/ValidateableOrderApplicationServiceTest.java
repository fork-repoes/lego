package com.geekhalo.lego.validator;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.core.validator.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/9/19.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
class ValidateableOrderApplicationServiceTest {

    @Autowired
    private ValidateableOrderApplicationService applicationService;

    @Test
    void createOrder_error() {
        Assertions.assertThrows(ValidateException.class, ()->{
            try {
                ValidateableOrder order = new ValidateableOrder();
                order.setSellPrice(20);
                order.setAmount(2);
                order.setDiscountPrice(5);
                order.setManualPrice(1);

                order.setPayPrice(35);
                this.applicationService.createOrder(order);
            }catch (RuntimeException e){
                e.printStackTrace();;
                throw e;
            }
        });
    }

    @Test
    void createOrder() {
        ValidateableOrder order = new ValidateableOrder();
        order.setSellPrice(20);
        order.setAmount(2);
        order.setDiscountPrice(5);
        order.setManualPrice(1);

        order.setPayPrice(34);
        this.applicationService.createOrder(order);
    }
}