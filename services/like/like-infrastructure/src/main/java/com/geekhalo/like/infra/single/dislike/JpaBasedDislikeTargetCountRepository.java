package com.geekhalo.like.infra.single.dislike;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.dislike.DislikeTargetCountRepository;
import com.geekhalo.like.domain.target.ActionTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaBasedDislikeTargetCountRepository
    extends DislikeTargetCountRepository, JpaRepository<DislikeTargetCount, Long> {

    @Override
    default DislikeTargetCount sync(DislikeTargetCount dislikeTargetCount){
        return save(dislikeTargetCount);
    }

//    @Query("select c from DislikeTargetCount c where c.target.type = ?1 and c.target.id = ?2")
//    Optional<DislikeTargetCount> getByTarget(String targetType, Long targetId);

    @Override
    @Modifying
    @Query("update DislikeTargetCount c set c.count = c.count + ?2 where c.target = ?1")
    void incr(ActionTarget target, long count);



}
