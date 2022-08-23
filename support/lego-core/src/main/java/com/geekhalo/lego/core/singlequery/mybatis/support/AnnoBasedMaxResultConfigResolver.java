package com.geekhalo.lego.core.singlequery.mybatis.support;

import com.geekhalo.lego.annotation.singlequery.MaxResult;
import com.geekhalo.lego.core.singlequery.MaxResultConfig;
import com.geekhalo.lego.core.singlequery.MaxResultConfigResolver;
import com.google.common.collect.Maps;

import java.util.Map;

public class AnnoBasedMaxResultConfigResolver implements MaxResultConfigResolver {
    private final Map<Class, MaxResultConfig> maxResultConfigMap = Maps.newHashMap();
    private final MaxResultConfig defMaxResultConfig;

    public AnnoBasedMaxResultConfigResolver() {
        this.defMaxResultConfig = MaxResultConfig.builder()
                .maxResult(MaxResult.DEF_MAX)
                .checkStrategy(MaxResult.DEF_STRATEGY)
                .build();

    }

    @Override
    public MaxResultConfig maxResult(Object query) {
        if (query == null){
            return null;
        }
        return this.maxResultConfigMap.computeIfAbsent(query.getClass(), this::createMaxResultConfig);
    }

    private MaxResultConfig createMaxResultConfig(Class aClass) {
        MaxResult annotation = (MaxResult) aClass.getAnnotation(MaxResult.class);
        if (annotation == null){
            return defMaxResultConfig;
        }
        return MaxResultConfig.builder()
                .maxResult(annotation.max())
                .checkStrategy(annotation.strategy())
                .build();
    }
}
