package com.geekhalo.lego.command;

import com.geekhalo.lego.core.command.AggNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by taoli on 2022/10/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class CustomOrderCommandServiceImpl implements CustomOrderCommandService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void cancel(Long orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new AggNotFoundException(orderId));
        order.cancel();
        this.orderRepository.save(order);
    }
}
