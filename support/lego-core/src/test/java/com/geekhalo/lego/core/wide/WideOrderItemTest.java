package com.geekhalo.lego.core.wide;

import com.geekhalo.lego.core.wide.support.BindFromBasedWideWrapperFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Random;

class WideOrderItemTest {
    private WideOrderItem wideOrderItem;
    private WideWrapper wideWrapper;

    @BeforeEach
    void setUp() {
        this.wideOrderItem = new WideOrderItem();
        WideWrapperFactory wideWrapperFactory = new BindFromBasedWideWrapperFactory(new GenericConversionService());
        this.wideWrapper = wideWrapperFactory.createForWide(this.wideOrderItem);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test() {
        {
            Assertions.assertNull(this.wideOrderItem.getOrderId());
            Assertions.assertNull(this.wideOrderItem.getOrderStatus());

            OrderItem orderItem = createOrderItem();

            this.wideWrapper.bindItem(orderItem);

            Assertions.assertEquals(orderItem.getId(), this.wideOrderItem.getOrderId());
            Assertions.assertEquals(orderItem.getStatus(), this.wideOrderItem.getOrderStatus());

            Assertions.assertTrue(this.wideWrapper.isSameWithItem(orderItem));

            OrderItem orderItem2 = createOrderItem();
            this.wideWrapper.updateItem(orderItem2);

            Assertions.assertEquals(orderItem2.getId(), this.wideOrderItem.getOrderId());
            Assertions.assertEquals(orderItem2.getStatus(), this.wideOrderItem.getOrderStatus());

            Assertions.assertFalse(this.wideWrapper.isSameWithItem(orderItem));
            Assertions.assertTrue(this.wideWrapper.isSameWithItem(orderItem2));

        }

        {
            Assertions.assertNull(this.wideOrderItem.getUserId());
            Assertions.assertNull(this.wideOrderItem.getUserName());

            User user = createUser();

            this.wideWrapper.bindItem(user);

            Assertions.assertEquals(user.getId(), this.wideOrderItem.getUserId());
            Assertions.assertEquals(user.getName(), this.wideOrderItem.getUserName());

            Assertions.assertTrue(this.wideWrapper.isSameWithItem(user));

            User user2 = createUser();
            this.wideWrapper.updateItem(user2);

            Assertions.assertEquals(user2.getId(), this.wideOrderItem.getUserId());
            Assertions.assertEquals(user2.getName(), this.wideOrderItem.getUserName());

            Assertions.assertFalse(this.wideWrapper.isSameWithItem(user));
            Assertions.assertTrue(this.wideWrapper.isSameWithItem(user2));

        }

        {
            Assertions.assertNull(this.wideOrderItem.getProductId());
            Assertions.assertNull(this.wideOrderItem.getProductName());

            Product product = createProduct();

            this.wideWrapper.bindItem(product);

            Assertions.assertEquals(product.getId(), this.wideOrderItem.getProductId());
            Assertions.assertEquals(product.getName(), this.wideOrderItem.getProductName());

            Assertions.assertTrue(this.wideWrapper.isSameWithItem(product));

            Product product2 = createProduct();
            this.wideWrapper.updateItem(product2);

            Assertions.assertEquals(product2.getId(), this.wideOrderItem.getProductId());
            Assertions.assertEquals(product2.getName(), this.wideOrderItem.getProductName());
            Assertions.assertFalse(this.wideWrapper.isSameWithItem(product));
            Assertions.assertTrue(this.wideWrapper.isSameWithItem(product2));

        }
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(new Random().nextLong());
        product.setName(String.valueOf(new Random().nextInt()));
        return product;
    }

    private User createUser() {
        User user = new User();
        user.setId(new Random().nextLong());
        user.setName(String.valueOf(new Random().nextInt()));
        return user;
    }

    private OrderItem createOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(new Random().nextLong());
        orderItem.setStatus(new Random().nextInt());
        return orderItem;
    }

    @Test
    void isSameWithItem() {
    }

    @Test
    void updateItem() {
    }
}