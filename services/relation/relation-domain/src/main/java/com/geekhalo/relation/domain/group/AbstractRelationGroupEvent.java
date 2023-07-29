package com.geekhalo.relation.domain.group;

import com.geekhalo.lego.core.command.support.AbstractDomainEvent;

public abstract class AbstractRelationGroupEvent
        extends AbstractDomainEvent<Long, RelationGroup> {
    public AbstractRelationGroupEvent(RelationGroup agg) {
        super(agg);
    }
}
