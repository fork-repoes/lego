package com.geekhalo.like.api.event;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by taoli on 2023/5/20.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@Setter(AccessLevel.PRIVATE)
abstract class AbstractActionEvent {
    private Long userId;

    private String targetType;

    private Long targetId;

    protected void init(Long userId, String targetType, Long targetId){
        Preconditions.checkArgument(userId != null);
        Preconditions.checkArgument(StringUtils.isNoneBlank(targetType));
        Preconditions.checkArgument(targetId != null);

        setUserId(userId);
        setTargetType(targetType);
        setTargetId(targetId);
    }
}
