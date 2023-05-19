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

}
