package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.AbstractAggRoot;
import com.google.common.base.Preconditions;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractAction extends AbstractAggRoot {
    private Long userId;

    private ActionTarget target;

    private ActionStatus status;

    protected void init(Long userId, ActionTarget target){
        Preconditions.checkArgument(userId != null);
        Preconditions.checkArgument(target != null);
        setUserId(userId);
        setTarget(target);
        mark();
    }

    public void cancel(){
        setStatus(ActionStatus.INVALID);
    }

    public void mark(){
        setStatus(ActionStatus.VALID);
    }

}
