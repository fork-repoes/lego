package com.geekhalo.tinyurl.infra.repository.cache;

import com.geekhalo.tinyurl.TestApplication;
import com.geekhalo.tinyurl.domain.*;
import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import org.apache.commons.lang3.RandomUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestApplication.class)
@Import(RedisBasedTinyUrlCache.class)
@ActiveProfiles("db-single")
class RedisBasedTinyUrlCacheTest {
    @Autowired
    private RedisBasedTinyUrlCache cache;
    @Autowired
    private NumberGenerator numberGenerator;

    private TinyUrl tinyUrl;
    private String url;
    private Integer maxTime;

    @BeforeEach
    void setUp() {
        this.url = "http://" + RandomUtils.nextLong() + ".com";
        this.maxTime = RandomUtils.nextInt(5, 10);
        CreateLimitTimeTinyUrlCommand command = new CreateLimitTimeTinyUrlCommand();
        command.setUrl(url);
        command.setMaxCount(maxTime);
        CreateLimitTimeTinyUrlContext context = CreateLimitTimeTinyUrlContext.create(command);
        context.setNumberGenerator(this.numberGenerator);
        this.tinyUrl = TinyUrl.createLimitTimeTinyUrl(context);
        this.cache.put(tinyUrl);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findById() {
        Optional<TinyUrl> byId = this.cache.findById(this.tinyUrl.getId());
        Assert.assertTrue(byId.isPresent());
        TinyUrl tinyUrl = byId.get();
        Assert.assertEquals(TinyUrlType.LIMIT_TIME, tinyUrl.getType());
        Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrl.getStatus());
        Assert.assertEquals(url, tinyUrl.getUrl());
        Assert.assertEquals(this.maxTime, tinyUrl.getMaxCount());
        Assert.assertEquals(0, tinyUrl.getAccessCount().intValue());
    }

    @Test
    void put() {
    }

    @Test
    void incrAccessCount() {
        {
            Optional<TinyUrl> byId = this.cache.findById(this.tinyUrl.getId());
            Assert.assertTrue(byId.isPresent());
            TinyUrl tinyUrl = byId.get();
            Assert.assertEquals(TinyUrlType.LIMIT_TIME, tinyUrl.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrl.getStatus());
            Assert.assertEquals(url, tinyUrl.getUrl());
            Assert.assertEquals(this.maxTime, tinyUrl.getMaxCount());
            Assert.assertEquals(0, tinyUrl.getAccessCount().intValue());
        }
        this.cache.incrAccessCount(this.tinyUrl.getId(), 1);
        {
            Optional<TinyUrl> byId = this.cache.findById(this.tinyUrl.getId());
            Assert.assertTrue(byId.isPresent());
            TinyUrl tinyUrl = byId.get();
            Assert.assertEquals(TinyUrlType.LIMIT_TIME, tinyUrl.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrl.getStatus());
            Assert.assertEquals(url, tinyUrl.getUrl());
            Assert.assertEquals(this.maxTime, tinyUrl.getMaxCount());
            Assert.assertEquals(1, tinyUrl.getAccessCount().intValue());
        }

        this.cache.incrAccessCount(this.tinyUrl.getId(), 1);
        {
            Optional<TinyUrl> byId = this.cache.findById(this.tinyUrl.getId());
            Assert.assertTrue(byId.isPresent());
            TinyUrl tinyUrl = byId.get();
            Assert.assertEquals(TinyUrlType.LIMIT_TIME, tinyUrl.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrl.getStatus());
            Assert.assertEquals(url, tinyUrl.getUrl());
            Assert.assertEquals(this.maxTime, tinyUrl.getMaxCount());
            Assert.assertEquals(2, tinyUrl.getAccessCount().intValue());
        }

        this.cache.incrAccessCount(this.tinyUrl.getId(), 1);
        {
            Optional<TinyUrl> byId = this.cache.findById(this.tinyUrl.getId());
            Assert.assertTrue(byId.isPresent());
            TinyUrl tinyUrl = byId.get();
            Assert.assertEquals(TinyUrlType.LIMIT_TIME, tinyUrl.getType());
            Assert.assertEquals(TinyUrlStatus.ENABLE, tinyUrl.getStatus());
            Assert.assertEquals(url, tinyUrl.getUrl());
            Assert.assertEquals(this.maxTime, tinyUrl.getMaxCount());
            Assert.assertEquals(3, tinyUrl.getAccessCount().intValue());
        }
    }

    @Test
    void incrAccessCount_NotExist() {
        Long id = RandomUtils.nextLong();

        this.cache.incrAccessCount(id, 1);
        Optional<TinyUrl> byId = this.cache.findById(id);
        Assert.assertFalse(byId.isPresent());
    }

}