package com.geekhalo.lego.core.command;

import java.util.Date;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface DomainEvent<ID, AGG extends AggRoot<ID>> {

    Date createAt();

    AGG source();

    default AGG getSource(){
        return source();
    }

    default Date getCreateTime(){
        return createAt();
    }
}
