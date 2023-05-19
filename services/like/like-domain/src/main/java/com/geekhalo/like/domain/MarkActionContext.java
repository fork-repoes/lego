package com.geekhalo.like.domain;

public class MarkActionContext extends AbstractActionContext{
    protected MarkActionContext(){

    }


    public static MarkActionContext apply(Long userId, String targetType, Long targetId){
        MarkActionContext context = new MarkActionContext();
        context.init(userId, targetType, targetId);
        return context;
    }
}
