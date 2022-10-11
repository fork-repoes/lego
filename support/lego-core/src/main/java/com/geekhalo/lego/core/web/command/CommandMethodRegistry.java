package com.geekhalo.lego.core.web.command;

import com.geekhalo.lego.annotation.web.AutoRegisterWebController;
import com.geekhalo.lego.core.command.CommandServicesRegistry;
import com.geekhalo.lego.core.support.proxy.ProxyObject;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class CommandMethodRegistry {
    @Autowired
    private CommandServicesRegistry commandServicesRegistry;

    @Getter
    private Map<String, Map<String, CommandMethod>> commandServiceMap = Maps.newHashMap();

    @PostConstruct
    public void init(){
        List<Object> commandServices = this.commandServicesRegistry.getCommandServices();
        for (Object command : commandServices){
            AutoRegisterWebController autoRegisterWebController =
                    AnnotatedElementUtils.findMergedAnnotation(command.getClass(), AutoRegisterWebController.class);
            if (autoRegisterWebController == null){
                continue;
            }
            if (!(command instanceof ProxyObject)){
                continue;
            }
            Class itf = ((ProxyObject) command).getInterface();
            String name = autoRegisterWebController.name();
            Map<String, CommandMethod> methodMap = this.commandServiceMap.get(name);
            if (methodMap != null){
                throw new RuntimeException("Find More Than One Service " + name);
            }
            methodMap = Maps.newHashMap();
            this.commandServiceMap.put(name, methodMap);
            for (Method method : ReflectionUtils.getAllDeclaredMethods(itf)){

                if (method.getParameterCount() == 1){
                    String methodName = method.getName();
                    CommandMethod methodInMap = methodMap.get(methodName);
                    if (methodInMap != null){
                        throw new RuntimeException("Find More Than One Method " + methodName +" in Service " + name);
                    }
                    methodMap.put(methodName, new CommandMethod(command, method));
                }
            }
        }
    }
}
