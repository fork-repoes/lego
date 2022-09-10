package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrors;
import com.geekhalo.lego.common.validator.Validateable;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class ValidateableBasedValidator{

    public void validate(Object target, ValidateErrors errorReporter) {
        if (target instanceof Validateable){
            Validateable validateable = (Validateable) target;
            validateable.validate(errorReporter);
        }
    }
}
