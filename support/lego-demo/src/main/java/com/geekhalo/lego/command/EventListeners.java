package com.geekhalo.lego.command;

import lombok.Getter;
import org.assertj.core.util.Lists;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.List;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
@Getter
public class EventListeners {
    private List<Object> events = Lists.newArrayList();

    @EventListener
    public void onEvent(OrderEvent event){
        this.events.add(event);
    }

    public void clear(){
        this.events.clear();
    }
}
