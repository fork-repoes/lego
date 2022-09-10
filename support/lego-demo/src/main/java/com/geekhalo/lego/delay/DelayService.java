package com.geekhalo.lego.delay;

import com.geekhalo.lego.annotation.delay.DelayBasedRocketMQ;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoli on 2022/9/4.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Service
@Slf4j
public class DelayService {
    @Getter
    private final List<DelayTask> tasks = new ArrayList<>();

    @DelayBasedRocketMQ(
            topic = "${cancelOrder.delay.topic}",
            tag = "delayCancelOrder",
            consumerGroup = "${cancelOrder.delay.consumerGroup1}",
            delayLevel = 2
    )
    public void delayCancelOrder(Long orderId, String reason){
        DelayTask delayTask = new DelayTask(orderId, reason, null);
        log.info("Run Cancel Order {}", delayTask);
        this.tasks.add(delayTask);
    }


    @DelayBasedRocketMQ(
            topic = "${cancelOrder.delay.topic}",
            tag = "delayCancelOrderForTimeout",
            consumerGroup = "${cancelOrder.delay.consumerGroup2}",
            delayLevelSpEl = "#timeOutLevel"
    )
    public void delayCancelOrderForTimeout(Long orderId, String reason, int timeOutLevel){
        DelayTask delayTask = new DelayTask(orderId, reason, timeOutLevel);
        log.info("Run Cancel Order {}", delayTask);
        this.tasks.add(delayTask);
    }

    public void clean() {
        this.tasks.clear();;
    }

    @Value
    static class DelayTask{
        private final Long orderId;
        private final String reason;
        private final Integer timeOutLevel;
    }
}
