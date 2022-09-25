package com.geekhalo.lego.core.joininmemory.support;

import com.google.common.collect.Lists;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
class JoinItemExecutorAdapterTest {
    private JoinItemExecutorAdapter<OrderDetail, Long, User, User> joinExecutorAdapter;

    @BeforeEach
    void setUp() {
        this.joinExecutorAdapter = JoinItemExecutorAdapter.<OrderDetail, Long, User, User>builder()
                .keyFromSourceData(order->order.getOrder().getUserId())
                .keyFromJoinData(user -> user.getId())
                .joinDataConverter(user -> user)
                .foundCallback((orderDetail, user) -> orderDetail.setUser(user.get(0)))
                .joinDataLoader(userIds -> createUser(userIds))
                .build();
    }

    private List<User> createUser(List<Long> userIds) {
        return userIds.stream()
                .map(this::createUser)
                .collect(toList());
    }
    private User createUser(Long id){
        return User.builder()
                .id(id)
                .name("Name-" + id)
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void execute() {
        List<OrderDetail> orderDetails = Lists.newArrayListWithCapacity(100);
        for(int i = 0; i< 100; i++){
            Order order = Order.builder()
                    .id(i + 1L)
                    .userId((long)(i % 20))
                    .build();
            orderDetails.add(new OrderDetail(order));
        }
        this.joinExecutorAdapter.execute(orderDetails);

        orderDetails.forEach(orderDetail -> {
            Assertions.assertNotNull(orderDetail);
            Assertions.assertNotNull(orderDetail.getOrder());
            Assertions.assertNotNull(orderDetail.getUser());
        });
    }
}