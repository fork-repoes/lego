package com.geekhalo.lego.core.threadpool;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorUpdater {
    @Autowired
    private Environment environment;

    private final Map<String, ThreadPoolExecutor> threadPoolExecutorRegistry = Maps.newHashMap();

    @EventListener
    public void update(EnvironmentChangeEvent event){
        Set<String> keys = event.getKeys();
        for (String key : keys){
            for (Map.Entry<String, ThreadPoolExecutor> entry : threadPoolExecutorRegistry.entrySet()){
                String beanName = entry.getKey();
                ThreadPoolExecutor threadPoolExecutor = entry.getValue();


                String coreSizeKey = beanName + ".corePoolSize";
                if (coreSizeKey.equalsIgnoreCase(key)) {
                    threadPoolExecutor.setCorePoolSize(environment.getProperty(coreSizeKey, Integer.class));
                }

                String maxSizeKey = beanName + ".maxPoolSize";
                if (maxSizeKey.equalsIgnoreCase(key)){
                    threadPoolExecutor.setMaximumPoolSize(environment.getProperty(maxSizeKey, Integer.class));
                }
            }
        }
    }

    public void registry(String beanName, ThreadPoolExecutor threadPoolExecutor){
        this.threadPoolExecutorRegistry.put(beanName, threadPoolExecutor);
    }
}
