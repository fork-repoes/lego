package com.geekhalo.lego.command;

import com.geekhalo.lego.core.command.support.AbstractDomainEvent;
import lombok.Builder;
import lombok.Value;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class OrderCreatedEvent
        extends AbstractDomainEvent<Long, Order>
        implements OrderEvent{
    public OrderCreatedEvent(Order order){
        super(order);
    }
}
