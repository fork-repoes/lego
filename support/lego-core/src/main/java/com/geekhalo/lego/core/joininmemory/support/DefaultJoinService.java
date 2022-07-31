package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinExecutorGroup;
import com.geekhalo.lego.core.joininmemory.JoinExecutorGroupFactory;
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
    private final JoinExecutorGroupFactory joinExecutorGroupFactory;
    private final Map<Class, JoinExecutorGroup> cache = Maps.newConcurrentMap();

    public DefaultJoinService(JoinExecutorGroupFactory joinExecutorGroupFactory) {
        this.joinExecutorGroupFactory = joinExecutorGroupFactory;
    }

    @Override
    public <T> void joinInMemory(Class<T> tCls, List<T> t) {
        JoinExecutorGroup joinExecutorGroup = this.cache.computeIfAbsent(tCls, this::createJoinExecutorGroup);
        joinExecutorGroup.execute(t);
    }

    private JoinExecutorGroup createJoinExecutorGroup(Class aClass) {
        return this.joinExecutorGroupFactory.createFor(aClass);
    }
}
