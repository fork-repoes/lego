package com.geekhalo.lego.core.command;

import com.geekhalo.lego.core.command.support.AbstractEntity;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Transient;
import java.util.List;
import java.util.function.Consumer;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AbstractAggRoot extends AbstractEntity
    implements AggRoot<Long>{
    @Transient
    private final List<DomainEvent> events = Lists.newArrayList();

    @Override
    public void consumeAndClearEvent(Consumer<DomainEvent> eventConsumer) {
        this.consumeEvent(eventConsumer);
        this.clearEvent();

    }

    private void clearEvent() {
        this.events.clear();;
    }

    private void consumeEvent(Consumer<DomainEvent> eventConsumer){
        this.events.forEach(eventConsumer);
    }

    protected void addEvent(DomainEvent domainEvent){
        this.events.add(domainEvent);
    }
}
