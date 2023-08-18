package com.geekhalo.feed.domain.feed;

import com.geekhalo.lego.core.command.support.AbstractDomainEvent;
import java.lang.Long;

public abstract class AbstractFeedEvent
        extends AbstractDomainEvent<Long, Feed> {
    public AbstractFeedEvent(Feed agg) {
        super(agg);
    }
}
