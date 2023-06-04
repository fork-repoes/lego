package com.geekhalo.like.domain;

public class MarkActionContext extends AbstractActionContext<MarkActionCommand>{
    protected MarkActionContext(){

    }


    public static MarkActionContext apply(MarkActionCommand command){
        MarkActionContext context = new MarkActionContext();
        context.init(command);
        return context;
    }
}
