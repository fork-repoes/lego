package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.AbstractAction;
import com.geekhalo.like.domain.AbstractActionContext;
import com.geekhalo.like.domain.AbstractCancelledEvent;
import com.geekhalo.like.domain.AbstractMarkedEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dislike_action")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DislikeAction extends AbstractAction {

    public static DislikeAction create(AbstractActionContext context){
        DislikeAction dislikeAction = new DislikeAction();
        dislikeAction.init(context);
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
