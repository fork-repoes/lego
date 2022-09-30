package com.geekhalo.lego.core.query.support.method;

import com.geekhalo.lego.core.joininmemory.JoinService;
import com.geekhalo.lego.core.query.ResultConverter;
import com.geekhalo.lego.core.query.support.QueryServiceMetadata;
import com.geekhalo.lego.core.singlequery.Page;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class QueryServiceMethodAdapterFactory implements QueryServiceMethodFactory{
    private final Object repository;
    private final JoinService joinService;
    private final QueryServiceMetadata metadata;
    private final List<ResultConverter> resultConverters;


    public QueryServiceMethodAdapterFactory(Object repository,
                                            JoinService joinService,
                                            QueryServiceMetadata metadata,
                                            List<ResultConverter> resultConverters) {
        this.repository = repository;
        this.joinService = joinService;
        this.metadata = metadata;
        this.resultConverters = resultConverters;
    }

    @Override
    public QueryServiceMethod createForMethod(Method method) {
        Method matchingMethod = findMatchMethodFromRepository(method);
        if (matchingMethod == null){
            return null;
        }

        Function<Object[], Object> queryExecutor = new MethodBasedQueryExecutor(this.repository, matchingMethod);

        Function converter = createResultConverter(method, matchingMethod);

        Function filler = createFiller(method.getReturnType());

        return new QueryServiceMethodAdapter<>(queryExecutor, converter, filler);
    }

    private Function createFiller(Class<?> returnType) {
        if (List.class.isAssignableFrom(returnType)){
            return data -> {
                this.joinService.joinInMemory((List<? extends Object>) data);
                return data;
            };
        }else if (Page.class.isAssignableFrom(returnType)){
            return data ->{
                Page page = (Page) data;
                this.joinService.joinInMemory(page.getContent());
                return data;
            };
        }else {
            return data -> {
                this.joinService.joinInMemory(data);
                return data;
            };
        }
    }

    private Function createResultConverter(Method method, Method matchingMethod) {
        Class<?> returnType = method.getReturnType();
        if (List.class.isAssignableFrom(returnType)){
            Class itemClass = findItemClass(method);
            Class entityClass = this.metadata.getDomainClass();
            Function itemConverter = findItemConverter(entityClass, itemClass);

            return new ListConverter(itemConverter);
        }else if (Page.class.isAssignableFrom(returnType)){
            Class itemClass = findItemClass(method);
            Class entityClass = this.metadata.getDomainClass();
            Function itemConverter = findItemConverter(entityClass, itemClass);

            return new PageConverter(itemConverter);
        }else if(returnType.isAssignableFrom(matchingMethod.getReturnType())){
            return Function.identity();
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

    private Function findItemConverter(Class entityClass, Class itemClass) {
        if (entityClass == itemClass){
            return data -> data;
        }
        Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(itemClass);
        for (Method method : allDeclaredMethods){
            int modifiers = method.getModifiers();
            if (Modifier.isStatic(modifiers)){
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1 && parameterTypes[0] == entityClass){
                    return entity -> {
                        try {
                            return MethodUtils.invokeStaticMethod(itemClass, method.getName(), entity);
                        } catch (Exception e) {
                            log.error("failed to invoker static method {}.{} use {}", itemClass, method, entity);
                        }
                        return null;
                    };
                }
            }
        }

        Constructor matchingAccessibleConstructor = ConstructorUtils.getMatchingAccessibleConstructor(itemClass, entityClass);
        if (matchingAccessibleConstructor != null){
            return entity -> {
                try {
                    return ConstructorUtils.invokeConstructor(itemClass, entity);
                } catch (Exception e) {
                    log.error("failed to invoke Constructor {} use {}", itemClass, entity);
                }
                return null;
            };
        }

        for (ResultConverter resultConverter : this.resultConverters){
            if (resultConverter.support(entityClass, itemClass)){
                return data -> resultConverter.convert(data);
            }
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

    private class PageConverter implements Function<Page, Page> {
        private final Function itemConverter;
        public PageConverter(Function itemConverter) {
            this.itemConverter = itemConverter;
        }

        @Override
        public Page apply(Page page) {
            if (page == null){
                return null;
            }
            return page.convert(this.itemConverter);
        }
    }

    class ListConverter implements Function<List, List>{
        private final Function itemConverter;

        ListConverter(Function itemConverter) {
            this.itemConverter = itemConverter;
        }

        @Override
        public List apply(List list) {
            if (CollectionUtils.isEmpty(list)){
                return Collections.emptyList();
            }
            return (List) list.stream()
                    .map(itemConverter)
                    .collect(Collectors.toList());
        }
    }

    class MethodBasedQueryExecutor implements Function<Object[], Object>{
        private final Object repository;
        private final Method method;

        MethodBasedQueryExecutor(Object repository, Method method) {
            this.repository = repository;
            this.method = method;
        }

        @SneakyThrows
        @Override
        public Object apply(Object[] objects) {
            return method.invoke(this.repository, objects);
        }
    }
}
