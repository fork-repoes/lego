package com.geekhalo.like.domain;


public class CancelActionCommand extends AbstractActionCommand{
    protected CancelActionCommand(){

    }


    public static CancelActionCommand apply(Long userId, String targetType, Long targetId){
        CancelActionCommand context =  new CancelActionCommand();
        context.init(userId, targetType, targetId);
        return context;
    }
}
