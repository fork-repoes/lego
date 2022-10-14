package com.geekhalo.lego.core.web.command;

import com.geekhalo.lego.core.web.support.SingleParamMethod;
import com.geekhalo.lego.core.web.support.RestRequestBodyRequestHandler;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.RequestHandler;
import springfox.documentation.spi.service.RequestHandlerProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class CommandServicesProvider implements RequestHandlerProvider {
    @Autowired
    private CommandMethodRegistry commandMethodRegistry;

    @Override
    public List<RequestHandler> requestHandlers() {
        List<RequestHandler> requestHandlers = Lists.newArrayList();
        for (Map.Entry<String, Map<String, SingleParamMethod>> entry : this.commandMethodRegistry.getCommandServiceMap().entrySet()){
            String serviceName = entry.getKey();
            for (Map.Entry<String, SingleParamMethod> methodEntry : entry.getValue().entrySet()){
                String methodName = methodEntry.getKey();
                requestHandlers.add(new RestRequestBodyRequestHandler(serviceName, "BodyCommand", methodName,"bodyCommand", methodEntry.getValue()));
            }
        }
        return requestHandlers;
    }
}
