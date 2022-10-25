package com.geekhalo.lego.msg.consumer;

import com.geekhalo.lego.annotation.msg.consumer.HandleTag;
import com.geekhalo.lego.annotation.msg.consumer.TagBasedDispatcherMessageConsumer;

/**
 * Created by taoli on 2022/10/21.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@TagBasedDispatcherMessageConsumer(
        topic = "consumer-test-topic-2",
        consumer = "user-message-consumer-2"
)
public class AnnotationBasedUserMessageConsumer extends UserMessageConsumer{

    @HandleTag("UserCreatedEvent")
    public void handle(UserEvents.UserCreatedEvent userCreatedEvent){
        super.handle(userCreatedEvent);
    }

    @HandleTag("UserEnableEvent")
    public void handle(UserEvents.UserEnableEvent userEnableEvent){
        super.handle(userEnableEvent);
    }

    @HandleTag("UserDisableEvent")
    public void handle(UserEvents.UserDisableEvent userDisableEvent){
        super.handle(userDisableEvent);
    }

    @HandleTag("UserDeletedEvent")
    public void handle(UserEvents.UserDeletedEvent userDeletedEvent){
        super.handle(userDeletedEvent);
    }
}
