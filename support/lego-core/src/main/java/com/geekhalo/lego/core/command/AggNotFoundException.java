package com.geekhalo.lego.core.command;

import lombok.Value;

/**
 * Created by taoli on 2022/10/3.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class AggNotFoundException extends RuntimeException{
    private Object id;

}
