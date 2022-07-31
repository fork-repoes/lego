package com.geekhalo.lego.core.joininmemory;

/**
 * Created by taoli on 2022/7/31.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface JoinExecutorGroupFactory {
    <D> JoinExecutorGroup<D> createFor(Class<D> cls);
}
