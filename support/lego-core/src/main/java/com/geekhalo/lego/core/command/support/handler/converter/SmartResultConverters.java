package com.geekhalo.lego.core.command.support.handler.converter;

import com.geekhalo.lego.core.command.AggRoot;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SmartResultConverters {
    private final List<SmartResultConverter> smartResultConverters = Lists.newArrayList();

    public <AGG extends AggRoot<?>, CONTEXT, RESULT> ResultConverter<AGG, CONTEXT, RESULT> findResultConverter(
            Class<AGG> aggClass, Class<CONTEXT> contextClass, Class<RESULT> resultClass){
        return this.smartResultConverters.stream()
                .filter(smartResultConverter -> smartResultConverter.apply(aggClass, contextClass, resultClass))
                .findFirst()
                .orElseThrow(() -> new ResultConverterNotFoundException(aggClass, contextClass, resultClass));
    }

    @Autowired
    public void setSmartResultConverters(List<SmartResultConverter> smartResultConverters){
        this.smartResultConverters.addAll(smartResultConverters);
        AnnotationAwareOrderComparator.sort(this.smartResultConverters);
    }
}
