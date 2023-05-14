package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.AbstractAction;
import com.geekhalo.like.domain.ActionTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LikeAction extends AbstractAction {

    public static LikeAction create(Long userId, ActionTarget target){
        LikeAction likeAction = new LikeAction();
        likeAction.init(userId, target);
        return likeAction;
    }
}
