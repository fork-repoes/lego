package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.AbstractAction;
import com.geekhalo.like.domain.AbstractActionContext;
import com.geekhalo.like.domain.AbstractCancelledEvent;
import com.geekhalo.like.domain.AbstractMarkedEvent;
import com.geekhalo.like.domain.like.LikeActionContext;
import com.geekhalo.like.domain.like.LikeCancelledEvent;
import com.geekhalo.like.domain.like.LikeMarkedEvent;
import com.geekhalo.like.domain.like.UnlikeActionContext;
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

    public static DislikeAction create(DislikeActionContext context){
        DislikeAction dislikeAction = new DislikeAction();
        dislikeAction.init(context);
        return dislikeAction;
    }

    public void like(DislikeActionContext context){
        if (mark()){
            addEvent(DislikeMarkedEvent.apply(this));
        }
    }

    public void unlike(UndislikeActionContext context){
        if (cancel()){
            addEvent(DislikeCancelledEvent.apply(this));
        }
    }
}
