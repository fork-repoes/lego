package com.geekhalo.lego.msg.consumer;

import com.geekhalo.lego.annotation.msg.consumer.HandleTag;
import com.geekhalo.lego.annotation.msg.consumer.TagBasedDispatcherMessageConsumer;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/21.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@TagBasedDispatcherMessageConsumer(
        topic = "consumer-test-topic",
        consumer = "user-message-consumer"
)
public class UserMessageConsumer {
    private final Map<Long, List<UserEvents.UserEvent>> events = Maps.newHashMap();

    public void clean(){
        this.events.clear();;
    }

    public List<UserEvents.UserEvent> getUserEvents(Long userId){
        return this.events.get(userId);
    }

    @HandleTag("UserCreatedEvent")
    public void handle(UserEvents.UserCreatedEvent userCreatedEvent){
        List<UserEvents.UserEvent> userEvents = this.events.computeIfAbsent(userCreatedEvent.getUserId(), userId -> new ArrayList<>());
        userEvents.add(userCreatedEvent);
    }

    @HandleTag("UserEnableEvent")
    public void handle(UserEvents.UserEnableEvent userEnableEvent){
        List<UserEvents.UserEvent> userEvents = this.events.computeIfAbsent(userEnableEvent.getUserId(), userId -> new ArrayList<>());
        userEvents.add(userEnableEvent);
    }

    @HandleTag("UserDisableEvent")
    public void handle(UserEvents.UserDisableEvent userDisableEvent){
        List<UserEvents.UserEvent> userEvents = this.events.computeIfAbsent(userDisableEvent.getUserId(), userId -> new ArrayList<>());
        userEvents.add(userDisableEvent);
    }

    @HandleTag("UserDeletedEvent")
    public void handle(UserEvents.UserDeletedEvent userDeletedEvent){
        List<UserEvents.UserEvent> userEvents = this.events.computeIfAbsent(userDeletedEvent.getUserId(), userId -> new ArrayList<>());
        userEvents.add(userDeletedEvent);
    }
}
