package com.geekhalo.like.domain;

public class MarkActionCommand extends AbstractActionCommand{
    protected MarkActionCommand(){

    }


    public static MarkActionCommand apply(Long userId, String targetType, Long targetId){
        MarkActionCommand context = new MarkActionCommand();
        context.init(userId, targetType, targetId);
        return context;
    }
}
