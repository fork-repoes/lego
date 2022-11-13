package com.geekhalo.lego.starter.feign;

import com.geekhalo.lego.core.feign.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by taoli on 2022/11/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
public class FeignRpcConfiguration {
    @Bean
    public RpcRequestInterceptor rpcRequestInterceptor(){
        return new RpcRequestInterceptor();
    }

    @Bean
    public RpcHandlerExceptionResolver rpcExceptionHandler(){
        return new RpcHandlerExceptionResolver();
    }

    @Bean
    public RpcErrorDecoder rpcErrorDecoder(RpcExceptionResolvers resolvers){
        return new RpcErrorDecoder(resolvers);
    }

    @Bean
    public RpcExceptionResolvers rpcExceptionResolvers(List<RpcExceptionResolver> exceptionResolvers){
        return new RpcExceptionResolvers(exceptionResolvers);
    }

    @Bean
    public SimpleRpcExceptionResolver simpleRpcExceptionResolver(){
        return new SimpleRpcExceptionResolver();
    }

}
