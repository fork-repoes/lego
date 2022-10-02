package com.geekhalo.lego.command;

import lombok.Data;

import java.util.List;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class CreateOrderCommand {
    private Long userId;
    private Long userAddress;
    private List<ProductForBuy> products;
}
