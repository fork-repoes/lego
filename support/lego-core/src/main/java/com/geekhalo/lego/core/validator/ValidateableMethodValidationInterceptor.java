package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrorReporter;
import com.geekhalo.lego.common.validator.ValidateErrors;
import com.google.common.base.Preconditions;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class ValidateableMethodValidationInterceptor implements MethodInterceptor {
    private final ValidateableBasedValidator validator;
    private final ValidateErrorReporter errorReporter;

    public ValidateableMethodValidationInterceptor(ValidateableBasedValidator validator,
                                                   ValidateErrorReporter errorReporter) {
        Preconditions.checkArgument(validator != null);
        Preconditions.checkArgument(errorReporter != null);

        this.validator = validator;
        this.errorReporter = errorReporter;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        ValidateErrors errors = new ValidateErrors();
        for (Object argument : arguments){
            validator.validate(argument, errors);
        }
        if (errors.hasError()){
            this.errorReporter.onError(errors);
        }
        return invocation.proceed();
    }
}
