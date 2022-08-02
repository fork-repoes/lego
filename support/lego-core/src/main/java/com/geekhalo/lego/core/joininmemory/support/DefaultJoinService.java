package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinItemsExecutor;
import com.geekhalo.lego.core.joininmemory.JoinItemsExecutorFactory;
import com.geekhalo.lego.core.joininmemory.JoinService;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class DefaultJoinService implements JoinService {
    private final JoinItemsExecutorFactory joinItemsExecutorFactory;
    private final Map<Class, JoinItemsExecutor> cache = Maps.newConcurrentMap();

    public DefaultJoinService(JoinItemsExecutorFactory joinItemsExecutorFactory) {
        this.joinItemsExecutorFactory = joinItemsExecutorFactory;
    }

    @Override
    public <T> void joinInMemory(Class<T> tCls, List<T> t) {
        JoinItemsExecutor joinItemsExecutor = this.cache.computeIfAbsent(tCls, this::createJoinExecutorGroup);
        joinItemsExecutor.execute(t);
    }

    private JoinItemsExecutor createJoinExecutorGroup(Class aClass) {
        return this.joinItemsExecutorFactory.createFor(aClass);
    }
}
