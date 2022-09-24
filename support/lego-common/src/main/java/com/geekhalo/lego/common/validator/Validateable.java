package com.geekhalo.lego.common.validator;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface Validateable {
    void validate(ValidateErrorHandler validateErrorHandler);
}
