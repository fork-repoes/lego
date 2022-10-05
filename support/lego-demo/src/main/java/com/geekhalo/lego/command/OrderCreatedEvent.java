package com.geekhalo.lego.command;

import lombok.Builder;
import lombok.Value;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Builder
@Value
public class OrderCreatedEvent implements OrderEvent{
    private final Order order;
}
