package com.geekhalo.feed.app;

import com.geekhalo.feed.domain.QueryType;
import com.geekhalo.feed.domain.feed.Feed;
import com.geekhalo.feed.domain.feed.FeedOwner;

import java.util.List;

public interface CustomFeedQueryApplication {

    default List<Feed> queryFeeds(FeedOwner owner, QueryType queryType, Integer size){
        return queryFeeds(owner, queryType, Long.MAX_VALUE, size);
    }

    List<Feed> queryFeeds(FeedOwner owner, QueryType queryType, Long score, Integer size);
}
