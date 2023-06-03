package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.common.validator.Verifiable;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class VerifiableBasedValidator {

    public void validate(Object target, ValidateErrorHandler validateErrorHandler) {
        if (target instanceof Verifiable){
            ((Verifiable) target).validate(validateErrorHandler);
        }
    }
}
