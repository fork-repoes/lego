package com.geekhalo.lego.core.web.command;

import com.geekhalo.lego.core.web.support.MultiParamMethod;
import com.geekhalo.lego.core.web.support.RestRequestParamRequestHandler;
import com.geekhalo.lego.core.web.support.SingleParamMethod;
import com.geekhalo.lego.core.web.support.RestRequestBodyRequestHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
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
        for (Map.Entry<String, Map<String, SingleParamMethod>> entry : this.commandMethodRegistry.getSingleQueryServiceMap().entrySet()){
            String serviceName = entry.getKey();
            for (Map.Entry<String, SingleParamMethod> methodEntry : entry.getValue().entrySet()){
                String methodName = methodEntry.getKey();
                requestHandlers.add(new RestRequestBodyRequestHandler(serviceName,
                        "CommandByBody",
                        methodName,
                        CommandDispatcherController.COMMAND_BY_BODY_PATH,
                        methodEntry.getValue()));
            }
        }
        for (Map.Entry<String, Map<String, MultiParamMethod>> entry : this.commandMethodRegistry.getMultiQueryServiceMap().entrySet()){
            String serviceName = entry.getKey();
            for (Map.Entry<String, MultiParamMethod> methodEntry : entry.getValue().entrySet()){
                String method = methodEntry.getKey();
                requestHandlers.add(new RestRequestParamRequestHandler(serviceName,
                        "CommandByParam",
                        method,
                        CommandDispatcherController.COMMAND_BY_PARAM_PATH,
                        methodEntry.getValue(),
                        Sets.newHashSet(RequestMethod.POST)));
            }
        }
        return requestHandlers;
    }
}
