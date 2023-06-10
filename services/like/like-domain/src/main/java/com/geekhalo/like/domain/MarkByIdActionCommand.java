package com.geekhalo.like.domain;

public class MarkByIdActionCommand extends AbstractByIdActionCommand {
    protected MarkByIdActionCommand(){

    }


    public static MarkByIdActionCommand apply(Long userId, String targetType, Long targetId){
        MarkByIdActionCommand context = new MarkByIdActionCommand();
        context.init(userId, targetType, targetId);
        return context;
    }
}
