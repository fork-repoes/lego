package com.geekhalo.lego.wide;

import com.geekhalo.lego.core.wide.WideItemDataProvider;
import com.geekhalo.lego.service.order.Order;
import com.geekhalo.lego.service.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoli on 2022/10/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
@org.springframework.core.annotation.Order(value = Ordered.HIGHEST_PRECEDENCE)
public class OrderProvider implements WideItemDataProvider<WideOrderType, Long, Order> {
    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> apply(List<Long> key) {
        return orderDao.findAllById(key);
    }

    @Override
    public WideOrderType getSupportType() {
        return WideOrderType.ORDER;
    }
}
