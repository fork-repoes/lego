package com.geekhalo.lego.core.query;

import com.geekhalo.lego.core.joininmemory.JoinService;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/9/29.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class QueryServiceMethodAdapterFactory implements QueryServiceMethodFactory{
    private final Object repository;
    private final JoinService joinService;

    public QueryServiceMethodAdapterFactory(Object repository, JoinService joinService) {
        this.repository = repository;
        this.joinService = joinService;
    }

    @Override
    public QueryServiceMethod createForMethod(Method method) {
        return new QueryServiceMethodAdapter<>(null, null, null);
    }
}
