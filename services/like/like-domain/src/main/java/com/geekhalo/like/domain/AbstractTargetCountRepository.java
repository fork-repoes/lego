package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.CommandRepository;

public interface AbstractTargetCountRepository<A extends AbstractTargetCount>
    extends CommandRepository<A, Long> {
}
