package com.geekhalo.relation.domain.cancelRequest;

import com.geekhalo.relation.domain.AbstractRelationEvent;
import com.geekhalo.relation.domain.Relation;
import lombok.Value;

@Value
public class RequestCancelledEvent
        extends AbstractRelationEvent{
    public RequestCancelledEvent(Relation agg){
        super(agg);
    }
}
