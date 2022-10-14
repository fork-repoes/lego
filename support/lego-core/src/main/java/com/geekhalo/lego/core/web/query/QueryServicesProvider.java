package com.geekhalo.lego.core.web.query;

import com.geekhalo.lego.core.web.support.MultiParamMethod;
import com.geekhalo.lego.core.web.support.RestRequestBodyRequestHandler;
import com.geekhalo.lego.core.web.support.RestRequestParamRequestHandler;
import com.geekhalo.lego.core.web.support.SingleParamMethod;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.RequestHandler;
import springfox.documentation.spi.service.RequestHandlerProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/13.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class QueryServicesProvider implements RequestHandlerProvider {
    @Autowired
    private QueryMethodRegistry queryMethodRegistry;


    @Override
    public List<RequestHandler> requestHandlers() {
        List<RequestHandler> requestHandlers = Lists.newArrayList();
        for (Map.Entry<String, Map<String, SingleParamMethod>> entry : this.queryMethodRegistry.getSingleQueryServiceMap().entrySet()){
            String serviceName = entry.getKey();
            for (Map.Entry<String, SingleParamMethod> methodEntry : entry.getValue().entrySet()){
                String methodName = methodEntry.getKey();
                requestHandlers.add(new RestRequestBodyRequestHandler(serviceName, "BodyQuery", methodName, "bodyQuery/", methodEntry.getValue()));
            }
        }

        for (Map.Entry<String, Map<String, MultiParamMethod>> entry : this.queryMethodRegistry.getMultiQueryServiceMap().entrySet()){
            String serviceName = entry.getKey();
            for (Map.Entry<String, MultiParamMethod> methodEntry : entry.getValue().entrySet()){
                String method = methodEntry.getKey();
                requestHandlers.add(new RestRequestParamRequestHandler(serviceName, "ParamQuery", method, "paramQuery/", methodEntry.getValue()));
            }
        }
        return requestHandlers;
    }
}
