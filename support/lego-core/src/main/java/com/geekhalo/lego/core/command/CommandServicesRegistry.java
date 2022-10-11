package com.geekhalo.lego.core.command;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by taoli on 2022/10/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class CommandServicesRegistry {
    @Autowired
    private ApplicationContext applicationContext;

    @Getter
    private List<Object> commandServices = Lists.newArrayList();

    @PostConstruct
    public void init(){
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(CommandServiceDefinition.class);
        this.commandServices.addAll(beansWithAnnotation.values());
    }
}
