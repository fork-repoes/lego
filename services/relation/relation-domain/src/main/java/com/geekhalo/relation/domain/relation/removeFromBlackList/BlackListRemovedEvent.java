package com.geekhalo.relation.domain.relation.removeFromBlackList;

import lombok.Value;
import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;

@Value
public class BlackListRemovedEvent
        extends AbstractRelationEvent{
    public BlackListRemovedEvent(Relation agg){
        super(agg);
    }
}
