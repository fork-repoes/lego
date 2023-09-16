package com.geekhalo.feed;


import com.geekhalo.feed.domain.box.BoxDao;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.feed.domain.feed.OwnerType;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest(classes = Application.class)
class JpaBasedBoxDaoTest {
    @Autowired
    private BoxDao boxDao;
    private FeedOwner feedOwner;
    private BoxType boxType;
    private List<FeedIndex> feedIndexList;
    private int allData = 180;

    @BeforeEach
    void setUp() {
        this.feedOwner = new FeedOwner(OwnerType.USER, RandomUtils.nextLong());
        this.boxType = BoxType.TEST_BOX;
        this.feedIndexList = Lists.newArrayList();
        for (int i = 0; i < allData ; i ++) {
            FeedIndex feedIndex = FeedIndex.builder()
                    .feedId(i + 1L)
                    .score(i + 1L)
                    .build();
            this.feedIndexList.add(feedIndex);
        }

        boxDao.append(feedOwner, boxType, feedIndexList);

//        for (FeedIndex feedIndex : this.feedIndexList) {
//            boxDao.append(this.feedOwner, this.boxType, feedIndex);
//        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void append() {
    }

    @Test
    void load() {
        int realSize = this.allData;
        Long score = Long.MAX_VALUE;
        Set<FeedIndex> allFeedIndexList = Sets.newHashSet();
        List<FeedIndex> load = null;
        int batch = 0;
        do {
            load = boxDao.load(this.feedOwner, this.boxType, score, 10);
            score = load.stream()
                    .mapToLong(Feed -> Feed.getScore())
                    .min()
                    .orElse(0);
            batch ++;
            allFeedIndexList.addAll(load);
        }while (CollectionUtils.isNotEmpty(load));

        Assertions.assertEquals(realSize / 10 + 1, batch);

        Assertions.assertEquals(Sets.newHashSet(this.feedIndexList), allFeedIndexList);
    }
}