package com.geekhalo.feed;

import com.geekhalo.feed.app.FeedCommandApplication;
import com.geekhalo.feed.app.FeedQueryApplication;
import com.geekhalo.feed.domain.QueryType;
import com.geekhalo.feed.domain.box.BoxService;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.*;
import com.geekhalo.feed.domain.feed.create.CreateFeedCommand;
import com.geekhalo.feed.domain.feed.disable.DisableFeedCommand;
import com.geekhalo.feed.domain.feed.enable.EnableFeedCommand;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest(classes = Application.class)
class FeedCommandApplicationTest {
    @Autowired
    private FeedCommandApplication commandApplication;

    @Autowired
    private FeedQueryApplication queryApplication;

    @Autowired
    private BoxService boxService;

    private FeedOwner feedOwner;
    private FeedData feedData;
    private Feed feed;


    @BeforeEach
    void setUp() {
        this.feedOwner = new FeedOwner(OwnerType.TEST, RandomUtils.nextLong());
        this.feedData = new FeedData(FeedDataType.TEST, "Test");
        CreateFeedCommand command = new CreateFeedCommand();
        command.setOwner(this.feedOwner);
        command.setData(this.feedData);
        this.feed = this.commandApplication.create(command);

        Assertions.assertNotNull(this.feed.getId());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
    }

    @Test
    public void dispatcher(){
        FeedIndex feedIndex = this.feed.createIndex();
        for (int i = 0; i < 10; i++) {
            FeedOwner feedOwner = new FeedOwner(OwnerType.USER, this.feedOwner.getOwnerId() + i + 1);
            List<FeedIndex> feedIndices = this.boxService.queryInboxByScore(feedOwner, BoxType.IN_BOX, Long.MAX_VALUE, 10);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(feedIndices));
            FeedIndex index = feedIndices.get(0);
            Assertions.assertEquals(feedIndex, index);
        }
    }

    @Test
    public void merge(){
        FeedOwner feedOwner = new FeedOwner(OwnerType.USER, this.feedOwner.getOwnerId() - 5);
        List<Feed> feeds = this.queryApplication.queryFeeds1(feedOwner, QueryType.TEST, 10);
        Assertions.assertNotNull(feeds);
        Feed feed = feeds.get(0);
        Assertions.assertEquals(this.feed.getData(), feed.getData());
        Assertions.assertEquals(this.feed.createIndex(), feed.createIndex());
    }

    @Test
    void status() {
        {
            Optional<Feed> feedOptional = this.queryApplication.findById(this.feed.getId());
            Assertions.assertTrue(feedOptional.isPresent());
            Feed feedToCheck = feedOptional.get();
            Assertions.assertTrue(feedToCheck.isEnable());
        }

        DisableFeedCommand disableFeedCommand = new DisableFeedCommand();
        disableFeedCommand.setId(this.feed.getId());
        this.commandApplication.disable(disableFeedCommand);

        {
            Optional<Feed> feedOptional = this.queryApplication.findById(this.feed.getId());
            Assertions.assertTrue(feedOptional.isPresent());
            Feed feedToCheck = feedOptional.get();
            Assertions.assertTrue(feedToCheck.isDisable());
        }

        EnableFeedCommand enableFeedCommand = new EnableFeedCommand();
        enableFeedCommand.setId(this.feed.getId());
        this.commandApplication.enable(enableFeedCommand);

        {
            Optional<Feed> feedOptional = this.queryApplication.findById(this.feed.getId());
            Assertions.assertTrue(feedOptional.isPresent());
            Feed feedToCheck = feedOptional.get();
            Assertions.assertTrue(feedToCheck.isEnable());
        }

    }

}