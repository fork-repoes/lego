package com.geekhalo.lego.core.command.support.method;

import com.geekhalo.lego.core.command.*;
import com.geekhalo.lego.core.command.support.handler.ContextFactory;
import com.geekhalo.lego.core.command.support.handler.CreateAggCommandHandler;
import com.geekhalo.lego.core.loader.LazyLoadProxyFactory;
import com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker;
import com.geekhalo.lego.core.support.invoker.ServiceMethodInvokerFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;
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
public class CreateServiceMethodInvokerFactory
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
    @Setter
    private TransactionTemplate transactionTemplate;

    public CreateServiceMethodInvokerFactory(Class<? extends AggRoot> aggClass,
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

        CreateAggCommandHandler commandHandler = new
                CreateAggCommandHandler(this.validateService, this.lazyLoadProxyFactory, this.commandRepository, this.eventPublisher, this.transactionTemplate);


        // 在静态方法中查找
        boolean findMethod = false;
        for (Method aggMethod : this.getAggClass().getDeclaredMethods()){
            int modifiers = aggMethod.getModifiers();
            if (!Modifier.isStatic(modifiers)){
                continue;
            }
            int parameterCount = aggMethod.getParameterCount();
            if (parameterCount != 1){
                continue;
            }

            Class aggParamType = aggMethod.getParameterTypes()[0];
            if (ContextForCreate.class.isAssignableFrom(aggParamType)){
                ContextFactory contextFactory = findContextFactory(commandType, aggParamType);
                if (contextFactory == null){
                    continue;
                }
                commandHandler.setContextFactory(contextFactory);
                commandHandler.setAggFactory(context -> {
                    try {
                        return MethodUtils.invokeStaticMethod(this.getAggClass(), aggMethod.getName(), context);
                    } catch (Exception e) {
                        log.error("failed to call method {} use {}", aggMethod, context, e);
                        throw new RuntimeException(e);
                    }
                });
                findMethod = true;
                break;
            }
            if (CommandForCreate.class.isAssignableFrom(aggParamType)){
                commandHandler.setContextFactory(command -> NullContextForCreate.apply((CommandForCreate) command));
                commandHandler.setAggFactory(context->{
                    NullContextForCreate contextForCreate = (NullContextForCreate) context;
                    CommandForCreate command = contextForCreate.getCommand();
                    try {
                        return MethodUtils.invokeExactStaticMethod(this.getAggClass(), aggMethod.getName(), command);
                    } catch (Exception e) {
                        log.error("failed to call method {} use {}", aggMethod, command, e);
                        throw new RuntimeException(e);
                    }
                });
                findMethod = true;
                break;
            }
        }

        for (Constructor constructor : this.getAggClass().getConstructors()){
            int parameterCount = constructor.getParameterCount();
            if (parameterCount != 1){
                continue;
            }

            Class paramType = constructor.getParameterTypes()[0];
            if (ContextForCreate.class.isAssignableFrom(paramType)){

                commandHandler.setAggFactory(context -> {
                    try {
                        return MethodUtils.invokeMethod(this.getAggClass(), constructor.getName(), context);
                    } catch (Exception e) {
                        log.error("failed to call method {} use {}", constructor, context, e);
                        throw new RuntimeException(e);
                    }
                });
                findMethod = true;
                break;
            }
            if (CommandForCreate.class.isAssignableFrom(paramType)){
                commandHandler.setContextFactory(command -> NullContextForCreate.apply((CommandForCreate) command));
                commandHandler.setAggFactory(context->{
                    NullContextForCreate contextForCreate = (NullContextForCreate) context;
                    CommandForCreate command = contextForCreate.getCommand();
                    try {
                        return ConstructorUtils.invokeConstructor(this.getAggClass(), command);
                    } catch (Exception e) {
                        log.error("failed to call Constructor {} use {}", constructor, command, e);
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

        commandHandler.addBizMethod((agg, context) -> {});

        commandHandler.setResultConverter(createResultConverter(returnType));

        return new CommandHandlerBasedServiceMethodInvoker(commandHandler);
    }

    private ContextFactory findContextFactory(Class commandType, Class aggParamType) {
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
