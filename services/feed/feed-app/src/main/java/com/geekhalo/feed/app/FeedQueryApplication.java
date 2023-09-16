package com.geekhalo.feed.app;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.geekhalo.feed.domain.feed.FeedQueryRepository;
import com.geekhalo.feed.domain.feed.Feed;

import java.util.Optional;

@QueryServiceDefinition(
        repositoryClass = FeedQueryRepository.class,
        domainClass = Feed.class
)
public interface FeedQueryApplication extends CustomFeedQueryApplication{
    Optional<Feed> findById(Long id);
}
