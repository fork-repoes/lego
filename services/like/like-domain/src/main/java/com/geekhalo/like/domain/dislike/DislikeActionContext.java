package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.CancelActionContext;
import com.geekhalo.like.domain.MarkActionContext;
import com.geekhalo.like.domain.like.LikeActionCommand;

public class DislikeActionContext extends MarkActionContext<DislikeActionCommand> {
    protected DislikeActionContext(){

    }


    public static DislikeActionContext apply(DislikeActionCommand command){
        DislikeActionContext context = new DislikeActionContext();
        context.init(command);
        return context;
    }
}
