package com.geekhalo.like.domain;


public class CancelActionContext extends AbstractActionContext{
    protected CancelActionContext(){

    }


    public static CancelActionContext apply(Long userId, String targetType, Long targetId){
        CancelActionContext context =  new CancelActionContext();
        context.init(userId, targetType, targetId);
        return context;
    }
}
