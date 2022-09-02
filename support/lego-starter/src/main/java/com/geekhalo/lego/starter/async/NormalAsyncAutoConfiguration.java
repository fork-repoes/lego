package com.geekhalo.lego.starter.async;

import com.geekhalo.lego.annotation.async.AsyncBasedRocketMQ;
import com.geekhalo.lego.core.async.normal.NormalAsyncConsumerContainerRegistry;
import com.geekhalo.lego.core.async.normal.NormalAsyncInterceptor;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.env.Environment;

/**
 * Created by taoli on 2022/9/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@ConditionalOnBean(RocketMQTemplate.class)
public class NormalAsyncAutoConfiguration {
    @Autowired
    private Environment environment;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Bean
    public NormalAsyncInterceptor asyncInterceptor(){
        return new NormalAsyncInterceptor(this.environment, this.rocketMQTemplate);
    }

    @Bean
    public NormalAsyncConsumerContainerRegistry asyncConsumerContainerRegistry(){
        return new NormalAsyncConsumerContainerRegistry(environment);
    }

    @Bean
    public PointcutAdvisor asyncPointcutAdvisor(@Autowired NormalAsyncInterceptor sendMessageInterceptor){
        return new DefaultPointcutAdvisor(
                new AnnotationMatchingPointcut(null, AsyncBasedRocketMQ.class),
                sendMessageInterceptor);
    }

//    @Bean
//    public AsyncForOrderedInterceptor asyncForOrderedInterceptor(){
//        return new AsyncForOrderedInterceptor(this.environment, producerRegistry(), parameterNameDiscoverer);
//    }
//
//    @Bean
//    public AsyncForOrderedConsumerContainerRegistry asyncForOrderConsumerContainerRegistry(){
//        return new AsyncForOrderedConsumerContainerRegistry(this.environment, asyncConsumerFactory());
//    }
//    @Bean
//    public PointcutAdvisor asyncForOrderPointcutAdvisor(@Autowired AsyncForOrderedInterceptor sendMessageInterceptor){
//        return new DefaultPointcutAdvisor(new AnnotationMatchingPointcut(null, AsyncForOrderedBasedRocketMQ.class), sendMessageInterceptor);
//    }
//
//    protected abstract ProducerRegistry producerRegistry();
//
//    protected abstract ConsumerFactory asyncConsumerFactory();
}
