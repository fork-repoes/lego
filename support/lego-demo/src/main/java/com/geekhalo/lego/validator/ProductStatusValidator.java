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
@Component
@Order(2)
public class ProductStatusValidator
        extends FixTypeBeanValidator<CreateOrderContext> {

    @Override
    public void validate(CreateOrderContext context, ValidateErrorHandler validateErrorHandler) {
        if(context.getProduct() == null){
            validateErrorHandler.handleError("product", "2", "商品不存在");
        }
        if (!context.getProduct().isSaleable()){
            validateErrorHandler.handleError("product", "3", "商品不可售卖");
        }
    }
}
