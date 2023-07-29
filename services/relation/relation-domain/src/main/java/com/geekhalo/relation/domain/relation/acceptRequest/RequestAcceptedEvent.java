package com.geekhalo.relation.domain.relation.acceptRequest;

import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;
import lombok.Value;

@Value
public class RequestAcceptedEvent
        extends AbstractRelationEvent{
    public RequestAcceptedEvent(Relation agg){
        super(agg);
    }
}
