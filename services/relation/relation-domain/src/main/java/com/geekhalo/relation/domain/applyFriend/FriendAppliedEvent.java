package com.geekhalo.relation.domain.applyFriend;

import lombok.Value;
import com.geekhalo.relation.domain.AbstractRelationEvent;
import com.geekhalo.relation.domain.Relation;

@Value
public class FriendAppliedEvent
        extends AbstractRelationEvent{
    public FriendAppliedEvent(Relation agg){
        super(agg);
    }
}
