package com.geekhalo.lego.core.query.support.method;

import com.geekhalo.lego.core.query.QueryResultConverter;
import com.geekhalo.lego.core.query.support.QueryServiceMetadata;
import com.geekhalo.lego.core.query.support.handler.DefaultQueryHandler;
import com.geekhalo.lego.core.query.support.handler.converter.*;
import com.geekhalo.lego.core.query.support.handler.executor.MethodBasedQueryExecutor;
import com.geekhalo.lego.core.query.support.handler.executor.QueryExecutor;
import com.geekhalo.lego.core.query.support.handler.filler.ResultFiller;
import com.geekhalo.lego.core.query.support.handler.filler.SmartResultFillers;
import com.geekhalo.lego.core.singlequery.Page;
import com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker;
import com.geekhalo.lego.core.support.invoker.ServiceMethodInvokerFactory;
import com.geekhalo.lego.core.validator.ValidateService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class QueryServiceMethodInvokerFactory implements ServiceMethodInvokerFactory {
    private final Object repository;
    private final ValidateService validateService;
    private final SmartResultFillers smartResultFillers;
    private final QueryServiceMetadata metadata;
    private final List<QueryResultConverter> queryResultConverters;


    public QueryServiceMethodInvokerFactory(Object repository,
                                            ValidateService validateService,
                                            SmartResultFillers smartResultFillers,
                                            QueryServiceMetadata metadata,
                                            List<QueryResultConverter> queryResultConverters) {
        this.repository = repository;
        this.validateService = validateService;
        this.smartResultFillers = smartResultFillers;
        this.metadata = metadata;
        this.queryResultConverters = queryResultConverters;
    }

    @Override
    public ServiceMethodInvoker createForMethod(Method method) {
        Method matchingMethod = findMatchMethodFromRepository(method);
        if (matchingMethod == null){
            return null;
        }

        QueryExecutor queryExecutor = new MethodBasedQueryExecutor(this.repository, matchingMethod);

        ResultConverter converter = createResultConverter(method, matchingMethod);

        ResultFiller filler = this.smartResultFillers.findResultFiller(method.getReturnType());

        DefaultQueryHandler defaultQueryHandler = new DefaultQueryHandler<>(this.validateService,
                queryExecutor, converter, filler);

        return new QueryServiceMethodInvoker<>(defaultQueryHandler);
    }

    private ResultConverter createResultConverter(Method method, Method matchingMethod) {
        Class<?> returnType = method.getReturnType();
        if (List.class.isAssignableFrom(returnType)){
            Class itemClass = findItemClass(method);
            Class entityClass = this.metadata.getDomainClass();
            ResultConverter itemConverter = findItemConverter(entityClass, itemClass);

            return new ListConverter(itemConverter);
        }else if (Page.class.isAssignableFrom(returnType)){
            Class itemClass = findItemClass(method);
            Class entityClass = this.metadata.getDomainClass();
            ResultConverter itemConverter = findItemConverter(entityClass, itemClass);

            return new PageConverter(itemConverter);
        }else if(returnType.isAssignableFrom(matchingMethod.getReturnType())){
            return EqualConverter.getInstance();
        }else {
            return findItemConverter(this.metadata.getDomainClass(), returnType);
        }
    }

    private Class findItemClass(Method method) {
        Class<?> returnType = method.getReturnType();
        if (Page.class.isAssignableFrom(returnType) || List.class.isAssignableFrom(returnType)) {
            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                Type itemType = actualTypeArguments[0];
                String itemTypeName = itemType.getTypeName();
                try {
                    return Class.forName(itemTypeName);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private Method findMatchMethodFromRepository(Method method) {
        // 根据方法名和参数 精确查找 最合适的方法
        Method matchingMethod = MethodUtils.getMatchingMethod(this.repository.getClass(), method.getName(), method.getParameterTypes());
        if (matchingMethod == null){
            // 根据返回值和参数查找匹配的方法
            List<Method> allMatchMethods = Lists.newArrayList();
            Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(this.repository.getClass());
            for (Method repositoryMethod : allDeclaredMethods){
                if (match(repositoryMethod, method)){
                    allMatchMethods.add(repositoryMethod);
                }
            }
            matchingMethod = findMethod(allMatchMethods, method);
        }
        return matchingMethod;
    }

    private Method findMethod(List<Method> allMatchMethods, Method method) {
        if (CollectionUtils.isEmpty(allMatchMethods)){
            return null;
        }
        return allMatchMethods.get(0);
    }

    private ResultConverter findItemConverter(Class entityClass, Class itemClass) {
        if (entityClass == itemClass){
            return data -> data;
        }

        for (QueryResultConverter queryResultConverter : this.queryResultConverters){
            if (queryResultConverter.support(entityClass, itemClass)){
                return data -> queryResultConverter.convert(data);
            }
        }

        Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(itemClass);
        for (Method method : allDeclaredMethods){
            int modifiers = method.getModifiers();
            if (Modifier.isStatic(modifiers)){
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1 && parameterTypes[0] == entityClass){
                    return new StaticMethodConverter(itemClass, method);
                }
            }
        }

        Constructor matchingAccessibleConstructor = ConstructorUtils.getMatchingAccessibleConstructor(itemClass, entityClass);
        if (matchingAccessibleConstructor != null){
            return new ConstructorBasedConvertor(itemClass);
        }
        throw new RuntimeException("Result Converter Not Found");

    }

    private boolean match(Method repositoryMethod, Method method) {
        return matchReturnType(repositoryMethod, method) && matchParamType(repositoryMethod, method);
    }

    private boolean matchParamType(Method repositoryMethod, Method method) {
        Class<?>[] repositoryParameterTypes = repositoryMethod.getParameterTypes();
        Class<?>[] callParameterTypes = method.getParameterTypes();
        if (repositoryParameterTypes.length != callParameterTypes.length){
            return false;
        }
        for (int i = 0; i< repositoryParameterTypes.length; i++){
            Class rParamType = repositoryParameterTypes[i];
            Class cParamType = callParameterTypes[i];
            if (!rParamType.isAssignableFrom(cParamType)){
                return false;
            }
        }
        return true;
    }

    private boolean matchReturnType(Method repositoryMethod, Method method) {
        Class<?> repositoryReturnType = repositoryMethod.getReturnType();
        Class<?> callReturnType = method.getReturnType();
        return Objects.equals(repositoryReturnType, callReturnType);
    }
}
