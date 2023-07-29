package com.geekhalo.relation.domain.relation.receiveRequest;

import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;
import lombok.Value;

@Value
public class RequestReceivedEvent
        extends AbstractRelationEvent{
    public RequestReceivedEvent(Relation agg){
        super(agg);
    }
}
