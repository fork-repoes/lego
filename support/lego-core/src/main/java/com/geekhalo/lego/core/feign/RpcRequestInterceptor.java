package com.geekhalo.lego.core.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by taoli on 2022/11/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class RpcRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(RpcConstants.RPC_TAG_HEADER, RpcConstants.RPC_TAG_VALUE);
    }
}
