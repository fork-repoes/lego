package com.geekhalo.like.domain;


public class CancelActionContext extends AbstractActionContext<CancelByIdActionCommand>{
    protected CancelActionContext(){

    }


    public static CancelActionContext apply(CancelByIdActionCommand command){
        CancelActionContext context =  new CancelActionContext();
        context.init(command);
        return context;
    }
}
