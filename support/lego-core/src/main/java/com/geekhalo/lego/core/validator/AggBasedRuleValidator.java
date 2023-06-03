package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.core.command.AggRoot;
import org.springframework.stereotype.Component;

@Component
public class AggBasedRuleValidator<A> implements RuleValidator<A>{
    @Override
    public boolean support(A a) {
        return a instanceof AggRoot;
    }

    @Override
    public void validate(A a, ValidateErrorHandler validateErrorHandler) {
        ((AggRoot)a).validate();
    }
}
