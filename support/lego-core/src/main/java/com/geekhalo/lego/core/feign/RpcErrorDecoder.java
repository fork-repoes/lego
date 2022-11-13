package com.geekhalo.lego.core.feign;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * Created by taoli on 2022/11/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Slf4j
public class RpcErrorDecoder implements ErrorDecoder {
    private final RpcExceptionResolvers rpcExceptionResolvers;

    public RpcErrorDecoder(RpcExceptionResolvers rpcExceptionResolvers) {
        this.rpcExceptionResolvers = rpcExceptionResolvers;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        RpcErrorResult errorResult = convertToErrorResult(response);
        Collection<String> appNames = response.headers().get(RpcConstants.RPC_REMOTE_APPLICATION_NAME_HEADER);
        String appName = CollectionUtils.isNotEmpty(appNames) ? appNames.iterator().next() : "Unknown";
        log.warn("Rpc Exception, receive is methodKey {}, status {}, appName {}, Result {}",
                methodKey, response.status(), appName, errorResult);
        return this.rpcExceptionResolvers.resolve(methodKey, response.status(), appName, errorResult);
    }

    private RpcErrorResult convertToErrorResult(Response response) {
        RpcErrorResult errorResult = null;
        try {
            String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            errorResult = RpcErrorResultSerializer.decode(body);
        } catch (IOException e) {
            log.error("feign.IOException", e);
        }
        return errorResult;
    }
}
