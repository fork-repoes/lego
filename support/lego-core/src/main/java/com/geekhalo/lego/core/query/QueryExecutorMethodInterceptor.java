package com.geekhalo.lego.core.query;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by taoli on 2022/9/28.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 自动创建 Query 方法，完成查询逻辑
 */
public class QueryExecutorMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }
}
