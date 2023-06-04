package com.geekhalo.lego.core.command.support.handler;

import com.google.common.collect.Lists;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SmartContextFactories {
    private final List<SmartContextFactory> smartContextFactories = Lists.newArrayList();

    public <CMD, CONTEXT> ContextFactory<CMD, CONTEXT> findContextFactory(Class cmdClass, Class contextClass){
        return this.smartContextFactories.stream()
                .filter(smartContextFactory -> smartContextFactory.apply(cmdClass, contextClass))
                .findFirst()
                .orElseThrow(() -> new ContextFactoryNotFoundException(cmdClass, contextClass));
    }

    public void setSmartContextFactories(List<SmartContextFactory> smartContextFactories){
        this.smartContextFactories.addAll(smartContextFactories);
        AnnotationAwareOrderComparator.sort(this.smartContextFactories);
    }

}
