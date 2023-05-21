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
public class LikeCancelledEvent extends AbstractActionEvent {
    public static final String TAG = "LikeCancelledEvent";

    public static LikeCancelledEvent apply(Long userId, String targetType, Long targetId){
        LikeCancelledEvent event = new LikeCancelledEvent();
        event.init(userId, targetType, targetId);
        return event;
    }
}
