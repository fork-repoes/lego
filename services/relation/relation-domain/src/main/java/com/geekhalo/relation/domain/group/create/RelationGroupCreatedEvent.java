package com.geekhalo.relation.domain.group.create;

import com.geekhalo.relation.domain.group.AbstractRelationGroupEvent;
import com.geekhalo.relation.domain.group.RelationGroup;
import lombok.Value;

@Value
public class RelationGroupCreatedEvent
        extends AbstractRelationGroupEvent{
    public RelationGroupCreatedEvent(RelationGroup agg){
        super(agg);
    }
}
