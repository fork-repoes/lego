package com.geekhalo.lego.wide;

import com.geekhalo.lego.core.wide.WideItemDataProvider;
import com.geekhalo.lego.service.order.Order;
import com.geekhalo.lego.service.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class OrderProvider implements WideItemDataProvider<WideOrderType, Long, Order> {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> apply(List<Long> key) {
        return orderRepository.getById(key);
    }

    @Override
    public WideOrderType getSupportType() {
        return WideOrderType.ORDER;
    }
}
