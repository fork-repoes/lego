package com.geekhalo.lego.core.command.support;

import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class CommandServiceProxyFactoryBean<B>
        implements FactoryBean<B>,
        InitializingBean,
        ApplicationContextAware,
        BeanClassLoaderAware {
    private final Class commandService;

    private final CommandServiceProxyFactory commandServiceProxyFactory
            = new CommandServiceProxyFactory();

    private ApplicationContext applicationContext;

    private ClassLoader classLoader;

    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @Autowired
    private ValidateService validateService;

    public CommandServiceProxyFactoryBean(Class commandService) {
        this.commandService = commandService;
    }

    @Override
    public B getObject() throws Exception {
        return this.commandServiceProxyFactory.createCommandService();
    }

    @Override
    public Class<?> getObjectType() {
        return this.commandService;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        commandServiceProxyFactory.setCommandService(this.commandService);
        commandServiceProxyFactory.setClassLoader(this.classLoader);
        commandServiceProxyFactory.setApplicationContext(this.applicationContext);
        commandServiceProxyFactory.setLazyLoadProxyFactory(this.lazyLoadProxyFactory);
        commandServiceProxyFactory.setValidateService(this.validateService);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
