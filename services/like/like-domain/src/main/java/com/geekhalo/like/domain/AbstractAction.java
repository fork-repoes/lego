package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.support.AbstractAggRoot;
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
    }

    public void cancel(){
        if (getStatus() != ActionStatus.INVALID) {
            setStatus(ActionStatus.INVALID);
            addEvent(createCancelledEvent());
        }
    }

    public void mark(){
        if (getStatus() != ActionStatus.VALID) {
            setStatus(ActionStatus.VALID);
            addEvent(createMarkedEvent());
        }
    }

    protected abstract AbstractCancelledEvent<? extends AbstractAction> createCancelledEvent();

    protected abstract AbstractMarkedEvent<? extends AbstractAction> createMarkedEvent();

}
