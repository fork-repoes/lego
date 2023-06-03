package com.geekhalo.like.domain;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.core.validator.FixTypeBusinessValidator;
import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.user.ActionUser;
import org.springframework.stereotype.Component;

@Component
public class ActionContextValidator
        extends FixTypeBusinessValidator<AbstractActionContext> {

    @Override
    public void validate(AbstractActionContext context, ValidateErrorHandler validateErrorHandler) {
        ActionUser actionUser = context.getActionUser();
        if (actionUser == null || !actionUser.isValid()){
            validateErrorHandler.handleError("ActionUser", "404", "无效用户");
            return;
        }

        ActionTarget actionTarget = context.getActionTarget();
        if (actionTarget == null || !actionTarget.isValid()){
            validateErrorHandler.handleError("ActionTarget", "404", "无效内容");
            return;
        }
    }
}
