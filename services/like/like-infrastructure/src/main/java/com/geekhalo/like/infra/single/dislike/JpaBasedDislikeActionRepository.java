package com.geekhalo.like.infra.single.dislike;

import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaBasedDislikeActionRepository
        extends DislikeActionRepository, JpaRepository<DislikeAction, Long> {

    @Override
    default DislikeAction sync(DislikeAction dislikeAction){
        return save(dislikeAction);
    }

    @Override
    @Query("select a from DislikeAction a where a.user.userId = ?1 and a.target.type = ?2 and a.target.id = ?3")
    Optional<DislikeAction> getByUserIdAndTarget(Long userId, String targetType, Long targetId);

}
