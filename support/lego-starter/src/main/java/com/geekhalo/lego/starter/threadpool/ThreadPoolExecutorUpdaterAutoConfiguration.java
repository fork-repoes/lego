package com.geekhalo.lego.starter.threadpool;

import com.geekhalo.lego.core.threadpool.ThreadPoolExecutorUpdater;
import com.geekhalo.lego.core.threadpool.ThreadPoolExecutorUpdaterProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.annotation.Bean;

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
