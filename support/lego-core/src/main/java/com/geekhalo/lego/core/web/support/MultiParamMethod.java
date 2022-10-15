package com.geekhalo.lego.core.web.support;

import lombok.Getter;
import lombok.Value;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by taoli on 2022/10/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class MultiParamMethod {
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private final Object bean;
    private final Method method;
    @Getter
    private final Class[] paramTypes;
    @Getter
    private final String[] paramNames;

    public MultiParamMethod(Object bean, Method method){
        this.bean = bean;
        this.method = method;
        this.paramTypes = method.getParameterTypes();
        this.paramNames = PARAMETER_NAME_DISCOVERER.getParameterNames(this.method);
    }

    public Object invoke(Object[] params) throws Exception{
        return MethodUtils.invokeMethod(this.bean, this.method.getName(), params);
    }
}
