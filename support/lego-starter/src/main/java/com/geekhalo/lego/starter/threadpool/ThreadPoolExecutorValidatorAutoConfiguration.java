package com.geekhalo.lego.starter.threadpool;

import com.geekhalo.lego.core.threadpool.ThreadPoolExecutorValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ThreadPoolExecutorValidatorAutoConfiguration {
    @Bean
    public ThreadPoolExecutorValidator threadPoolExecutorValidator(){
        return new ThreadPoolExecutorValidator();
    }
}
