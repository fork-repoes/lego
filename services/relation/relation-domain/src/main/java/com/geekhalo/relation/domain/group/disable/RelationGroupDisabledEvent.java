package com.geekhalo.relation.domain.group.disable;

import lombok.Value;
import com.geekhalo.relation.domain.group.AbstractRelationGroupEvent;
import com.geekhalo.relation.domain.group.RelationGroup;

@Value
public class RelationGroupDisabledEvent
        extends AbstractRelationGroupEvent{
    public RelationGroupDisabledEvent(RelationGroup agg){
        super(agg);
    }
}
