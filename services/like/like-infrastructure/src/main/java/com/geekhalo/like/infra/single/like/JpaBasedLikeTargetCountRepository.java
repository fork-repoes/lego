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

//    @Query("select c from LikeTargetCount c where c.target.type = ?1 and c.target.id = ?2")
//    Optional<LikeTargetCount> getByTarget(String targetType, Long targetId);

    @Override
    @Modifying
    @Query("update LikeTargetCount c set c.count = c.count + ?2 where c.target = ?1")
    void incr(ActionTarget target, long count);

}
