package com.geekhalo.lego.validator;

import com.geekhalo.lego.loader.CreateOrderCmd;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Created by taoli on 2022/9/11.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Validated
public interface DomainValidateService {
    void createOrder(@NotNull CreateOrderContext context);

    void createOrder(@NotNull CreateOrderCmd cmd);
}
