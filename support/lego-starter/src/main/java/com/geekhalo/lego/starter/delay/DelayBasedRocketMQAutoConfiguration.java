package com.geekhalo.lego.starter.delay;

import com.geekhalo.lego.annotation.delay.DelayBasedRocketMQ;
import com.geekhalo.lego.core.delay.DelayConsumerContainerRegistry;
import com.geekhalo.lego.core.delay.DelayMethodInterceptor;
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
 * Created by taoli on 2022/9/4.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@ConditionalOnBean(RocketMQTemplate.class)
public class DelayBasedRocketMQAutoConfiguration {
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Autowired
    private Environment environment;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Bean
    public DelayMethodInterceptor delayInterceptor(){
        return new DelayMethodInterceptor(this.environment, this.rocketMQTemplate, parameterNameDiscoverer);
    }

    @Bean
    public DelayConsumerContainerRegistry delayConsumerContainerRegistry(){
        return new DelayConsumerContainerRegistry(this.environment);
    }
    @Bean
    public PointcutAdvisor delayPointcutAdvisor(@Autowired DelayMethodInterceptor delayMethodInterceptor){
        return new DefaultPointcutAdvisor(new AnnotationMatchingPointcut(null, DelayBasedRocketMQ.class),
                delayMethodInterceptor);
    }
}
