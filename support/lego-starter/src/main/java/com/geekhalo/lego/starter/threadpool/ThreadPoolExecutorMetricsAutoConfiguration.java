package com.geekhalo.lego.starter.threadpool;

import com.geekhalo.lego.core.threadpool.ThreadPoolExecutorMetricsProcessor;
import com.geekhalo.lego.core.threadpool.ThreadPoolTaskExecutorMetricsProcessor;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass({MeterRegistry.class})
@ConditionalOnBean(MeterRegistry.class)
@AutoConfigureAfter(CompositeMeterRegistryAutoConfiguration.class)
public class ThreadPoolExecutorMetricsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadPoolExecutorMetricsProcessor threadPoolExecutorMetricsProcessor(){
        return new ThreadPoolExecutorMetricsProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadPoolTaskExecutorMetricsProcessor threadPoolTaskExecutorMetricsProcessor(){
        return new ThreadPoolTaskExecutorMetricsProcessor();
    }
}
