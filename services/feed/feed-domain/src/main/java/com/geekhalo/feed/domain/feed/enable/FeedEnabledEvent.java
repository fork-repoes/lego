package com.geekhalo.feed.domain.feed.enable;

import lombok.Value;
import com.geekhalo.feed.domain.feed.AbstractFeedEvent;
import com.geekhalo.feed.domain.feed.Feed;

@Value
public class FeedEnabledEvent
        extends AbstractFeedEvent{
    public FeedEnabledEvent(Feed agg){
        super(agg);
    }
}
