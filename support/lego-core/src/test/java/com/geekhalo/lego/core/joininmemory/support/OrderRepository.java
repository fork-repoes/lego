package com.geekhalo.lego.core.joininmemory.support;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by taoli on 2022/8/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Repository
public class OrderRepository {
    public List<Order> getRandom(){
        List<Order> orders = Lists.newArrayListWithCapacity(100);
        for(int i = 0; i< 100; i++){
            Order order = Order.builder()
                    .id(i + 1L)
                    .userId((long)(i % 20))
                    .productId((long) i % 5)
                    .build();
            orders.add(order);
        }
        return orders;
    }
}
