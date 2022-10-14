package com.geekhalo.lego.core.web.support;

import lombok.Value;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by taoli on 2022/10/14.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class MultiParamMethod {
    private final Object bean;
    private final Method method;

    public Object invoke(Object[] params) throws Exception{
        return MethodUtils.invokeMethod(this.bean, this.method.getName(), params);
    }
}
