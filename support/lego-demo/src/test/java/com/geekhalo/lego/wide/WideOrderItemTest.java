package com.geekhalo.lego.wide;

import com.geekhalo.lego.core.wide.support.DataFromItemWideObjectWrapper;
import com.geekhalo.lego.core.wide.WideObjectWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Random;

class WideOrderItemTest {
    private WideOrderItem wideOrderItem;
    private WideObjectWrapper wideObjectWrapper;

    @BeforeEach
    void setUp() {
        this.wideOrderItem = new WideOrderItem();
        this.wideObjectWrapper = new DataFromItemWideObjectWrapper(this.wideOrderItem, new GenericConversionService());
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

            this.wideObjectWrapper.bindItem(orderItem);

            Assertions.assertEquals(orderItem.getId(), this.wideOrderItem.getOrderId());
            Assertions.assertEquals(orderItem.getStatus(), this.wideOrderItem.getOrderStatus());

            Assertions.assertTrue(this.wideObjectWrapper.isSameWithItem(orderItem));

            OrderItem orderItem2 = createOrderItem();
            this.wideObjectWrapper.updateItem(orderItem2);

            Assertions.assertEquals(orderItem2.getId(), this.wideOrderItem.getOrderId());
            Assertions.assertEquals(orderItem2.getStatus(), this.wideOrderItem.getOrderStatus());

            Assertions.assertFalse(this.wideObjectWrapper.isSameWithItem(orderItem));
            Assertions.assertTrue(this.wideObjectWrapper.isSameWithItem(orderItem2));

        }

        {
            Assertions.assertNull(this.wideOrderItem.getUserId());
            Assertions.assertNull(this.wideOrderItem.getUserName());

            User user = createUser();

            this.wideObjectWrapper.bindItem(user);

            Assertions.assertEquals(user.getId(), this.wideOrderItem.getUserId());
            Assertions.assertEquals(user.getName(), this.wideOrderItem.getUserName());

            Assertions.assertTrue(this.wideObjectWrapper.isSameWithItem(user));

            User user2 = createUser();
            this.wideObjectWrapper.updateItem(user2);

            Assertions.assertEquals(user2.getId(), this.wideOrderItem.getUserId());
            Assertions.assertEquals(user2.getName(), this.wideOrderItem.getUserName());

            Assertions.assertFalse(this.wideObjectWrapper.isSameWithItem(user));
            Assertions.assertTrue(this.wideObjectWrapper.isSameWithItem(user2));

        }

        {
            Assertions.assertNull(this.wideOrderItem.getProductId());
            Assertions.assertNull(this.wideOrderItem.getProductName());

            Product product = createProduct();

            this.wideObjectWrapper.bindItem(product);

            Assertions.assertEquals(product.getId(), this.wideOrderItem.getProductId());
            Assertions.assertEquals(product.getName(), this.wideOrderItem.getProductName());

            Assertions.assertTrue(this.wideObjectWrapper.isSameWithItem(product));

            Product product2 = createProduct();
            this.wideObjectWrapper.updateItem(product2);

            Assertions.assertEquals(product2.getId(), this.wideOrderItem.getProductId());
            Assertions.assertEquals(product2.getName(), this.wideOrderItem.getProductName());
            Assertions.assertFalse(this.wideObjectWrapper.isSameWithItem(product));
            Assertions.assertTrue(this.wideObjectWrapper.isSameWithItem(product2));

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