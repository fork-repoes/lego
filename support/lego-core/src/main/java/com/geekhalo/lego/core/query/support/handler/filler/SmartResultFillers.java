package com.geekhalo.lego.core.query.support.handler.filler;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

public class SmartResultFillers {
    private final List<SmartResultFiller> smartResultFillers = Lists.newArrayList();

    public SmartResultFiller findResultFiller(Class resultClass){
        return smartResultFillers.stream()
                .filter(smartResultFiller -> smartResultFiller.apply(resultClass))
                .findFirst()
                .orElse(null);
    }

    @Autowired(required = false)
    public void setSmartResultFillers(List<SmartResultFiller> smartResultFillers){
        this.smartResultFillers.addAll(smartResultFillers);
        AnnotationAwareOrderComparator.sort(this.smartResultFillers);
    }
}
