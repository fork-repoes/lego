package com.geekhalo.lego.core.query.support;

import com.geekhalo.lego.core.joininmemory.JoinService;
import com.geekhalo.lego.core.query.support.handler.filler.SmartResultFillers;
import com.geekhalo.lego.core.validator.ValidateService;
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
public class QueryServiceProxyFactoryBean<B>
        implements FactoryBean<B>,
        InitializingBean,
        ApplicationContextAware,
        BeanClassLoaderAware {
    private final Class queryService;

    private final QueryServiceProxyFactory queryServiceFactory = new QueryServiceProxyFactory();

    private ApplicationContext applicationContext;

    private ClassLoader classLoader;

    @Autowired
    private SmartResultFillers smartResultFillers;

    @Autowired
    private ValidateService validateService;

    public QueryServiceProxyFactoryBean(Class queryService) {
        this.queryService = queryService;
    }

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
        queryServiceFactory.setSmartResultFillers(this.smartResultFillers);
        queryServiceFactory.setValidateService(this.validateService);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
