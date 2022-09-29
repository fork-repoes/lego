package com.geekhalo.lego.core.query;

import com.geekhalo.lego.core.joininmemory.JoinService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class OrderQueryServiceProxyFactoryBean<B>
        implements FactoryBean<B>,
        InitializingBean,
        ApplicationContextAware,
        BeanClassLoaderAware {
    private final Class queryService;

    private final QueryServiceFactory queryServiceFactory = new QueryServiceFactory();

    private ApplicationContext applicationContext;

    private ClassLoader classLoader;

    @Autowired
    private JoinService joinService;

    public OrderQueryServiceProxyFactoryBean(Class queryService) {
        this.queryService = queryService;
    }

//    @Autowired(required = false)
//    private ValidateableMethodValidationInterceptor validateableMethodValidationInterceptor;
//

    @Override
    public B getObject() throws Exception {
        return this.queryServiceFactory.createQueryService();
    }

    @Override
    public Class<?> getObjectType() {
        return this.queryService;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        queryServiceFactory.setQueryService(this.queryService);
        queryServiceFactory.setClassLoader(this.classLoader);
        queryServiceFactory.setApplicationContext(this.applicationContext);
        queryServiceFactory.setJoinService(this.joinService);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
