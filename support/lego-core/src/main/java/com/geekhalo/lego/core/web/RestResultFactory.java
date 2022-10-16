package com.geekhalo.lego.core.web;

/**
 * Created by taoli on 2022/10/15.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface RestResultFactory {
    <T> RestResult<T> success(T t);

    <T> RestResult<T> error(Throwable error);
}
