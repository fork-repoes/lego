package com.geekhalo.like.domain;


public class CancelByIdActionCommand extends AbstractByIdActionCommand {
    protected CancelByIdActionCommand(){

    }


    public static CancelByIdActionCommand apply(Long userId, String targetType, Long targetId){
        CancelByIdActionCommand context =  new CancelByIdActionCommand();
        context.init(userId, targetType, targetId);
        return context;
    }
}
