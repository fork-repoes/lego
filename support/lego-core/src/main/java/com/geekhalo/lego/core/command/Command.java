package com.geekhalo.lego.core.command;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface Command {
    default void validate(ValidateErrorHandler errorHandler){

    }
}
