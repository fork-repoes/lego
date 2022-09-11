package com.geekhalo.lego.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.core.validator.BeanValidator;
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
public class UserExistValidator
        extends FixTypeBeanValidator<CreateOrderContext> {

    @Override
    public void validate(CreateOrderContext context, ValidateErrorHandler validateErrorHandler) {
        if (context.getUser() == null){
            validateErrorHandler.handleError("user", "1", "用户不存在");
        }
    }
}
