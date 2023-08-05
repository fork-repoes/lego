package com.geekhalo.lego.starter.threadpool;

import com.geekhalo.lego.core.threadpool.ThreadPoolExecutorUpdater;
import com.geekhalo.lego.core.threadpool.ThreadPoolExecutorUpdaterProcessor;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnClass(EnvironmentChangeEvent.class)
public class ThreadPoolExecutorUpdaterAutoConfiguration {

    @Bean
    public ThreadPoolExecutorUpdater threadPoolExecutorUpdater(){
        return new ThreadPoolExecutorUpdater();
    }

    @Bean
    public ThreadPoolExecutorUpdaterProcessor threadPoolExecutorUpdaterProcessor(){
        return new ThreadPoolExecutorUpdaterProcessor();
    }
}
