package com.geekhalo.relation.domain.relation.removeFromBlackList;

import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;
import lombok.Value;

@Value
public class BlackListRemovedEvent
        extends AbstractRelationEvent{
    public BlackListRemovedEvent(Relation agg){
        super(agg);
    }
}
