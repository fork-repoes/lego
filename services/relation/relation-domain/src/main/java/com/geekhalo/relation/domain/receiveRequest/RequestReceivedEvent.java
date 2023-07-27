package com.geekhalo.relation.domain.receiveRequest;

import com.geekhalo.relation.domain.AbstractRelationEvent;
import com.geekhalo.relation.domain.Relation;
import lombok.Value;

@Value
public class RequestReceivedEvent
        extends AbstractRelationEvent{
    public RequestReceivedEvent(Relation agg){
        super(agg);
    }
}
