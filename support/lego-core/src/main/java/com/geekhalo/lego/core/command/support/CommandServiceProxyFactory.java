package com.geekhalo.lego.core.command.support;

import com.geekhalo.lego.core.command.NoCommandService;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.support.intercepter.MethodDispatcherInterceptor;
import com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker;
import com.geekhalo.lego.core.support.invoker.TargetBasedServiceMethodInvokerFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
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
import java.util.Set;

/**
 * Created by taoli on 2022/9/25.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class CommandServiceProxyFactory {
    @Setter(AccessLevel.PROTECTED)
    private ClassLoader classLoader;
    @Setter(AccessLevel.PROTECTED)
    private Class queryService;
    @Setter(AccessLevel.PROTECTED)
    private ApplicationContext applicationContext;
    @Setter(AccessLevel.PROTECTED)
    private LazyLoadProxyFactory lazyLoadProxyFactory;
    @Setter(AccessLevel.PROTECTED)
    private ValidateService validateService;


    public <T> T createCommandService(){
        CommandServiceMetadata metadata = CommandServiceMetadata.fromCommandService(queryService);

        Object target = createTargetQueryService(metadata);
        ProxyFactory result = new ProxyFactory();

        result.setTarget(target);
        result.setInterfaces(metadata.getCommandServiceClass(), TransactionalProxy.class);

        if (DefaultMethodInvokingMethodInterceptor.hasDefaultMethods(queryService)) {
            result.addAdvice(new DefaultMethodInvokingMethodInterceptor());
        }

        Set<Method> methods = Sets.newHashSet(ReflectionUtils.getAllDeclaredMethods(queryService));

        // 对所有的实现进行封装，基于拦截器进行请求转发
        // 1. target 对象封装
        result.addAdvice(createTargetDispatcherInterceptor(target, methods));

        // 2. 自定义实现类封装
        List<Class<?>> allInterfaces = ClassUtils.getAllInterfaces(metadata.getCommandServiceClass());
        for (Class itfCls : allInterfaces){
            if (itfCls.getAnnotation(NoCommandService.class) != null){
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

        if (CollectionUtils.isNotEmpty(methods)){
//            throw new CommandServiceMethodLostException(methods);
        }

//        result.addAdvice(methodDispatcher);
        T proxy = (T) result.getProxy(classLoader);
        return proxy;
    }

    private MethodDispatcherInterceptor createDispatcherInterceptor(Set<Method> methods, Object repository, CommandServiceMetadata metadata) {
//        MethodDispatcherInterceptor methodDispatcher = new MethodDispatcherInterceptor();
//        Map<String, QueryResultConverter> beansOfType = this.applicationContext.getBeansOfType(QueryResultConverter.class);
//        QueryServiceMethodAdapterInvokerFactory queryServiceMethodAdapterFactory = new QueryServiceMethodAdapterInvokerFactory(repository,
//                this.joinService,
//                metadata,
//                Lists.newArrayList(beansOfType.values()));
//        Iterator<Method> iterator = methods.iterator();
//        while (iterator.hasNext()){
//            Method callMethod = iterator.next();
//            ServiceMethodInvoker exeMethod = queryServiceMethodAdapterFactory.createForMethod(callMethod);
//            if (exeMethod != null){
//                methodDispatcher.register(callMethod, exeMethod);
//                iterator.remove();
//            }
//        }
//        return methodDispatcher;
        return null;
    }

    private MethodDispatcherInterceptor createTargetDispatcherInterceptor(Object target, Set<Method> methods){
        MethodDispatcherInterceptor targetMethodDispatcher = new MethodDispatcherInterceptor();
        TargetBasedServiceMethodInvokerFactory targetBasedQueryServiceMethodFactory = new TargetBasedServiceMethodInvokerFactory(target);
        Iterator<Method> targetIterator = methods.iterator();
        while (targetIterator.hasNext()){
            Method callMethod = targetIterator.next();
            ServiceMethodInvoker exeMethod = targetBasedQueryServiceMethodFactory.createForMethod(callMethod);
            if (exeMethod != null){
                targetMethodDispatcher.register(callMethod, exeMethod);
                targetIterator.remove();
            }
        }

        return targetMethodDispatcher;
    }

    private Object createTargetQueryService(CommandServiceMetadata metadata) {
        return new Object();
    }
}
