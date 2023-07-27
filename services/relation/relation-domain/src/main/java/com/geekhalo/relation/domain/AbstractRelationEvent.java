package com.geekhalo.relation.domain;

import com.geekhalo.lego.core.command.support.AbstractDomainEvent;

public abstract class AbstractRelationEvent
        extends AbstractDomainEvent<Long, Relation> {
    public AbstractRelationEvent(Relation agg) {
        super(agg);
    }
}
