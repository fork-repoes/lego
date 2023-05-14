package com.geekhalo.message.infra.single.Like;

import com.geekhalo.like.domain.ActionTarget;
import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.domain.like.LikeTargetCountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaBasedLikeTargetCountRepository
    extends LikeTargetCountRepository, JpaRepository<LikeTargetCount, Long> {

    @Modifying
    @Query("update LikeTargetCount set count = count + ?2 where target = ?1")
    void incr(ActionTarget target, int count);
}
