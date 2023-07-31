package com.geekhalo.relation.domain.relation.sendRequest;

import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;
import lombok.Value;

@Value
public class RequestSentEvent
        extends AbstractRelationEvent{
    public RequestSentEvent(Relation agg){
        super(agg);
    }
}
