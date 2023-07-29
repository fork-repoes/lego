package com.geekhalo.relation.domain.group.create;

import lombok.Value;
import com.geekhalo.relation.domain.group.AbstractRelationGroupEvent;
import com.geekhalo.relation.domain.group.RelationGroup;

@Value
public class RelationGroupCreatedEvent
        extends AbstractRelationGroupEvent{
    public RelationGroupCreatedEvent(RelationGroup agg){
        super(agg);
    }
}
