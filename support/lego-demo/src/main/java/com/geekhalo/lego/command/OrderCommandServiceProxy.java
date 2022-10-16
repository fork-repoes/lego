package com.geekhalo.lego.command;

import com.geekhalo.lego.annotation.web.AutoRegisterWebController;
import com.geekhalo.lego.core.command.CommandServiceDefinition;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@CommandServiceDefinition(
        domainClass = Order.class,
        idClass = Long.class,
        repositoryClass = OrderRepository.class)
@AutoRegisterWebController(name = "order")
public interface OrderCommandServiceProxy extends OrderCommandService{
}
