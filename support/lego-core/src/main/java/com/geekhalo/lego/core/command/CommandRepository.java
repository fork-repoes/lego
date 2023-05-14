package com.geekhalo.lego.core.command;


import java.util.Optional;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface CommandRepository<E extends AggRoot<ID>, ID>{

    E sync(E entity);

    Optional<E> findById(ID id);
}
