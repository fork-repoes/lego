package com.geekhalo.relation.domain.acceptRequest;

import com.geekhalo.relation.domain.AbstractRelationEvent;
import com.geekhalo.relation.domain.Relation;
import lombok.Value;

@Value
public class RequestAcceptedEvent
        extends AbstractRelationEvent{
    public RequestAcceptedEvent(Relation agg){
        super(agg);
    }
}
