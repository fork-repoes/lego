package com.geekhalo.lego.core.query;

import com.google.common.collect.Sets;
import lombok.Setter;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor;
import org.springframework.transaction.interceptor.TransactionalProxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

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

    @Setter
    private ApplicationContext applicationContext;

    public <T> T createQueryService(){
        QueryServiceMetadata metadata = QueryServiceMetadata.fromQueryService(queryService);

        Object target = createTargetQueryService(metadata);
        ProxyFactory result = new ProxyFactory();

        result.setTarget(target);
        result.setInterfaces(metadata.getQueryServiceClass(), TransactionalProxy.class);

        if (DefaultMethodInvokingMethodInterceptor.hasDefaultMethods(queryService)) {
            result.addAdvice(new DefaultMethodInvokingMethodInterceptor());
        }

        Set<Method> methods = Sets.newHashSet(ReflectionUtils.getAllDeclaredMethods(queryService));

        // 对所有的实现进行封装，基于拦截器进行请求转发
        // 1. target 对象封装
        ImplementationMethodExecutionInterceptor targetImplementationMethodExecutionInterceptor = new ImplementationMethodExecutionInterceptor(target);
        targetImplementationMethodExecutionInterceptor.initMethods(methods);

        methods.removeIf(method -> targetImplementationMethodExecutionInterceptor.support(method));

        result.addAdvice(targetImplementationMethodExecutionInterceptor);

        // 2. 自定义实现类封装
        List<Class<?>> allInterfaces = ClassUtils.getAllInterfaces(metadata.getQueryServiceClass());
        for (Class itfCls : allInterfaces){
            if (itfCls.getAnnotation(NoQueryService.class) != null){
                continue;
            }
            String clsName = itfCls.getSimpleName();
            String beanName = Character.toLowerCase(clsName.charAt(0)) + clsName.substring(1, clsName.length()) + "Impl";
            Object bean = applicationContext.getBean(beanName, itfCls);
            if (bean != null){
                ImplementationMethodExecutionInterceptor implementationMethodExecutionInterceptor = new ImplementationMethodExecutionInterceptor(bean);
                implementationMethodExecutionInterceptor.initMethods(methods);

                methods.removeIf(method -> implementationMethodExecutionInterceptor.support(method));
                result.addAdvice(implementationMethodExecutionInterceptor);
            }
        }


        // 3. 自动解析方法封装
        result.addAdvice(new QueryExecutorMethodInterceptor());

        T proxy = (T) result.getProxy(classLoader);
        return proxy;
    }

    private Object createTargetQueryService(QueryServiceMetadata metadata) {
        return new Object();
    }
}
