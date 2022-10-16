package com.geekhalo.lego.core.web.command;

import com.geekhalo.lego.core.command.CommandServicesRegistry;
import com.geekhalo.lego.core.web.support.WebMethodRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by taoli on 2022/10/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
public class CommandMethodRegistry extends WebMethodRegistry {
    @Autowired
    private CommandServicesRegistry commandServicesRegistry;


    @Override
    protected List<Object> getServices() {
        return commandServicesRegistry.getCommandServices();
    }
}
