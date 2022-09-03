package com.geekhalo.lego.core.async.normal;

import com.geekhalo.lego.annotation.async.AsyncBasedRocketMQ;
import com.geekhalo.lego.core.async.support.AbstractAsyncConsumerContainer;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by taoli on 2022/9/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class NormalAsyncConsumerContainer
        extends AbstractAsyncConsumerContainer {
    private final AsyncBasedRocketMQ asyncBasedRocketMQ;

    private DefaultMQPushConsumer consumer;

    @Getter
    @Setter
    private int delayLevelWhenNextConsume = 0;

    protected NormalAsyncConsumerContainer(Environment environment,
                                     AsyncBasedRocketMQ asyncBasedRocketMQ,
                                     Object bean,
                                     Method method) {
        super(environment, bean, method);
        Preconditions.checkArgument(asyncBasedRocketMQ != null);

        this.asyncBasedRocketMQ = asyncBasedRocketMQ;
    }

    @Override
    protected void doStart() {
        try {
            this.consumer.start();
            log.info("success to start consumer {}", this.consumer);
        } catch (MQClientException e) {
            log.error("Failed to start consumer {}", this.consumer);
        }
    }

    @Override
    protected void doShutdown() {
        this.consumer.shutdown();
        log.info("success to shutdown consumer {}", this.consumer);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 构建 DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        this.consumer = consumer;

        String nameServer = resolve(this.asyncBasedRocketMQ.nameServer());
        consumer.setNamesrvAddr(nameServer);

        String group = resolve(this.asyncBasedRocketMQ.consumerGroup());
        consumer.setConsumerGroup(group);

        String topic = resolve(this.asyncBasedRocketMQ.topic());
        String tag = resolve(this.asyncBasedRocketMQ.tag());
        this.consumer.subscribe(topic, tag);

        this.consumer.setMessageListener(new DefaultMessageListenerConcurrently());

        log.info("success to subscribe  {}, topic {}, tag {}, group {}", nameServer, topic, tag, group);
    }

    public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {

        @SuppressWarnings("unchecked")
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();
                    invokeMethod(messageExt);
                    long costTime = System.currentTimeMillis() - now;
                    log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);
                    context.setDelayLevelWhenNextConsume(delayLevelWhenNextConsume);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
