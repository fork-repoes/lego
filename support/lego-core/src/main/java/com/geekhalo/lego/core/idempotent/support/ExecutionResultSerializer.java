package com.geekhalo.lego.core.idempotent.support;

/**
 * Created by taoli on 2022/11/5.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface ExecutionResultSerializer {
    <T> String serializeResult(T data);

    <T> T deserializeResult(String data, Class<T> cls);

    <T extends Exception> String serializeException(T data);

    <T extends Exception> T deserializeException(String data);
}
