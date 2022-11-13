package com.geekhalo.lego.core.feign;

/**
 * Created by taoli on 2022/11/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface RpcExceptionResolver {
    Exception resolve(String methodKey, int status, String remoteAppName, RpcErrorResult errorResult);
}
