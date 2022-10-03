package com.geekhalo.lego.command;

import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ValidateService validateService;

    @Override
    public Long create(CreateOrderCommand command) {
        CreateOrderContext context = new CreateOrderContext(command);
        CreateOrderContext contextProxy = this.lazyLoadProxyFactory.createProxyFor(context);

        validateService.validate(contextProxy);

        Order order = Order.create(contextProxy);

        this.orderRepository.save(order);
        order.consumeAndClearEvent(event -> eventPublisher.publishEvent(event));
        return order.getId();
    }

    @Override
    public void paySuccess(PaySuccessCommand command) {
        Order order = this.orderRepository.findById(command.getOrderId()).orElse(null);
        order.paySuccess(command.getChanel(), command.getPrice());
        this.orderRepository.save(order);
        order.consumeAndClearEvent(event -> eventPublisher.publishEvent(event));
    }
}
