package com.geekhalo.lego.core.web.command;

import com.alibaba.fastjson.JSON;
import com.geekhalo.lego.annotation.web.AutoRegisterWebController;
import com.geekhalo.lego.core.command.CommandServicesRegistry;
import com.geekhalo.lego.core.support.proxy.ProxyObject;
import com.geekhalo.lego.core.web.RestResult;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.util.ProxyUtils;
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@RestController
@RequestMapping("command")
public class CommandDispatcherController {
    @Autowired
    private CommandMethodRegistry commandMethodRegistry;

    @PostMapping("{serviceName}/{method}")
    public <T> RestResult<T> runCommand(
            @PathVariable String serviceName,
            @PathVariable String method,
            @RequestBody String payload){
        Map<String, CommandMethod> methodMap = this.commandMethodRegistry.getCommandServiceMap().get(serviceName);
        if (methodMap == null){
            throw new RuntimeException("ServiceName " + serviceName + " Not Found");
        }
        CommandMethod commandMethod = methodMap.get(method);
        if (commandMethod == null){
            throw new RuntimeException("ServiceName " + serviceName + " Method" + method + " Not Found");
        }

        Class paramCls = commandMethod.getParamCls();
        try {
            Object param = JSON.parseObject(payload, paramCls);
            Object result = commandMethod.invoke(param);
            return RestResult.success((T)result);
        }catch (Exception e){
            return RestResult.error("Error");
        }
    }

}
