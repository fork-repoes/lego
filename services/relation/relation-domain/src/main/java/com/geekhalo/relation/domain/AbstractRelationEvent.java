package com.geekhalo.relation.domain;

import com.geekhalo.lego.core.command.support.AbstractDomainEvent;
import java.lang.Long;

public abstract class AbstractRelationEvent
        extends AbstractDomainEvent<Long, Relation> {
    public AbstractRelationEvent(Relation agg) {
        super(agg);
    }
}
