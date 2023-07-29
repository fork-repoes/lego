package com.geekhalo.relation.domain.relation.addToBlackList;

import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;
import lombok.Value;

@Value
public class BlackListAddedEvent
        extends AbstractRelationEvent{
    public BlackListAddedEvent(Relation agg){
        super(agg);
    }
}
