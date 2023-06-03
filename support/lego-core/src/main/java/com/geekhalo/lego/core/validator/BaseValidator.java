package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.core.SmartComponent;

public interface BaseValidator<A> extends SmartComponent<A> {
    void validate(A a, ValidateErrorHandler validateErrorHandler);

    default void validate(A a){
        validate(a, ((name, code, msg) -> {
            throw new ValidateException(name, code, msg);
        }));
    }
}
