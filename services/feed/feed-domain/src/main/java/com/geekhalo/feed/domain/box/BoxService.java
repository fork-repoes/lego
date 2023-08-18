package com.geekhalo.feed.domain.box;

import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;

import java.util.List;

public interface BoxService {
    void appendInbox(FeedOwner feedOwner, FeedIndex feedIndex);

    List<FeedIndex> queryInboxByScore(FeedOwner feedOwner, Long score, Long size);

    void appendOutbox(FeedOwner feedOwner, FeedIndex feedIndex);

    List<FeedIndex> queryOutboxByScore(FeedOwner feedOwner, Long score, Long size);
}
