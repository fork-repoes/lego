package com.geekhalo.like.domain;


public class CancelActionContext extends AbstractActionContext{
    private CancelActionContext(Long userId, String targetType, Long targetId) {
        super(userId, targetType, targetId);
    }

    public static CancelActionContext apply(Long userId, String targetType, Long targetId){
        return new CancelActionContext(userId, targetType, targetId);
    }
}
