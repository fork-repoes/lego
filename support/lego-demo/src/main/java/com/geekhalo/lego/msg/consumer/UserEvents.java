package com.geekhalo.lego.msg.consumer;

import lombok.Data;

/**
 * Created by taoli on 2022/10/21.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface UserEvents {

    interface UserEvent{
        Long getUserId();
    }

    @Data
    class UserCreatedEvent implements UserEvent{
        private Long userId;
        private String userName;
    }

    @Data
    class UserEnableEvent implements UserEvent{
        private Long userId;
        private String userName;
    }

    @Data
    class UserDisableEvent implements UserEvent{
        private Long userId;
        private String userName;
    }

    @Data
    class UserDeletedEvent implements UserEvent{
        private Long userId;
        private String userName;
    }
}
