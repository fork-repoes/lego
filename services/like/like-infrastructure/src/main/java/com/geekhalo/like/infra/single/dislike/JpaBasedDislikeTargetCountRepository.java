package com.geekhalo.like.infra.single.dislike;

import com.geekhalo.like.domain.ActionTarget;
import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.dislike.DislikeTargetCountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaBasedDislikeTargetCountRepository
    extends DislikeTargetCountRepository, JpaRepository<DislikeTargetCount, Long> {

    @Modifying
    @Query("update DislikeTargetCount set count = count + ?2 where target = ?1")
    void incr(ActionTarget target, int count);
}
