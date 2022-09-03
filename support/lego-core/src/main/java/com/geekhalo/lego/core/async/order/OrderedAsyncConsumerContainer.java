package com.geekhalo.lego.core.async.order;

import com.geekhalo.lego.annotation.async.AsyncForOrderedBasedRocketMQ;
import com.geekhalo.lego.core.async.support.AbstractAsyncConsumerContainer;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by taoli on 2022/9/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class OrderedAsyncConsumerContainer extends AbstractAsyncConsumerContainer {
    private final AsyncForOrderedBasedRocketMQ asyncForOrderedBasedRocketMQ;

    private DefaultMQPushConsumer orderConsumer;

    private long suspendCurrentQueueTimeMillis = 1000;

    public OrderedAsyncConsumerContainer(Environment environment,
                                            AsyncForOrderedBasedRocketMQ asyncForOrderedBasedRocketMQ,
                                            Object bean,
                                            Method method) {
        super(environment, bean, method);

        Preconditions.checkArgument(asyncForOrderedBasedRocketMQ != null);
        this.asyncForOrderedBasedRocketMQ = asyncForOrderedBasedRocketMQ;
    }

    @Override
    protected void doStart() {
        try {
            this.orderConsumer.start();
            log.info("success to start consumer {}", this.orderConsumer);
        } catch (MQClientException e) {
            log.error("failed to start rocketmq consumer {}", this.orderConsumer);
        }
    }

    @Override
    protected void doShutdown() {
        this.orderConsumer.shutdown();
        log.info("success to shutdown consumer {}", this.orderConsumer);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 构建 DefaultMQPushConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        this.orderConsumer = consumer;

        String nameServer = resolve(this.asyncForOrderedBasedRocketMQ.nameServer());
        consumer.setNamesrvAddr(nameServer);

        String group = resolve(this.asyncForOrderedBasedRocketMQ.consumerGroup());
        consumer.setConsumerGroup(group);

        String topic = resolve(this.asyncForOrderedBasedRocketMQ.topic());
        String tag = resolve(this.asyncForOrderedBasedRocketMQ.tag());
        this.orderConsumer.subscribe(topic, tag);

        this.orderConsumer.setMessageListener(new DefaultMessageListenerOrderly());

        log.info("success to subscribe  {}, topic {}, tag {}, group {}", nameServer, topic, tag, group);
    }

    public class DefaultMessageListenerOrderly implements MessageListenerOrderly {

        @SuppressWarnings("unchecked")
        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();
                    invokeMethod(messageExt);
                    long costTime = System.currentTimeMillis() - now;
                    log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);

                    if (skipWhenException()){
                        return ConsumeOrderlyStatus.SUCCESS;
                    }

                    context.setSuspendCurrentQueueTimeMillis(suspendCurrentQueueTimeMillis);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }
}
