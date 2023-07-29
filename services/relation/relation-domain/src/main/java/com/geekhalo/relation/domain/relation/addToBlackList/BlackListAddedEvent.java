package com.geekhalo.relation.domain.relation.addToBlackList;

import lombok.Value;
import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;

@Value
public class BlackListAddedEvent
        extends AbstractRelationEvent{
    public BlackListAddedEvent(Relation agg){
        super(agg);
    }
}
