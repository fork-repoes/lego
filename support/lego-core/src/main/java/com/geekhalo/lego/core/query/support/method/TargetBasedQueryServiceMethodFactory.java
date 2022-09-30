package com.geekhalo.lego.core.query.support.method;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class TargetBasedQueryServiceMethodFactory
    implements QueryServiceMethodFactory{
    private final Object target;

    public TargetBasedQueryServiceMethodFactory(Object target) {
        this.target = target;
    }

    @Override
    public QueryServiceMethod createForMethod(Method callMethod) {
        Method targetMethod = MethodUtils.getMatchingAccessibleMethod(target.getClass(), callMethod.getName(), callMethod.getParameterTypes());
        if (targetMethod != null){
            return new TargetBasedQueryServiceMethod(target, targetMethod);
        }
        return null;
    }
}
