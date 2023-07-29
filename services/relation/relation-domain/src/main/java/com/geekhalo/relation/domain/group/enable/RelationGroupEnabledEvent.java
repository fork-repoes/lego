package com.geekhalo.relation.domain.group.enable;

import com.geekhalo.relation.domain.group.AbstractRelationGroupEvent;
import com.geekhalo.relation.domain.group.RelationGroup;
import lombok.Value;

@Value
public class RelationGroupEnabledEvent
        extends AbstractRelationGroupEvent{
    public RelationGroupEnabledEvent(RelationGroup agg){
        super(agg);
    }
}
