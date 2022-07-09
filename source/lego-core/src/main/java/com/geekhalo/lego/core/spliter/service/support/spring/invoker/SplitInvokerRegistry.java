//package com.geekhalo.lego.core.spliter.service.support.spring.invoker;
//
//import com.google.common.collect.Maps;
//
//import java.lang.reflect.Method;
//import java.util.Map;
//
//public class SplitInvokerRegistry {
//    private final Map<Method, SplitInvoker> registryMap = Maps.newHashMap();
//
//    public SplitInvoker getByMethod(Method method){
//        return registryMap.get(method);
//    }
//
//    public void register(Method method, SplitInvoker splitInvoker){
//        this.registryMap.put(method, splitInvoker);
//    }
//}
