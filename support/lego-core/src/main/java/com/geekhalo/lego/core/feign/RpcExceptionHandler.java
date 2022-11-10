package com.geekhalo.lego.core.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

import static com.geekhalo.lego.core.feign.RpcConstants.PRC_RESULT_CODE_DEF;

/**
 * Created by taoli on 2022/11/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@RestControllerAdvice("com")
@Slf4j
public class RpcExceptionHandler {
    @Value("${spring.application.name:Unknown}")
    private String applicationName;

    @Value("${feign.service.rpcErrorHttpStatus:520}")
    private Integer rpcErrorHttpStatus;

    @Value("${feign.service.useCodeAsStatus:true}")
    private Boolean useCodeAsStatus;

    @ExceptionHandler
    public RpcErrorResult handleException(Exception e, HttpServletResponse response){
        response.addHeader(RpcConstants.RPC_REMOTE_APPLICATION_NAME_HEADER, this.applicationName);

        if (e instanceof CodeBasedException){
            CodeBasedException codeBasedException = (CodeBasedException) e;
            if (useCodeAsStatus){
                response.setStatus(codeBasedException.getErrorCode());
            }else {
                response.setStatus(rpcErrorHttpStatus);
            }
            RpcErrorResult errorResult = new RpcErrorResult();
            errorResult.setCode(codeBasedException.getErrorCode());
            errorResult.setMsg(codeBasedException.getErrorMsg());
            log.warn("Rpc Exception, return is code {}, Result {}", response.getStatus(), errorResult, e);
            return errorResult;
        }else {
            response.setStatus(rpcErrorHttpStatus);
            RpcErrorResult errorResult = new RpcErrorResult();
            errorResult.setCode(PRC_RESULT_CODE_DEF);
            errorResult.setMsg(e.getMessage());
            log.warn("Rpc Exception, return is code {}, Result {}", response.getStatus(), errorResult, e);
            return errorResult;
        }
    }
}
