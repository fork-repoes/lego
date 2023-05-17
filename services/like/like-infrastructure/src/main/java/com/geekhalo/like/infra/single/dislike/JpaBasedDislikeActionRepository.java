package com.geekhalo.like.infra.single.dislike;

import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedDislikeActionRepository
        extends DislikeActionRepository, JpaRepository<DislikeAction, Long> {

    @Override
    default DislikeAction sync(DislikeAction dislikeAction){
        return save(dislikeAction);
    }
}
