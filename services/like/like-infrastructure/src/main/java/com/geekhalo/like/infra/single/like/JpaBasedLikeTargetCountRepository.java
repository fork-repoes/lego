package com.geekhalo.like.infra.single.like;

import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.domain.like.LikeTargetCountRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaBasedLikeTargetCountRepository
    extends LikeTargetCountRepository, JpaRepository<LikeTargetCount, Long> {

    @Override
    default LikeTargetCount sync(LikeTargetCount likeTargetCount){
        return save(likeTargetCount);
    }

    @Query("select c from LikeTargetCount c where c.target.type = ?1 and c.target.id = ?2")
    Optional<LikeTargetCount> getByTarget(String targetType, Long targetId);

    @Modifying
    @Query("update LikeTargetCount c set c.count = c.count + ?3 where c.target.type = ?1 and c.target.id = ?2")
    void incr(String targetType, Long targetId, long count);

}
