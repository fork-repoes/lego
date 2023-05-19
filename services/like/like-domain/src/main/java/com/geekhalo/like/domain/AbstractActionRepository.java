package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.user.ActionUser;

import java.util.Optional;

public interface AbstractActionRepository<A extends AbstractAction> extends CommandRepository<A, Long> {
    Optional<A> getByUserAndTarget(ActionUser user, ActionTarget target);
}
