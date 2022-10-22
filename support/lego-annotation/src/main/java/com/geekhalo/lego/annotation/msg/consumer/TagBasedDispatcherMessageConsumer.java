package com.geekhalo.lego.annotation.msg.consumer;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by taoli on 2022/10/21.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface TagBasedDispatcherMessageConsumer {
    /**
     * Topic
     * @return
     */
    String topic();

    /**
     * 消费者组名称
     * @return
     */
    String consumer();

    /**
     * 命名服务
     * @return
     */
    String nameServer() default "${rocketmq.name-server:}";

}
