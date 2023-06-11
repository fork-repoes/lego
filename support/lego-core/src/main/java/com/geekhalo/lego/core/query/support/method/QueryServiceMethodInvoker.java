package com.geekhalo.lego.core.query.support.method;

import com.geekhalo.lego.core.query.support.handler.DefaultQueryHandler;
import com.geekhalo.lego.core.query.support.handler.QueryHandler;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
@Builder
@Slf4j
public class QueryServiceMethodInvoker<Q, R>
        implements com.geekhalo.lego.core.support.invoker.ServiceMethodInvoker {
    private final QueryHandler<R> queryHandler;

    @Override
    public final Object invoke(Method method, Object[] arguments) {
        return queryHandler.query(arguments);
    }

    @Override
    public String toString(){
        return this.queryHandler.toString();
    }
}
