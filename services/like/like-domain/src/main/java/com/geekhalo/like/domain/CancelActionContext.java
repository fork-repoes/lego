package com.geekhalo.like.domain;


public class CancelActionContext extends AbstractActionContext<CancelActionCommand>{
    protected CancelActionContext(){

    }


    public static CancelActionContext apply(CancelActionCommand command){
        CancelActionContext context =  new CancelActionContext();
        context.init(command);
        return context;
    }
}
