package com.geekhalo.feed.infra;

import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoxItemsWrapperRepository extends JpaRepository<BoxItemsWrapper, Long> {
    Optional<BoxItemsWrapper> getFirstByFeedOwnerAndTypeAndMinScoreLessThanOrderByMinScoreDesc(FeedOwner feedOwner, BoxType boxType, Long score);
}
