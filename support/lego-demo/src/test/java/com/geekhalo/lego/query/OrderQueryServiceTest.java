package com.geekhalo.lego.query;

import com.geekhalo.lego.DemoApplication;
import com.geekhalo.lego.core.singlequery.Page;
import com.geekhalo.lego.core.singlequery.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
@Transactional
class OrderQueryServiceTest {
    @Autowired
    private OrderQueryRepository orderQueryRepository;
    @Autowired
    private OrderItemQueryRepository orderItemQueryRepository;

    @Autowired
    private OrderQueryService queryService;

    private Long userId = 100L;
    private List<Order> orders;


    @BeforeEach
    void setUp() {
        this.orders = new ArrayList<>();
        for (int i=0; i<100; i++){
            Order order = new Order();
            order.setUserId(userId);
            OrderStatus status = i % 2 == 0? OrderStatus.NONE : OrderStatus.PAID;
            order.setStatus(status);
            order.setPrice(100);
            this.orders.add(order);
            this.orderQueryRepository.save(order);

            List<OrderItem> orderItems = new ArrayList<>();
            for (int j=0; j<5; j++){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(order.getId());

                orderItem.setAmount(2);
                orderItem.setPrice(10);
                orderItem.setProductName("测试商品");
                orderItems.add(orderItem);
            }
            this.orderItemQueryRepository.saveAll(orderItems);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getById() {
        this.orders.forEach(order -> {
            log.info("item repository {}", this.orderItemQueryRepository);
            OrderDetail detail = this.queryService.getById(order.getId());
            checkForOrderDetail(order.getId(), detail);
        });
    }

    private void checkForOrderDetail(Long orderId, OrderDetail detail) {
        Assertions.assertNotNull(detail);
        Assertions.assertNotNull(detail.getOrder());
        Assertions.assertNotNull(detail.getOrderItems());
        Assertions.assertEquals(5, detail.getOrderItems().size());
    }

    @Test
    void pageByUserId() {
        PageByUserId pageByUserId = new PageByUserId();
        pageByUserId.setUserId(this.userId);
        Pageable pageable = Pageable.builder()
                .pageNo(1)
                .pageSize(5)
                .build();
        pageByUserId.setPageable(pageable);
        Page<OrderDetail> orderDetailPage = this.queryService.pageByUserId(pageByUserId);
        Assertions.assertNotNull(orderDetailPage);
        Assertions.assertTrue(orderDetailPage.getTotalElements() > 0);
        Assertions.assertTrue(orderDetailPage.getTotalPages() > 0);
        List<OrderDetail> content = orderDetailPage.getContent();
        Assertions.assertNotNull(content);
        content.stream().forEach(orderDetail -> {
            Assertions.assertNotNull(orderDetail);
            checkForOrderDetail(orderDetail.getOrder().getId(), orderDetail);
        });
    }

    @Test
    void getByUserId() {
        {
            GetByUserId getByUserId = new GetByUserId();
            getByUserId.setUserId(userId);
            getByUserId.setStatus(OrderStatus.NONE);
            List<OrderDetail> orderDetails = this.queryService.getByUserId(getByUserId);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(orderDetails));
            orderDetails.forEach(detail -> checkForOrderDetail(detail.getOrder().getId(), detail));
        }

        {
            GetByUserId getByUserId = new GetByUserId();
            getByUserId.setUserId(userId);
            getByUserId.setStatus(OrderStatus.CANCELLED);
            List<OrderDetail> orderDetails = this.queryService.getByUserId(getByUserId);
            Assertions.assertFalse(CollectionUtils.isNotEmpty(orderDetails));
        }

    }

    @Test
    void countByUser() {
        {
            CountByUserId countByUserId = new CountByUserId();
            countByUserId.setUserId(userId);
            countByUserId.setStatus(OrderStatus.NONE);
            Long count = this.queryService.countByUser(countByUserId);
            Assertions.assertTrue(count > 0);
        }

        {
            CountByUserId countByUserId = new CountByUserId();
            countByUserId.setUserId(userId);
            countByUserId.setStatus(OrderStatus.CANCELLED);
            Long count = this.queryService.countByUser(countByUserId);
            Assertions.assertTrue(count == 0);
        }
    }
}