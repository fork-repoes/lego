package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.common.validator.Verifiable;
import org.springframework.stereotype.Component;

@Component
public class VerifiableBasedParamValidator implements ParamValidator<Object>{
    @Override
    public boolean support(Object verifiable) {
        return verifiable instanceof Verifiable;
    }

    @Override
    public void validate(Object verifiable, ValidateErrorHandler validateErrorHandler) {
        ((Verifiable) verifiable).validate(validateErrorHandler);
    }
}
