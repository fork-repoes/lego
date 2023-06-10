package com.geekhalo.lego.command;

import com.geekhalo.lego.query.OrderStatus;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Transactional
abstract class BaseOrderCommandServiceTest {

    @Autowired
    private EventListeners eventListeners;

    @Autowired
    private OrderRepository orderRepository;

    abstract OrderCommandService orderCommandService();

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

        Long orderId = this.orderCommandService().create(command).getId();

        Order order = this.orderRepository.findById(orderId).orElse(null);

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
        Long orderId = this.orderCommandService().create(command).getId();

        {
            Order order = this.orderRepository.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.NONE, order.getStatus());
        }

        PaySuccessCommand paySuccessCommand = new PaySuccessCommand();
        paySuccessCommand.setOrderId(orderId);
        paySuccessCommand.setChanel("微信");
        paySuccessCommand.setPrice(48L);
        this.orderCommandService().paySuccess(paySuccessCommand);

        {
            Order order = this.orderRepository.findById(orderId).orElse(null);
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

    @Test
    void cancel() {

        CreateOrderCommand command = getCreateOrderCommand();
        Long orderId = this.orderCommandService().create(command).getId();

        {
            Order order = this.orderRepository.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.NONE, order.getStatus());
        }

        this.orderCommandService().cancel(orderId);

        {
            Order order = this.orderRepository.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.CANCELLED, order.getStatus());

        }
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
