package com.geekhalo.feed.domain.feed.create;

import lombok.Value;
import com.geekhalo.feed.domain.feed.AbstractFeedEvent;
import com.geekhalo.feed.domain.feed.Feed;

@Value
public class FeedCreatedEvent
        extends AbstractFeedEvent{
    public FeedCreatedEvent(Feed agg){
        super(agg);
    }
}
