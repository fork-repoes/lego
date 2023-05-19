package com.geekhalo.like.domain;

import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.target.LoadActionTargetByTarget;
import com.geekhalo.like.domain.user.ActionUser;
import com.geekhalo.like.domain.user.LoadActionUserByUserId;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractActionContext {
    private Long userId;
    private String targetType;
    private Long targetId;

    @LoadActionUserByUserId(userId = "userId")
    private ActionUser actionUser;

    @LoadActionTargetByTarget(targetType = "targetType", targetId = "targetId")
    private ActionTarget actionTarget;

    protected void init(Long userId, String targetType, Long targetId) {
        Preconditions.checkArgument(userId != null);
        Preconditions.checkArgument(StringUtils.isNotEmpty(targetType));
        Preconditions.checkArgument(targetType != null);
        this.userId = userId;
        this.targetType = targetType;
        this.targetId = targetId;
    }

}
