package com.geekhalo.relation.domain.group.enable;

import lombok.Value;
import com.geekhalo.relation.domain.group.AbstractRelationGroupEvent;
import com.geekhalo.relation.domain.group.RelationGroup;

@Value
public class RelationGroupEnabledEvent
        extends AbstractRelationGroupEvent{
    public RelationGroupEnabledEvent(RelationGroup agg){
        super(agg);
    }
}
