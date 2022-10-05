package com.geekhalo.lego.core.command;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface CommandRepository<E extends AggRoot<ID>, ID> extends CrudRepository<E, ID> {

}
