package com.geekhalo.lego.core.command.support;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.DomainEvent;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class AbstractAggRoot extends AbstractEntity
    implements AggRoot<Long> {
    @Transient
    private final List<DomainEvent> events = Lists.newArrayList();

    @Override
    public void consumeAndClearEvent(Consumer<DomainEvent> eventConsumer) {
        List<DomainEvent> tmpEvents = new ArrayList<>(this.events);
        // 先清理，避免出现事件消费嵌套导致事件无法被清理的问题
        this.clearEvent();
        tmpEvents.forEach(event -> eventConsumer.accept(event));
    }

    private void clearEvent() {
        this.events.clear();;
    }


    protected void addEvent(DomainEvent domainEvent){
        this.events.add(domainEvent);
    }
}
