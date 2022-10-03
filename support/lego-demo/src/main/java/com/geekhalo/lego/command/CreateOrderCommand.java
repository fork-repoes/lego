package com.geekhalo.lego.command;

import com.geekhalo.lego.core.command.CommandForCreate;
import lombok.Data;

import java.util.List;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class CreateOrderCommand implements CommandForCreate {
    private Long userId;
    private Long userAddress;
    private List<ProductForBuy> products;
}
