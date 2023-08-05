package com.geekhalo.lego.starter.threadpool;

import com.geekhalo.lego.core.threadpool.ThreadPoolExecutorUpdaterProcessor;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ThreadPoolExecutorUpdaterAutoConfiguration {

    @Bean
    public ThreadPoolExecutorUpdaterProcessor threadPoolExecutorUpdaterProcessor(){
        return new ThreadPoolExecutorUpdaterProcessor();
    }
}
