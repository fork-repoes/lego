package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.AbstractAction;
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
}
