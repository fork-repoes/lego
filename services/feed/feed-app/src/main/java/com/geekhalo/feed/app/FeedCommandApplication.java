package com.geekhalo.feed.app;

import com.geekhalo.feed.domain.feed.create.CreateFeedCommand;
import com.geekhalo.feed.domain.feed.disable.DisableFeedCommand;
import com.geekhalo.feed.domain.feed.enable.EnableFeedCommand;
import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.feed.domain.feed.FeedCommandRepository;
import com.geekhalo.feed.domain.feed.Feed;

@CommandServiceDefinition(
        domainClass = Feed.class,
        repositoryClass = FeedCommandRepository.class
)
public interface FeedCommandApplication {

    Feed create(CreateFeedCommand command);

    Feed enable(EnableFeedCommand command);

    Feed diable(DisableFeedCommand command);
}
