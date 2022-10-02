package com.geekhalo.lego.command;

import lombok.Data;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class PaySuccessCommand {
    private Long orderId;

    private String chanel;

    private Long price;
}
