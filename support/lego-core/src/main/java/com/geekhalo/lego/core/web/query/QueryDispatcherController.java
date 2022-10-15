package com.geekhalo.lego.core.web.query;

import com.alibaba.fastjson.JSON;
import com.geekhalo.lego.core.web.RestResult;
import com.geekhalo.lego.core.web.support.MultiParamMethod;
import com.geekhalo.lego.core.web.support.SingleParamMethod;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.DefaultDataBinderFactory;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by taoli on 2022/10/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@RestController
public class QueryDispatcherController {
    @Autowired
    private QueryMethodRegistry queryMethodRegistry;

    private WebDataBinderFactory webDataBinderFactory = new DefaultDataBinderFactory(null);

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostMapping("/bodyQuery/{serviceName}/{method}")
    public <T> RestResult<T> runBodyQuery(
            @PathVariable String serviceName,
            @PathVariable String method,
            @RequestBody String payload){
        Map<String, SingleParamMethod> methodMap = this.queryMethodRegistry.getSingleQueryServiceMap().get(serviceName);
        if (methodMap == null){
            throw new RuntimeException("ServiceName " + serviceName + " Not Found");
        }
        SingleParamMethod queryMethod = methodMap.get(method);
        if (queryMethod == null){
            throw new RuntimeException("ServiceName " + serviceName + " Method" + method + " Not Found");
        }

        Type paramCls = queryMethod.getParamCls();
        try {
            Object param = JSON.parseObject(payload, paramCls);
            Object result = queryMethod.invoke(param);
            return RestResult.success((T)result);
        }catch (Exception e){
            return RestResult.error("Error");
        }
    }

    @RequestMapping(value = "/paramQuery/{serviceName}/{method}", method = {RequestMethod.POST, RequestMethod.GET})
    public <T> RestResult<T> runParamQuery(
            @PathVariable String serviceName,
            @PathVariable String method,
            NativeWebRequest webRequest){
        Map<String, MultiParamMethod> methodMap = this.queryMethodRegistry.getMultiQueryServiceMap().get(serviceName);
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
            return RestResult.success((T)result);
        }catch (Exception e){
            return RestResult.error("Error");
        }
    }

    @SneakyThrows
    private Object[] convertToParams(MultiParamMethod method, NativeWebRequest webRequest) {
        List<HandlerMethodArgumentResolver> argumentResolvers = this.requestMappingHandlerAdapter.getArgumentResolvers();
        Object[] result = new Object[method.getParamTypes().length];

        for (int i = 0; i < method.getParamNames().length; i++){
            String paramName = method.getParamNames()[i];
            Class paramCls = method.getParamTypes()[i];
            Object dataBinderFactory = MethodUtils.invokeMethod(this.requestMappingHandlerAdapter, true, "getDataBinderFactory", new HandlerMethod(method.getBean(), method.getMethod()));
            MethodParameter methodParameter = new NamedMethodParameter(method.getMethod(), i, paramName, BeanUtils.isSimpleValueType(paramCls));
            result[i] = argumentResolvers.stream()
                    .filter(resolver -> resolver.supportsParameter(methodParameter))
                    .map(resolver -> {
                        try {
                            return resolver.resolveArgument(methodParameter, new ModelAndViewContainer(), webRequest, (WebDataBinderFactory) dataBinderFactory);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .findFirst()
                    .orElse(null);
        }
        return result;
    }


    class NamedMethodParameter extends MethodParameter{
        private final String name;
        private final boolean param;
        private final boolean modelAttribute;
        public NamedMethodParameter(Method method, int parameterIndex,
                                    String name, boolean param) {
            super(method, parameterIndex);
            this.name = name;
            this.param = param;
            this.modelAttribute = !this.param;
        }

        @Override
        public String getParameterName(){
            if (StringUtils.isNotEmpty(name)){
                return name;
            }
            return "";
        }

        @Override
        public <A extends Annotation> boolean hasParameterAnnotation(Class<A> annotationType) {
            if (param && annotationType == RequestParam.class){
                return true;
            }
            if (modelAttribute && annotationType == ModelAttribute.class){
                return true;
            }
            return false;
        }
    }

}
