package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.common.validator.ValidateErrors;
import com.geekhalo.lego.common.validator.ValidateErrorsHandler;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class ValidateableMethodValidationInterceptor implements MethodInterceptor {
    private final ValidateableBasedValidator validator;
    private final ValidateErrorsHandler errorsHandler;

    public ValidateableMethodValidationInterceptor(ValidateableBasedValidator validator,
                                                   ValidateErrorsHandler errorsHandler) {
        Preconditions.checkArgument(validator != null);
        Preconditions.checkArgument(errorsHandler != null);

        this.validator = validator;
        this.errorsHandler = errorsHandler;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        if (arguments.length > 0) {
            ErrorsCollector errorsCollector = new ErrorsCollector();
            for (Object argument : arguments) {
                validator.validate(argument, errorsCollector);
            }
            ValidateErrors errors = errorsCollector.getValidateErrors();
            if (errors != null && errors.hasError()) {
                this.errorsHandler.handleErrors(errors);
            }
        }
        return invocation.proceed();
    }

    static class ErrorsCollector implements ValidateErrorHandler{
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
}
