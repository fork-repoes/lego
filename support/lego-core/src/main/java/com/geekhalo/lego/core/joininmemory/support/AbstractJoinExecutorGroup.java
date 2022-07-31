package com.geekhalo.lego.core.joininmemory.support;

import com.geekhalo.lego.core.joininmemory.JoinExecutor;
import com.geekhalo.lego.core.joininmemory.JoinExecutorGroup;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
abstract class AbstractJoinExecutorGroup<DATA>
        implements JoinExecutorGroup<DATA> {
    @Getter(AccessLevel.PROTECTED)
    private final Class<DATA> dataCls;
    @Getter(AccessLevel.PROTECTED)
    private final List<JoinExecutor<DATA>> joinExecutors;

    public AbstractJoinExecutorGroup(Class<DATA> dataCls,
                              List<JoinExecutor<DATA>> joinExecutors) {
        Preconditions.checkArgument(dataCls != null);
        Preconditions.checkArgument(joinExecutors != null);

        this.dataCls = dataCls;
        this.joinExecutors = joinExecutors;
        Collections.sort(this.joinExecutors, Comparator.comparingInt(JoinExecutor::runOnLevel));
    }
}
