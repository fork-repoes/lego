package com.geekhalo.lego.core.web.query;

import com.geekhalo.lego.annotation.web.AutoRegisterWebController;
import com.geekhalo.lego.core.query.QueryServicesRegistry;
import com.geekhalo.lego.core.support.proxy.ProxyObject;
import com.geekhalo.lego.core.web.support.SingleParamMethod;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class QueryMethodRegistry {
    @Autowired
    private QueryServicesRegistry queryServicesRegistry;

    @Getter
    private Map<String, Map<String, SingleParamMethod>> queryServiceMap = Maps.newHashMap();

    @PostConstruct
    public void init(){
        List<Object> queryServices = this.queryServicesRegistry.getQueryServices();
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
            Map<String, SingleParamMethod> methodMap = this.queryServiceMap.get(name);
            if (methodMap != null){
                throw new RuntimeException("Find More Than One Service " + name);
            }
            methodMap = Maps.newHashMap();
            this.queryServiceMap.put(name, methodMap);
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
    }

    private boolean isSameMethod(Method method, Method method1) {
        return  method.getName().equals(method1.getName())
                && Arrays.equals(method.getParameterTypes(), method1.getParameterTypes());
    }
}
