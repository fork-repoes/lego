package com.geekhalo.lego.core.query.support;

import com.geekhalo.lego.core.query.NoQueryService;
import com.geekhalo.lego.core.query.QueryResultConverter;
import com.geekhalo.lego.core.query.QueryServiceMethodLostException;
import com.geekhalo.lego.core.query.support.handler.filler.SmartResultFillers;
import com.geekhalo.lego.core.query.support.method.QueryServiceMethodInvokerFactory;
import com.geekhalo.lego.core.support.intercepter.MethodDispatcherInterceptor;
import com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker;
import com.geekhalo.lego.core.support.invoker.TargetBasedServiceMethodInvokerFactory;
import com.geekhalo.lego.core.support.proxy.DefaultProxyObject;
import com.geekhalo.lego.core.support.proxy.ProxyObject;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
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
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class QueryServiceProxyFactory {
    @Setter
    private ClassLoader classLoader;
    @Setter
    private Class queryService;
    @Setter
    private ApplicationContext applicationContext;
    @Setter
    private ValidateService validateService;
    @Setter
    private SmartResultFillers smartResultFillers;


    public <T> T createQueryService(){
        QueryServiceMetadata metadata = QueryServiceMetadata.fromQueryService(queryService);

        Object target = createTargetQueryService(metadata);
        ProxyFactory result = new ProxyFactory();

        result.setTarget(target);
        result.setInterfaces(metadata.getQueryServiceClass(), ProxyObject.class, TransactionalProxy.class);

        Set<Method> methods = Sets.newHashSet(ReflectionUtils.getAllDeclaredMethods(queryService));
        methods.addAll(Sets.newHashSet(ReflectionUtils.getAllDeclaredMethods(ProxyObject.class)));

        if (DefaultMethodInvokingMethodInterceptor.hasDefaultMethods(queryService)) {
            result.addAdvice(new DefaultMethodInvokingMethodInterceptor());
            // 移除默认方法
            Set<Method> methodsForRemove = methods.stream()
                    .filter(Method::isDefault)
                    .collect(Collectors.toSet());

            removeHierarchy(methods, methodsForRemove);
        }



        // 对所有的实现进行封装，基于拦截器进行请求转发
        // 1. target 对象封装
        result.addAdvice(createTargetDispatcherInterceptor(target, methods));

        // 添加 ProxyObject 提供 Meta信息
        result.addAdvice(createTargetDispatcherInterceptor(new DefaultProxyObject(metadata.getQueryServiceClass()), methods));

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
        MethodDispatcherInterceptor methodDispatcher = createDispatcherInterceptor(methods, repository, metadata);
        log.info("Auto Register QueryMethod for {} : {}", queryService, methodDispatcher);

        if (CollectionUtils.isNotEmpty(methods)){
            throw new QueryServiceMethodLostException(methods);
        }

        result.addAdvice(methodDispatcher);
        T proxy = (T) result.getProxy(classLoader);
        return proxy;
    }

    private void removeHierarchy(Set<Method> methods, Set<Method> methodsForRemove) {
        Set<Method> all = methodsForRemove.stream()
                .flatMap(method -> MethodUtils.getOverrideHierarchy(method, ClassUtils.Interfaces.INCLUDE).stream())
                .collect(Collectors.toSet());
        methods.removeAll(all);
    }

    private MethodDispatcherInterceptor createDispatcherInterceptor(Set<Method> methods, Object repository, QueryServiceMetadata metadata) {
        MethodDispatcherInterceptor methodDispatcher = new MethodDispatcherInterceptor();
        Map<String, QueryResultConverter> beansOfType = this.applicationContext.getBeansOfType(QueryResultConverter.class);
        QueryServiceMethodInvokerFactory queryServiceMethodAdapterFactory = new QueryServiceMethodInvokerFactory(repository,
                validateService, this.smartResultFillers,
                metadata,
                Lists.newArrayList(beansOfType.values()));

        Set<Method> methodsForRemove = Sets.newHashSet();
        Iterator<Method> iterator = methods.iterator();
        while (iterator.hasNext()){
            Method callMethod = iterator.next();
            ServiceMethodInvoker exeMethod = queryServiceMethodAdapterFactory.createForMethod(callMethod);
            if (exeMethod != null){
                methodDispatcher.register(callMethod, exeMethod);
                methodsForRemove.add(callMethod);
            }
        }
        removeHierarchy(methods, methodsForRemove);

        return methodDispatcher;
    }

    private MethodDispatcherInterceptor createTargetDispatcherInterceptor(Object target, Set<Method> methods){
        MethodDispatcherInterceptor targetMethodDispatcher = new MethodDispatcherInterceptor();
        TargetBasedServiceMethodInvokerFactory targetBasedQueryServiceMethodFactory = new TargetBasedServiceMethodInvokerFactory(target);

        Set<Method> methodsForRemove = Sets.newHashSet();
        Iterator<Method> targetIterator = methods.iterator();
        while (targetIterator.hasNext()){
            Method callMethod = targetIterator.next();
            ServiceMethodInvoker exeMethod = targetBasedQueryServiceMethodFactory.createForMethod(callMethod);
            if (exeMethod != null){
                targetMethodDispatcher.register(callMethod, exeMethod);
                methodsForRemove.add(callMethod);
            }
        }

        removeHierarchy(methods, methodsForRemove);
        return targetMethodDispatcher;
    }

    private Object createTargetQueryService(QueryServiceMetadata metadata) {
        return new Object();
    }
}
