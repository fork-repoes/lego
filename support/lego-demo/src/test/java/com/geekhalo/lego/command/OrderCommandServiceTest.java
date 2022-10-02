package com.geekhalo.lego.command;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.query.OrderStatus;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
@Transactional
class OrderCommandServiceTest {
    @Autowired
    private OrderCommandService commandService;

    @Autowired
    private EventListeners eventListeners;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        eventListeners.clear();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        CreateOrderCommand command = getCreateOrderCommand();

        Long orderId = this.commandService.create(command);

        Order order = this.orderRepository.getById(orderId);

        Assertions.assertNotNull(order);
        Assertions.assertNotNull(order.getAddress());
        Assertions.assertNotNull(order.getItems());
        Assertions.assertEquals(command.getProducts().size(), order.getItems().size());


        List<Object> events = this.eventListeners.getEvents();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(events));
        Object event = this.eventListeners.getEvents().get(0);
        Assertions.assertTrue(event instanceof OrderCreatedEvent);

    }

    @Test
    void paySuccess() {

        CreateOrderCommand command = getCreateOrderCommand();
        Long orderId = this.commandService.create(command);

        {
            Order order = this.orderRepository.getById(orderId);
            Assertions.assertEquals(OrderStatus.NONE, order.getStatus());
        }

        PaySuccessCommand paySuccessCommand = new PaySuccessCommand();
        paySuccessCommand.setOrderId(orderId);
        paySuccessCommand.setChanel("微信");
        paySuccessCommand.setPrice(48L);
        this.commandService.paySuccess(paySuccessCommand);

        {
            Order order = this.orderRepository.getById(orderId);
            Assertions.assertEquals(OrderStatus.PAID, order.getStatus());
            List<PayRecord> payRecords = order.getPayRecords();
            Assertions.assertNotNull(payRecords);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(payRecords));
            PayRecord payRecord = payRecords.get(0);
            Assertions.assertEquals("微信", payRecord.getChannel());
            Assertions.assertEquals(48L, payRecord.getPrice());
        }

        List<Object> events = this.eventListeners.getEvents();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(events));
        Object event = this.eventListeners.getEvents().get(0);
        Assertions.assertTrue(event instanceof OrderCreatedEvent);
    }

    private CreateOrderCommand getCreateOrderCommand() {
        CreateOrderCommand command = new CreateOrderCommand();
        command.setUserId(100L);
        command.setUserAddress(10000L);
        List<ProductForBuy> products = Lists.newArrayList();
        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1000L);
            product.setAmount(1);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1100L);
            product.setAmount(2);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1200L);
            product.setAmount(3);
            products.add(product);
        }

        command.setProducts(products);
        return command;
    }


}