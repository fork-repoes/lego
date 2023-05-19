package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.CommandRepository;

import java.util.Optional;

public interface AbstractTargetCountRepository<A extends AbstractTargetCount>
    extends CommandRepository<A, Long> {
    Optional<A> getByTarget(String targetType, Long targetId);
    void incr(String targetType, Long targetId, long count);
}
