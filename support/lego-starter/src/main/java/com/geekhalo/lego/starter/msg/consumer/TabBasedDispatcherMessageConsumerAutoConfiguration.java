package com.geekhalo.lego.starter.msg.consumer;

import com.geekhalo.lego.core.msg.consumer.TagBasedDispatcherConsumerContainerRegistry;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by taoli on 2022/10/21.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@ConditionalOnBean(RocketMQTemplate.class)
public class TabBasedDispatcherMessageConsumerAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public TagBasedDispatcherConsumerContainerRegistry tagBasedDispatcherConsumerContainerRegistry(){
        return new TagBasedDispatcherConsumerContainerRegistry(environment);
    }
}
