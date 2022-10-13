package com.geekhalo.lego.core.web.support;

import lombok.Value;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by taoli on 2022/10/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class SingleParamMethod {
    private final Object bean;
    private final Method method;

    private final Type paramCls;

    public SingleParamMethod(Object bean, Method method){
        this.bean = bean;
        this.method = method;
        this.paramCls = method.getParameterTypes()[0];
    }

    public Object invoke(Object param) throws Exception{
        return MethodUtils.invokeMethod(this.bean, method.getName(), param);
    }
}
