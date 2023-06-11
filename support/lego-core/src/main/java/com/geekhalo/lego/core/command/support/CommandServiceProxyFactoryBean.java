package com.geekhalo.lego.core.command.support;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class CommandServiceProxyFactoryBean<B>
        implements FactoryBean<B>{
    private final Class commandService;

    @Autowired
    private CommandServiceProxyFactory commandServiceProxyFactory;


    public CommandServiceProxyFactoryBean(Class commandService) {
        this.commandService = commandService;
    }

    @Override
    public B getObject() throws Exception {
        return this.commandServiceProxyFactory.createCommandService(this.commandService);
    }

    @Override
    public Class<?> getObjectType() {
        return this.commandService;
    }

}
