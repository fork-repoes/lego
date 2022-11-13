package com.geekhalo.lego.feign;

import com.geekhalo.lego.core.feign.RpcErrorResult;
import com.geekhalo.lego.core.feign.RpcExceptionResolver;
import org.springframework.stereotype.Component;

/**
 * Created by taoli on 2022/11/12.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class CustomExceptionResolver implements RpcExceptionResolver {
    @Override
    public Exception resolve(String methodKey, int status, String remoteAppName, RpcErrorResult errorResult) {
        if (errorResult.getCode() == CustomException.CODE){
            throw new CustomException();
        }
        return null;
    }
}
