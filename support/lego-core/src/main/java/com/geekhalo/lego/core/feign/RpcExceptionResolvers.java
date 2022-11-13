package com.geekhalo.lego.core.feign;

import com.google.common.collect.Lists;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

/**
 * Created by taoli on 2022/11/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class RpcExceptionResolvers {
    private final List<RpcExceptionResolver> exceptionResolvers = Lists.newArrayList();

    public RpcExceptionResolvers(List<RpcExceptionResolver> exceptionResolvers){
        setExceptionResolvers(exceptionResolvers);
    }

    public Exception resolve(String methodKey, int status, String appName, RpcErrorResult errorResult){
        return this.exceptionResolvers.stream()
                .map(rpcExceptionResolver -> rpcExceptionResolver.resolve(methodKey, status, appName, errorResult))
                .findFirst()
                .orElse(null);
    }

    public void setExceptionResolvers(List<RpcExceptionResolver> exceptionResolvers){
        this.exceptionResolvers.addAll(exceptionResolvers);
        AnnotationAwareOrderComparator.sort(this.exceptionResolvers);
    }
}
