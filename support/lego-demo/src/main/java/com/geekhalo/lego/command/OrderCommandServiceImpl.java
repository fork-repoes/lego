package com.geekhalo.lego.command;

import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
public class OrderCommandServiceImpl implements OrderCommandService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public Long create(CreateOrderCommand command) {
        CreateOrderContext context = new CreateOrderContext(command);
        CreateOrderContext contextProxy = this.lazyLoadProxyFactory.createProxyFor(context);
        Order order = Order.create(contextProxy);
        this.orderRepository.save(order);
        order.consumeAndClean(event -> eventPublisher.publishEvent(event));
        return order.getId();
    }

    @Override
    public void paySuccess(PaySuccessCommand command) {
        Order order = this.orderRepository.getById(command.getOrderId());
        order.paySuccess(command.getChanel(), command.getPrice());
        this.orderRepository.save(order);
        order.consumeAndClean(event -> eventPublisher.publishEvent(event));
    }
}
