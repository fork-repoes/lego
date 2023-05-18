package com.geekhalo.like.domain;

import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.target.LoadActionTargetByTarget;
import com.geekhalo.like.domain.user.ActionUser;
import com.geekhalo.like.domain.user.LoadActionUserByUserId;
import lombok.Getter;

@Getter
public abstract class AbstractActionContext {
    private final Long userId;
    private final String targetType;
    private final Long targetId;

    @LoadActionUserByUserId(userId = "userId")
    private ActionUser actionUser;

    @LoadActionTargetByTarget(targetType = "targetType", targetId = "targetId")
    private ActionTarget actionTarget;

    protected AbstractActionContext(Long userId, String targetType, Long targetId) {
        this.userId = userId;
        this.targetType = targetType;
        this.targetId = targetId;
    }
}
