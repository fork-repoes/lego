package com.geekhalo.lego.core.web.support;

import com.google.common.collect.Sets;
import lombok.Value;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;

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
    private final HandlerMethod handlerMethod;
    private final Type paramCls;
    private final NamedMethodParameter methodParameter;

    public SingleParamMethod(Object bean, Method method){
        this.bean = bean;
        this.method = method;
        this.handlerMethod = new HandlerMethod(this.bean, method);
        this.paramCls = method.getParameterTypes()[0];
        this.methodParameter = new NamedMethodParameter(this.method, 0, "data", Sets.newHashSet(RequestBody.class));
    }

    public Object invoke(Object param) throws Exception{
        return MethodUtils.invokeMethod(this.bean, method.getName(), param);
    }
}
