package com.geekhalo.lego.command;

import com.geekhalo.lego.core.command.CommandForUpdate;
import lombok.Data;

/**
 * Created by taoli on 2022/10/2.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class PaySuccessCommand implements CommandForUpdate<Long> {
    private Long orderId;

    private String chanel;

    private Long price;

    @Override
    public Long id() {
        return orderId;
    }
}
