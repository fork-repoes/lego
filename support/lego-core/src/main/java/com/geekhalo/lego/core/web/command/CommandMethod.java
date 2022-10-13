package com.geekhalo.lego.core.web.command;

import lombok.Value;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/10/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class CommandMethod {
    private final Object bean;
    private final Method method;

    private final Class paramCls;
    private final Class returnCls;

    public CommandMethod(Object bean, Method method){
        this.bean = bean;
        this.method = method;

        this.paramCls = method.getParameterTypes()[0];
        this.returnCls = method.getReturnType();
    }

    public Object invoke(Object param) throws Exception{
        return MethodUtils.invokeMethod(this.bean, method.getName(), param);
    }
}
