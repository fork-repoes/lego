package com.geekhalo.like.domain;

public class MarkActionContext extends AbstractActionContext<MarkByIdActionCommand>{
    protected MarkActionContext(){

    }


    public static MarkActionContext apply(MarkByIdActionCommand command){
        MarkActionContext context = new MarkActionContext();
        context.init(command);
        return context;
    }
}
