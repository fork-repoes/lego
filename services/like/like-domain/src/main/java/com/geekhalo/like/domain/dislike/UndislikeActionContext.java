package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.AbstractActionContext;
import com.geekhalo.like.domain.CancelActionContext;
import com.geekhalo.like.domain.like.UnlikeActionCommand;

public class UndislikeActionContext extends CancelActionContext<UndislikeActionCommand> {
    protected UndislikeActionContext(){

    }


    public static UndislikeActionContext apply(UndislikeActionCommand command){
        UndislikeActionContext context = new UndislikeActionContext();
        context.init(command);
        return context;
    }
}
