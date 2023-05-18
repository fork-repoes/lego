package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.like.domain.target.ActionTarget;

import java.util.Optional;

public interface AbstractActionRepository<A extends AbstractAction> extends CommandRepository<A, Long> {
    Optional<A> getByUserIdAndTarget(Long userId, ActionTarget target);
}
