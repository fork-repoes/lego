package com.geekhalo.relation.domain.group.disable;

import com.geekhalo.relation.domain.group.AbstractRelationGroupEvent;
import com.geekhalo.relation.domain.group.RelationGroup;
import lombok.Value;

@Value
public class RelationGroupDisabledEvent
        extends AbstractRelationGroupEvent{
    public RelationGroupDisabledEvent(RelationGroup agg){
        super(agg);
    }
}
