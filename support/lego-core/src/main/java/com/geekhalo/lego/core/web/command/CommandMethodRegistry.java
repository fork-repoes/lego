package com.geekhalo.lego.core.web.command;

import com.geekhalo.lego.annotation.web.AutoRegisterWebController;
import com.geekhalo.lego.core.command.CommandServicesRegistry;
import com.geekhalo.lego.core.support.proxy.ProxyObject;
import com.geekhalo.lego.core.web.support.MethodRegistry;
import com.geekhalo.lego.core.web.support.SingleParamMethod;
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
public class CommandMethodRegistry extends MethodRegistry {
    @Autowired
    private CommandServicesRegistry commandServicesRegistry;


    @Override
    protected List<Object> getServices() {
        return commandServicesRegistry.getCommandServices();
    }
}
