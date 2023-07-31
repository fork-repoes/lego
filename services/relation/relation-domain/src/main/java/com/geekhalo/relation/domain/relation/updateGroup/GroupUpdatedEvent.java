package com.geekhalo.relation.domain.relation.updateGroup;

import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;
import lombok.Value;

@Value
public class GroupUpdatedEvent
        extends AbstractRelationEvent{
    public GroupUpdatedEvent(Relation agg){
        super(agg);
    }
}
