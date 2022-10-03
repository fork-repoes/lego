package com.geekhalo.lego.core.command.support.method;

import com.geekhalo.lego.core.command.*;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker;
import com.geekhalo.lego.core.support.invoker.ServiceMethodInvokerFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Function;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class UpdateServiceMethodInvokerFactory
        extends BaseCommandServiceMethodInvokerFactory
        implements ServiceMethodInvokerFactory {
    @Setter
    private LazyLoadProxyFactory lazyLoadProxyFactory;
    @Setter
    private ValidateService validateService;
    @Setter
    private CommandRepository commandRepository;
    @Setter
    private ApplicationEventPublisher eventPublisher;

    public UpdateServiceMethodInvokerFactory(Class<? extends AggRoot> aggClass,
                                             Class idClass) {
        super(aggClass, idClass);
    }

    @Override
    public ServiceMethodInvoker createForMethod(Method method) {
        if (method.getParameterCount() != 1){
            return null;
        }

        Class commandType = method.getParameterTypes()[0];
        Class returnType = method.getReturnType();

        UpdateServiceMethodInvoker invoker = new UpdateServiceMethodInvoker<>(this.lazyLoadProxyFactory,
                this.validateService,
                this.commandRepository,
                this.eventPublisher);

        // 在静态方法中查找
        boolean findMethod = false;
        for (Method aggMethod : this.getAggClass().getDeclaredMethods()){
            int modifiers = aggMethod.getModifiers();
            if (Modifier.isStatic(modifiers)){
                continue;
            }

            int parameterCount = aggMethod.getParameterCount();
            if (parameterCount != 1){
                continue;
            }

            Class aggParamType = aggMethod.getParameterTypes()[0];
            if (ContextForUpdate.class.isAssignableFrom(aggParamType)){
                Function contextFactory = findContextFactory(commandType, aggParamType);
                invoker.setContextFactory(contextFactory);
                invoker.setBizMethod((agg, context) -> {
                    try {
                        MethodUtils.invokeMethod(agg, aggMethod.getName(), context);
                    } catch (Exception e) {
                        log.error("failed to call method {} use {}", aggMethod, context, e);
                        throw new RuntimeException(e);
                    }
                });
                findMethod = true;
                break;
            }
            if (CommandForUpdate.class.isAssignableFrom(aggParamType)){
                invoker.setContextFactory(command -> NullContextForUpdate.apply((CommandForUpdate) command));
                invoker.setBizMethod((agg, context) -> {
                    NullContextForUpdate contextForUpdate = (NullContextForUpdate) context;
                    CommandForUpdate command = contextForUpdate.getCommand();
                    try {
                        MethodUtils.invokeMethod(agg, aggMethod.getName(), command);
                    } catch (Exception e) {
                        log.error("failed to call method {} use {}", aggMethod, command, e);
                        throw new RuntimeException(e);
                    }
                });
                findMethod = true;
                break;
            }
        }

        if (!findMethod){
            return null;
        }

        invoker.setResultConverter(createResultConverter(returnType));

        return invoker;
    }


    private Function findContextFactory(Class commandType, Class aggParamType) {
        for (Method method : ReflectionUtils.getAllDeclaredMethods(aggParamType)){
            int modifiers = method.getModifiers();
            if (!Modifier.isStatic(modifiers)){
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0] == commandType){
                return command -> {
                    try {
                        return MethodUtils.invokeStaticMethod(aggParamType, method.getName(), command);
                    } catch (Exception e) {
                        log.error("failed to invoker static method {} use {}", method, command);
                        throw new RuntimeException(e);
                    }
                };
            }
        }
        Constructor matchingAccessibleConstructor = ConstructorUtils.getMatchingAccessibleConstructor(aggParamType, commandType);
        if (matchingAccessibleConstructor != null){
            return command -> {
                try {
                    return ConstructorUtils.invokeConstructor(aggParamType, command);
                } catch (Exception e) {
                    log.error("failed to invoke Constructor {} use {}", aggParamType, command);
                    throw new RuntimeException(e);
                }
            };
        }

        return null;
    }
}
