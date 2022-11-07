package com.geekhalo.lego.core.idempotent;

import com.geekhalo.lego.annotation.idempotent.IdempotentHandleType;

/**
 * Created by taoli on 2022/11/4.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface IdempotentMeta {
    String executorFactory();

    int group();

    String[] paramNames();

    String keyEl();

    IdempotentHandleType handleType();

    Class returnType();

}
