package com.geekhalo.lego.query;

import com.geekhalo.lego.core.query.QueryServiceFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class OrderQueryServiceProxyFactoryBean
        implements FactoryBean<OrderQueryServiceProxy>,
        InitializingBean,
        BeanClassLoaderAware {
    private final Class queryService = OrderQueryServiceProxy.class;

    private final QueryServiceFactory queryServiceFactory = new QueryServiceFactory();

    private ClassLoader classLoader;

    @Override
    public OrderQueryServiceProxy getObject() throws Exception {
        return this.queryServiceFactory.createQueryService();
    }

    @Override
    public Class<?> getObjectType() {
        return OrderQueryServiceProxy.class;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        queryServiceFactory.setQueryService(this.queryService);
        queryServiceFactory.setClassLoader(this.classLoader);
    }
}
