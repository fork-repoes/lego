package com.geekhalo.lego.core.validator;

import com.geekhalo.lego.common.validator.ValidateErrors;
import com.geekhalo.lego.common.validator.ValidateErrorsHandler;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.List;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class VerifiableMethodValidationInterceptor implements MethodInterceptor {
    private final ValidateService validateService;

    public VerifiableMethodValidationInterceptor(ValidateService validateService) {
        Preconditions.checkArgument(validateService != null);
        this.validateService = validateService;

    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        if (arguments.length > 0) {
            List<Object> params = Lists.newArrayList();
            ErrorsCollector errorsCollector = new ErrorsCollector();
            for (Object argument : arguments) {
                params.add(argument);
            }
            this.validateService.validateParam(params);
        }
        return invocation.proceed();
    }


}
