package com.geekhalo.like.infra.single.like;

import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaBasedLikeActionRepository
        extends LikeActionRepository, JpaRepository<LikeAction, Long> {

    @Override
    default LikeAction sync(LikeAction likeAction){
        return save(likeAction);
    }

    @Override
    @Query("select a from LikeAction a where a.user.userId = ?1 and a.target.type = ?2 and a.target.id = ?3")
    Optional<LikeAction> getByUserIdAndTarget(Long userId, String targetType, Long targetId);
}
