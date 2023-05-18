package com.geekhalo.like.domain;

public class MarkActionContext extends AbstractActionContext{
    private MarkActionContext(Long userId, String targetType, Long targetId) {
        super(userId, targetType, targetId);
    }

    public static MarkActionContext apply(Long userId, String targetType, Long targetId){
        return new MarkActionContext(userId, targetType, targetId);
    }
}
