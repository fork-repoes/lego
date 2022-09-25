package com.geekhalo.lego.core.query;

import lombok.Setter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor;
import org.springframework.transaction.interceptor.TransactionalProxy;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class QueryServiceFactory {
    @Setter
    private ClassLoader classLoader;
    @Setter
    private Class queryService;

    public <T> T createQueryService(){
        QueryServiceMetadata metadata = QueryServiceMetadata.fromQueryService(queryService);

        Object target = createTargetQueryService(metadata);
        ProxyFactory result = new ProxyFactory();
        result.setTarget(target);
        result.setInterfaces(metadata.getQueryServiceClass(), TransactionalProxy.class);

        if (DefaultMethodInvokingMethodInterceptor.hasDefaultMethods(queryService)) {
            result.addAdvice(new DefaultMethodInvokingMethodInterceptor());
        }

        // 对所有的实现进行封装，基于拦截器进行请求转发
        // 1. target 对象封装
        // 2. 自定义实现类封装
        // 3. 自动解析方法封装


        T proxy = (T) result.getProxy(classLoader);
        return proxy;
    }

    private Object createTargetQueryService(QueryServiceMetadata metadata) {
        return new Object();
    }
}
