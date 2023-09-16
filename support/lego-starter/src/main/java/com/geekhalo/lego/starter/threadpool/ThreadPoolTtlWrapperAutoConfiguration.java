package com.geekhalo.lego.starter.threadpool;

import com.geekhalo.lego.core.threadpool.ThreadPoolExecutorTtlWrapperProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ThreadPoolTtlWrapperAutoConfiguration {
    @Bean
    public ThreadPoolExecutorTtlWrapperProcessor threadPoolExecutorTtlWrapperProcessor(){
        return new ThreadPoolExecutorTtlWrapperProcessor();
    }
}
