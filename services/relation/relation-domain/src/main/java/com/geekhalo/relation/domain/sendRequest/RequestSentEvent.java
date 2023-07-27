package com.geekhalo.relation.domain.sendRequest;

import com.geekhalo.relation.domain.AbstractRelationEvent;
import com.geekhalo.relation.domain.Relation;
import lombok.Value;

@Value
public class RequestSentEvent
        extends AbstractRelationEvent{
    public RequestSentEvent(Relation agg){
        super(agg);
    }
}
