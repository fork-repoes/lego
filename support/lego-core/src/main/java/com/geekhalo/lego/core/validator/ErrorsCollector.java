package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.common.validator.ValidateErrors;
import lombok.Getter;

public class ErrorsCollector implements ValidateErrorHandler {
    @Getter
    private ValidateErrors validateErrors;

    @Override
    public void handleError(String name, String code, String msg) {
        getOrCreateValidateErrors().addError(name, code, msg);
    }

    private ValidateErrors getOrCreateValidateErrors(){
        if (this.validateErrors == null){
            this.validateErrors = new ValidateErrors();
        }
        return this.validateErrors;
    }
}