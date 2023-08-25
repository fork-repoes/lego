package com.geekhalo;


import com.geekhalo.feed.Application;
import com.geekhalo.feed.domain.box.BoxCache;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.feed.domain.feed.OwnerType;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Application.class)
class RedisBasedBoxCacheTest {
    @Autowired
    private BoxCache boxCache;
    private FeedOwner feedOwner;
    private BoxType boxType;
    private List<FeedIndex> feedIndexList;
    private int allData = 1500;

    @BeforeEach
    void setUp() {
        this.feedOwner = new FeedOwner(OwnerType.USER, 100L);
        this.boxType = BoxType.IN_BOX;
        this.feedIndexList = Lists.newArrayList();
        for (int i = 0; i < allData ; i ++) {
            FeedIndex feedIndex = FeedIndex.builder()
                    .feedId(i + 1L)
                    .score(i + 1L)
                    .build();
            this.feedIndexList.add(feedIndex);
        }
        for (FeedIndex feedIndex : this.feedIndexList) {
            boxCache.append(this.feedOwner, this.boxType, feedIndex);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void append() {
    }

    @Test
    void load() {
        int realSize = Math.min(this.allData, this.boxType.getMaxCacheLength());
        Long score = Long.MAX_VALUE;
        List<FeedIndex> load = null;
        int batch = 0;
        do {
            load = boxCache.load(this.feedOwner, this.boxType, score, 10);
            score = load.stream()
                    .mapToLong(Feed -> Feed.getScore())
                    .min()
                    .orElse(0);
            batch ++;
        }while (CollectionUtils.isNotEmpty(load));

        Assertions.assertEquals(realSize / 10 + 1, batch);
    }
}