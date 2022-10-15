package com.geekhalo.lego.core.web.support;

import com.geekhalo.lego.annotation.web.AutoRegisterWebController;
import com.geekhalo.lego.core.support.proxy.ProxyObject;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/15.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public abstract class MethodRegistry {
    @Getter
    private Map<String, Map<String, SingleParamMethod>> singleQueryServiceMap = Maps.newHashMap();

    @Getter
    private Map<String, Map<String, MultiParamMethod>> multiQueryServiceMap = Maps.newHashMap();

    protected abstract List<Object> getServices();

    @PostConstruct
    public void init(){
        List<Object> queryServices = this.getServices();
        for (Object queryService : queryServices){
            AutoRegisterWebController autoRegisterWebController =
                    AnnotatedElementUtils.findMergedAnnotation(queryService.getClass(), AutoRegisterWebController.class);
            if (autoRegisterWebController == null){
                continue;
            }
            if (!(queryService instanceof ProxyObject)){
                continue;
            }
            Class itf = ((ProxyObject) queryService).getInterface();
            String name = autoRegisterWebController.name();

            buildSingleParamMethods(queryService, itf, name);

            buildMultiParamMethods(queryService, itf, name);
        }
    }

    private void buildMultiParamMethods(Object queryService, Class itf, String name) {
        Map<String, MultiParamMethod> methodMap = this.multiQueryServiceMap.get(name);
        if (methodMap != null){
            throw new RuntimeException("Find More Than One Service " + name);
        }
        methodMap = Maps.newHashMap();
        this.multiQueryServiceMap.put(name, methodMap);
        for (Method method : ReflectionUtils.getAllDeclaredMethods(itf)){
            String methodName = method.getName();
            MultiParamMethod methodInMap = methodMap.get(methodName);
            if (methodInMap != null && !isSameMethod(methodInMap.getMethod(), method)){
                throw new RuntimeException("Find More Than One Method " + methodName +" in Service " + name);
            }
            methodMap.put(methodName, new MultiParamMethod(queryService, method));
        }
    }

    private void buildSingleParamMethods(Object queryService, Class itf, String name) {
        Map<String, SingleParamMethod> methodMap = this.singleQueryServiceMap.get(name);
        if (methodMap != null){
            throw new RuntimeException("Find More Than One Service " + name);
        }
        methodMap = Maps.newHashMap();
        this.singleQueryServiceMap.put(name, methodMap);
        for (Method method : ReflectionUtils.getAllDeclaredMethods(itf)){
            if (method.getParameterCount() == 1){
                String methodName = method.getName();
                SingleParamMethod methodInMap = methodMap.get(methodName);
                if (methodInMap != null && !isSameMethod(methodInMap.getMethod(), method)){
                    throw new RuntimeException("Find More Than One Method " + methodName +" in Service " + name);
                }
                methodMap.put(methodName, new SingleParamMethod(queryService, method));
            }
        }
    }

    private boolean isSameMethod(Method method, Method method1) {
        return  method.getName().equals(method1.getName())
                && Arrays.equals(method.getParameterTypes(), method1.getParameterTypes());
    }
}
