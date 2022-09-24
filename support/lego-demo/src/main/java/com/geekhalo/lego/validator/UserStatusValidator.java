package com.geekhalo.lego.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.core.validator.FixTypeBeanValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Order(1)
@Component
public class UserStatusValidator
        extends FixTypeBeanValidator<CreateOrderContext> {

    @Override
    public void validate(CreateOrderContext context, ValidateErrorHandler validateErrorHandler) {
        if (context.getUser() == null){
            validateErrorHandler.handleError("user", "1", "用户不存在");
        }
        if (!context.getUser().isEnable()){
            validateErrorHandler.handleError("user", "2", "当前用户不可以");
        }
    }
}
