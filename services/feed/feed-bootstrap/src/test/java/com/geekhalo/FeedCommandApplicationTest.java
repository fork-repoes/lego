package com.geekhalo;

import com.geekhalo.feed.Application;
import com.geekhalo.feed.app.FeedCommandApplication;
import com.geekhalo.feed.app.FeedQueryApplication;
import com.geekhalo.feed.domain.feed.*;
import com.geekhalo.feed.domain.feed.create.CreateFeedCommand;
import com.geekhalo.feed.domain.feed.disable.DisableFeedCommand;
import com.geekhalo.feed.domain.feed.enable.EnableFeedCommand;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest(classes = Application.class)
class FeedCommandApplicationTest {
    @Autowired
    private FeedCommandApplication commandApplication;

    @Autowired
    private FeedQueryApplication queryApplication;

    private FeedOwner feedOwner;
    private FeedData feedData;
    private Feed feed;


    @BeforeEach
    void setUp() {
        this.feedOwner = new FeedOwner(OwnerType.USER, RandomUtils.nextLong());
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
    void status() {
        {
            Optional<Feed> feedOptional = this.queryApplication.findById(this.feed.getId());
            Assertions.assertTrue(feedOptional.isPresent());
            Feed feedToCheck = feedOptional.get();
            Assertions.assertTrue(feedToCheck.isEnable());
        }

        DisableFeedCommand disableFeedCommand = new DisableFeedCommand();
        disableFeedCommand.setId(this.feed.getId());
        this.commandApplication.diable(disableFeedCommand);

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