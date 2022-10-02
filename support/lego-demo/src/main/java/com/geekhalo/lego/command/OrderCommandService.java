package com.geekhalo.lego.command;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface OrderCommandService {
    Long create(CreateOrderCommand command);

    void paySuccess(PaySuccessCommand command);
}
