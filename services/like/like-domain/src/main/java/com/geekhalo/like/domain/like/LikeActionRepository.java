package com.geekhalo.like.domain.like;

import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.like.domain.ActionTarget;

import java.util.Optional;

public interface LikeActionRepository extends CommandRepository<LikeAction, Long> {
    Optional<LikeAction> getByUserIdAndTarget(Long userId, ActionTarget target);
}
