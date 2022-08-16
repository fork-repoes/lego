package com.geekhalo.lego.core.loader.support;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * Created by taoli on 2022/8/15.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class LazyLoaderInterceptorFactory {
    private final Map<Class, Map<String, PropertyLazyLoader>> loaderCache = Maps.newHashMap();
    private final PropertyLazyLoaderFactory propertyLazyLoaderFactory;

    public LazyLoaderInterceptorFactory(PropertyLazyLoaderFactory propertyLazyLoaderFactory) {
        this.propertyLazyLoaderFactory = propertyLazyLoaderFactory;
    }


    public LazyLoaderInterceptor createFor(Class cls, Object target){
        Map<String, PropertyLazyLoader>  loaders = this.loaderCache.computeIfAbsent(cls,
                targetCls -> createForClass(targetCls));

        return new LazyLoaderInterceptor(loaders, target);
    }

    private Map<String, PropertyLazyLoader> createForClass(Class targetCls) {
        List<PropertyLazyLoader> propertyLazyLoaders = propertyLazyLoaderFactory.createFor(targetCls);
        if (CollectionUtils.isEmpty(propertyLazyLoaders)){
            return Collections.emptyMap();
        }
        return propertyLazyLoaders.stream()
                .filter(Objects::nonNull)
                .collect(toMap(loader -> loader.getField().getName(), Function.identity()));
    }
}
