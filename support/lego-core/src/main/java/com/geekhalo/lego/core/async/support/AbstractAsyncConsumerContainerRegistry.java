package com.geekhalo.lego.core.async.support;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * Created by taoli on 2022/9/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class AbstractAsyncConsumerContainerRegistry implements BeanPostProcessor, SmartLifecycle {
    @Getter(AccessLevel.PROTECTED)
    private final List<AbstractAsyncConsumerContainer> consumerContainers = Lists.newArrayList();
    @Getter(AccessLevel.PROTECTED)
    private final Environment environment;

    private boolean running;

    public AbstractAsyncConsumerContainerRegistry(Environment environment) {
        Preconditions.checkArgument(environment != null);

        this.environment = environment;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {
        stop();
    }

    @Override
    public void start() {
        if (this.running == true){
            return;
        }
        this.running = true;
        this.consumerContainers.forEach(asyncConsumerContainer -> asyncConsumerContainer.start());
    }

    @Override
    public void stop() {
        if (this.running == false){
            return;
        }

        this.running = false;
        this.consumerContainers.forEach(asyncConsumerContainer -> asyncConsumerContainer.stop());
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
