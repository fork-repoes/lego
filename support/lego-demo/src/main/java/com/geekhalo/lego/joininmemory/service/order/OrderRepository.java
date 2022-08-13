package com.geekhalo.lego.joininmemory.service.order;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.geekhalo.lego.util.TimeUtils.sleepAsMS;

/**
 * Created by taoli on 2022/7/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class OrderRepository {
    public List<Order> getByUserId(Long userId){
        sleepAsMS(5);
        List<Order> orders = Lists.newArrayListWithCapacity(100);
        for (int i = 0; i< 100; i++){
            orders.add(createOrder(i+1));
        }
        return orders;
    }

    private Order createOrder(long userId) {
        return Order.builder()
                .id(RandomUtils.nextLong(0, 1000000))
                .userId(userId)
                .addressId(userId + 1)
                .productId(userId + 2)
                .build();
    }
}
