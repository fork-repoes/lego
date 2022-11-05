package com.geekhalo.lego.core.idempotent;

/**
 * Created by taoli on 2022/11/4.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface IdempotentExecutorFactory {
    IdempotentExecutor create(IdempotentMeta meta);
}
