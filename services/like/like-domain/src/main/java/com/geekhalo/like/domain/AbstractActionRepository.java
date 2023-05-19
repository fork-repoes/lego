package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.CommandRepository;

import java.util.Optional;

public interface AbstractActionRepository<A extends AbstractAction> extends CommandRepository<A, Long> {
    Optional<A> getByUserIdAndTarget(Long userId, String targetType, Long targetId);
}
