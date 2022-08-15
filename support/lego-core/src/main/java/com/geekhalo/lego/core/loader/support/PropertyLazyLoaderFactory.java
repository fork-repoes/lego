package com.geekhalo.lego.core.loader.support;

import org.springframework.context.ApplicationContext;

/**
 * Created by taoli on 2022/8/15.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class PropertyLazyLoaderFactory {
    private final ApplicationContext applicationContext;

    public PropertyLazyLoaderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public PropertyLazyLoader createFor(Class cls, Object target){
        return new PropertyLazyLoader(applicationContext, target);
    }
}
