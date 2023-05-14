package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.AbstractAction;
import com.geekhalo.like.domain.AbstractCancelledEvent;
import com.geekhalo.like.domain.AbstractMarkedEvent;
import com.geekhalo.like.domain.ActionTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DislikeAction extends AbstractAction {

    public static DislikeAction create(Long userId, ActionTarget target){
        DislikeAction dislikeAction = new DislikeAction();
        dislikeAction.init(userId, target);
        return dislikeAction;
    }

    @Override
    protected AbstractCancelledEvent<? extends AbstractAction> createCancelledEvent() {
        return DislikeCancelledEvent.apply(this);
    }

    @Override
    protected AbstractMarkedEvent<? extends AbstractAction> createMarkedEvent() {
        return DislikeMarkedEvent.apply(this);
    }
}
