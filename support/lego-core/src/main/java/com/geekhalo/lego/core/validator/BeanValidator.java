package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.core.SmartComponent;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface BeanValidator<A> extends SmartComponent<A> {

    void validate(A a, ValidateErrorHandler validateErrorHandler);

    default void validate(A a){
        validate(a, ((name, code, msg) -> {
            throw new ValidateException();
        }));
    }
}
