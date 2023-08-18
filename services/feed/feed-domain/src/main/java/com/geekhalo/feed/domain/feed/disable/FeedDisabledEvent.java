package com.geekhalo.feed.domain.feed.disable;

import lombok.Value;
import com.geekhalo.feed.domain.feed.AbstractFeedEvent;
import com.geekhalo.feed.domain.feed.Feed;

@Value
public class FeedDisabledEvent
        extends AbstractFeedEvent{
    public FeedDisabledEvent(Feed agg){
        super(agg);
    }
}
