package com.geekhalo.like.infra.single.like;

import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.domain.like.LikeTargetCountRepository;
import com.geekhalo.like.domain.target.ActionTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaBasedLikeTargetCountRepository
    extends LikeTargetCountRepository, JpaRepository<LikeTargetCount, Long> {

    @Override
    default LikeTargetCount sync(LikeTargetCount likeTargetCount){
        return save(likeTargetCount);
    }

    @Modifying
    @Query("update LikeTargetCount c set c.count = c.count + ?2 where c.target = ?1")
    void incr(ActionTarget target, long count);
}
