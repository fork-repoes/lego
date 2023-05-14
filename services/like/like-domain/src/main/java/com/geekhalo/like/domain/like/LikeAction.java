package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.AbstractAction;
import com.geekhalo.like.domain.AbstractCancelledEvent;
import com.geekhalo.like.domain.AbstractMarkedEvent;
import com.geekhalo.like.domain.ActionTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "like_action")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LikeAction extends AbstractAction {

    public static LikeAction create(Long userId, ActionTarget target){
        LikeAction likeAction = new LikeAction();
        likeAction.init(userId, target);
        return likeAction;
    }

    @Override
    protected AbstractCancelledEvent<? extends AbstractAction> createCancelledEvent() {
        return LikeCancelledEvent.apply(this);
    }

    @Override
    protected AbstractMarkedEvent<? extends AbstractAction> createMarkedEvent() {
        return LikeMarkedEvent.apply(this);
    }
}
