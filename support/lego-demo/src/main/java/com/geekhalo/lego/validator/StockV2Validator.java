package com.geekhalo.lego.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.core.validator.FixTypeBeanValidator;
import com.geekhalo.lego.loader.CreateOrderContextV2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Component
@Order(3)
public class StockV2Validator
        extends FixTypeBeanValidator<CreateOrderContextV2> {

    @Override
    public void validate(CreateOrderContextV2 context, ValidateErrorHandler validateErrorHandler) {
        if (context.getStock() == null){
            validateErrorHandler.handleError("stock", "3", "库存不存在");
        }
        if (context.getStock().getCount() < context.getCmd().getCount()){
            validateErrorHandler.handleError("stock", "4", "库存不足");
        }
    }
}
