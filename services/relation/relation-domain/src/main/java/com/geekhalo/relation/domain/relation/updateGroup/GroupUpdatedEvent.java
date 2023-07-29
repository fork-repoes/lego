package com.geekhalo.relation.domain.relation.updateGroup;

import lombok.Value;
import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;

@Value
public class GroupUpdatedEvent
        extends AbstractRelationEvent{
    public GroupUpdatedEvent(Relation agg){
        super(agg);
    }
}
