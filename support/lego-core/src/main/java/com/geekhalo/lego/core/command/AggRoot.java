package com.geekhalo.lego.core.command;

import java.util.function.Consumer;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface AggRoot<ID> {
    ID getId();
    void consumeAndClearEvent(Consumer<DomainEvent> eventConsumer);

    default void validate(){

    }
}
