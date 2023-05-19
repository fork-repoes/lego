package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.like.domain.target.ActionTarget;

import java.util.Optional;

public interface AbstractTargetCountRepository<A extends AbstractTargetCount>
    extends CommandRepository<A, Long> {

    Optional<A> getByTarget(ActionTarget target);

    void incr(ActionTarget target, long count);
}
