package com.geekhalo.lego.core.web.support;

import com.geekhalo.lego.core.web.RestResult;
import com.geekhalo.lego.core.web.RestResultFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/15.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public abstract class DispatcherController {
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    private RestResultFactory restResultFactory;

    protected <T> RestResult<T> runBodyMethod(String serviceName,
                                              String method,
                                              NativeWebRequest webRequest,
                                              WebMethodRegistry methodRegistry) {
        Map<String, SingleParamMethod> methodMap = methodRegistry.getSingleServiceMap().get(serviceName);

        if (methodMap == null){
            throw new RuntimeException("ServiceName " + serviceName + " Not Found");
        }
        SingleParamMethod queryMethod = methodMap.get(method);
        if (queryMethod == null){
            throw new RuntimeException("ServiceName " + serviceName + " Method" + method + " Not Found");
        }

        try {
            NamedMethodParameter methodParameter = queryMethod.getMethodParameter();
            WebDataBinderFactory webDataBinderFactory = createWebDataBinderFactory(queryMethod.getHandlerMethod());
            Object param = resolveArgument(webRequest, methodParameter, webDataBinderFactory);
            Object result = queryMethod.invoke(param);
            return restResultFactory.success((T) result);
        }catch (Throwable e){
            log.error("Filed to run {}#{}", serviceName, method, e);
            return restResultFactory.error(e);
        }
    }

    protected <T> RestResult<T> runParamMethod(String serviceName, String method, NativeWebRequest webRequest, WebMethodRegistry methodRegistry) {
        Map<String, MultiParamMethod> methodMap = methodRegistry.getMultiServiceMap().get(serviceName);
        if (methodMap == null){
            throw new RuntimeException("ServiceName " + serviceName + " Not Found");
        }
        MultiParamMethod queryMethod = methodMap.get(method);
        if (queryMethod == null){
            throw new RuntimeException("ServiceName " + serviceName + " Method" + method + " Not Found");
        }

        try {
            Object[] params = convertToParams(queryMethod, webRequest);
            Object result = queryMethod.invoke(params);
            return restResultFactory.success((T) result);
        }catch (Throwable e){
            log.error("Filed to run {}#{}", serviceName, method, e);
            return restResultFactory.error(e);
        }
    }

    @SneakyThrows
    private Object[] convertToParams(MultiParamMethod method, NativeWebRequest webRequest) {
        Object[] result = new Object[method.getNamedMethodParameters().length];
        for (int i=0; i< method.getNamedMethodParameters().length; i++){
            NamedMethodParameter methodParameter = method.getNamedMethodParameters()[i];
            WebDataBinderFactory webDataBinderFactory = createWebDataBinderFactory(method.getHandlerMethod());
            result[i] = resolveArgument(webRequest, methodParameter, webDataBinderFactory);
        }
        return result;
    }

    private Object resolveArgument(NativeWebRequest webRequest, MethodParameter methodParameter, WebDataBinderFactory webDataBinderFactory) {
        List<HandlerMethodArgumentResolver> argumentResolvers = this.requestMappingHandlerAdapter.getArgumentResolvers();
        return argumentResolvers.stream()
                .filter(resolver -> resolver.supportsParameter(methodParameter))
                .map(resolver -> {
                    try {
                        return resolver.resolveArgument(methodParameter, new ModelAndViewContainer(), webRequest, webDataBinderFactory);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .findFirst()
                .orElse(null);
    }

    @SneakyThrows
    private WebDataBinderFactory createWebDataBinderFactory(HandlerMethod handlerMethod){
        return (WebDataBinderFactory) MethodUtils.invokeMethod(this.requestMappingHandlerAdapter, true, "getDataBinderFactory", handlerMethod);
    }
}
