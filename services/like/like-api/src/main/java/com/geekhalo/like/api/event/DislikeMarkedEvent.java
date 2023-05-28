package com.geekhalo.like.api.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by taoli on 2023/5/20.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DislikeMarkedEvent extends AbstractActionEvent{
    public static final String TAG = "DislikeMarkedEvent";
    public static DislikeMarkedEvent apply(Long userId, String targetType, Long targetId){
        DislikeMarkedEvent event = new DislikeMarkedEvent();
        event.init(userId, targetType, targetId);
        return event;
    }
}
