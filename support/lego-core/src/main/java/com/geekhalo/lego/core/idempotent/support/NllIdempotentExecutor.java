package com.geekhalo.lego.core.idempotent.support;

import com.geekhalo.lego.core.idempotent.IdempotentExecutor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by taoli on 2022/11/4.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class NllIdempotentExecutor implements IdempotentExecutor {
    private static final NllIdempotentExecutor INSTANCE = new NllIdempotentExecutor();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

    public static NllIdempotentExecutor getInstance(){
        return INSTANCE;
    }
}
