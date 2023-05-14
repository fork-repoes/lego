package com.geekhalo.lego.command;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.DomainEvent;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface OrderEvent
        extends DomainEvent<Long, Order> {
}
