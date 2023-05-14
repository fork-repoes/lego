package com.geekhalo.like.domain.dislike;

import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.like.domain.ActionTarget;

import java.util.Optional;

public interface DislikeActionRepository extends CommandRepository<DislikeAction, Long> {
    Optional<DislikeAction> getByUserIdAndTarget(Long user, ActionTarget target);
}
