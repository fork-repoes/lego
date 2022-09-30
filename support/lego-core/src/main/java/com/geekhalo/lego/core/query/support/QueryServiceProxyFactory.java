package com.geekhalo.lego.core.query.support;

import com.geekhalo.lego.core.joininmemory.JoinService;
import com.geekhalo.lego.core.query.NoQueryService;
import com.geekhalo.lego.core.query.QueryServiceMethodLostException;
import com.geekhalo.lego.core.query.ResultConverter;
import com.geekhalo.lego.core.query.support.method.QueryServiceMethod;
import com.geekhalo.lego.core.query.support.method.QueryServiceMethodAdapterFactory;
import com.geekhalo.lego.core.query.support.method.TargetBasedQueryServiceMethodFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor;
import org.springframework.transaction.interceptor.TransactionalProxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class QueryServiceProxyFactory {
    @Setter
    private ClassLoader classLoader;
    @Setter
    private Class queryService;
    @Setter
    private ApplicationContext applicationContext;
    @Setter
    private JoinService joinService;


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
        result.addAdvice(createTargetDispatcherInterceptor(target, methods));

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
                result.addAdvice(createTargetDispatcherInterceptor(bean, methods));
            }
        }

        // 3. 自动解析方法封装
        Class repositoryClass = metadata.getRepositoryClass();
        Object repository = this.applicationContext.getBean(repositoryClass);
        QueryServiceMethodDispatcherInterceptor methodDispatcher = createDispatcherInterceptor(methods, repository, metadata);

        if (CollectionUtils.isNotEmpty(methods)){
            throw new QueryServiceMethodLostException(methods);
        }

        result.addAdvice(methodDispatcher);
        T proxy = (T) result.getProxy(classLoader);
        return proxy;
    }

    private QueryServiceMethodDispatcherInterceptor createDispatcherInterceptor(Set<Method> methods, Object repository, QueryServiceMetadata metadata) {
        QueryServiceMethodDispatcherInterceptor methodDispatcher = new QueryServiceMethodDispatcherInterceptor();
        Map<String, ResultConverter> beansOfType = this.applicationContext.getBeansOfType(ResultConverter.class);
        QueryServiceMethodAdapterFactory queryServiceMethodAdapterFactory = new QueryServiceMethodAdapterFactory(repository,
                this.joinService,
                metadata,
                Lists.newArrayList(beansOfType.values()));
        Iterator<Method> iterator = methods.iterator();
        while (iterator.hasNext()){
            Method callMethod = iterator.next();
            QueryServiceMethod exeMethod = queryServiceMethodAdapterFactory.createForMethod(callMethod);
            if (exeMethod != null){
                methodDispatcher.register(callMethod, exeMethod);
                iterator.remove();
            }
        }
        return methodDispatcher;
    }

    private QueryServiceMethodDispatcherInterceptor createTargetDispatcherInterceptor(Object target, Set<Method> methods){
        QueryServiceMethodDispatcherInterceptor targetMethodDispatcher = new QueryServiceMethodDispatcherInterceptor();
        TargetBasedQueryServiceMethodFactory targetBasedQueryServiceMethodFactory = new TargetBasedQueryServiceMethodFactory(target);
        Iterator<Method> targetIterator = methods.iterator();
        while (targetIterator.hasNext()){
            Method callMethod = targetIterator.next();
            QueryServiceMethod exeMethod = targetBasedQueryServiceMethodFactory.createForMethod(callMethod);
            if (exeMethod != null){
                targetMethodDispatcher.register(callMethod, exeMethod);
                targetIterator.remove();
            }
        }

        return targetMethodDispatcher;
    }

    private Object createTargetQueryService(QueryServiceMetadata metadata) {
        return new Object();
    }
}
