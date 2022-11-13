package com.geekhalo.lego.core.feign;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.geekhalo.lego.core.feign.RpcConstants.PRC_RESULT_CODE_DEF;

/**
 * Created by taoli on 2022/11/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */

@Slf4j
@Order(0)
public class RpcHandlerExceptionResolver implements HandlerExceptionResolver {
    @Value("${spring.application.name:Unknown}")
    private String applicationName;

    @Value("${feign.service.rpcErrorHttpStatus:520}")
    private Integer rpcErrorHttpStatus;

    @Value("${feign.service.useCodeAsStatus:true}")
    private Boolean useCodeAsStatus;

    @SneakyThrows
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        String tag = request.getHeader(RpcConstants.RPC_TAG_HEADER);
        if (RpcConstants.RPC_TAG_VALUE.equals(tag)){
            response.addHeader(RpcConstants.RPC_REMOTE_APPLICATION_NAME_HEADER, this.applicationName);
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
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
                String json = RpcErrorResultSerializer.encode(errorResult);
                response.getWriter().print(json);
                return new ModelAndView();
            }else {
                response.setStatus(rpcErrorHttpStatus);
                RpcErrorResult errorResult = new RpcErrorResult();
                errorResult.setCode(PRC_RESULT_CODE_DEF);
                errorResult.setMsg(e.getMessage());
                log.warn("Rpc Exception, return is code {}, Result {}", response.getStatus(), errorResult, e);
                String json = RpcErrorResultSerializer.encode(errorResult);
                response.getWriter().print(json);
                return new ModelAndView();
            }
        }
        return null;
    }
}
