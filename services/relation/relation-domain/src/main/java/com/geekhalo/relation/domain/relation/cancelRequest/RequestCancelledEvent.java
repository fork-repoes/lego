package com.geekhalo.relation.domain.relation.cancelRequest;

import com.geekhalo.relation.domain.relation.AbstractRelationEvent;
import com.geekhalo.relation.domain.relation.Relation;
import lombok.Value;

@Value
public class RequestCancelledEvent
        extends AbstractRelationEvent{
    public RequestCancelledEvent(Relation agg){
        super(agg);
    }
}
