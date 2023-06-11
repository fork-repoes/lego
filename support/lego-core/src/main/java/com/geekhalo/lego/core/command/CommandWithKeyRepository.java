package com.geekhalo.lego.core.command;


import java.util.Optional;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface CommandWithKeyRepository<E extends AggRoot<ID>, ID, KEY>
    extends CommandRepository<E, ID>{
    Optional<E> findByKey(KEY key);
}
