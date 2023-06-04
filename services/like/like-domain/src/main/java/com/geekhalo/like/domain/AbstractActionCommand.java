package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.Command;
import com.geekhalo.lego.core.command.CommandForUpdate;
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
public abstract class AbstractActionCommand implements CommandForUpdate{
    private Long userId;
    private String targetType;
    private Long targetId;

    protected void init(Long userId, String targetType, Long targetId) {
        Preconditions.checkArgument(userId != null);
        Preconditions.checkArgument(StringUtils.isNotEmpty(targetType));
        Preconditions.checkArgument(targetType != null);
        this.userId = userId;
        this.targetType = targetType;
        this.targetId = targetId;
    }

}
