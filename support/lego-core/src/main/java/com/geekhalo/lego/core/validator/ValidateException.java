package com.geekhalo.lego.core.validator;

import lombok.Value;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Value
public class ValidateException extends RuntimeException{
    private final String name;
    private final String code;
    private final String msg;
}
