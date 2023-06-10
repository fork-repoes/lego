package com.geekhalo.lego.core.command;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface CommandForUpdateByKey<KEY> extends CommandForUpdate {
    KEY getKey();
}
