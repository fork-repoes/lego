package com.geekhalo.lego.query;

import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class GetByUserId {
    @NotNull(message = "userId 不能为 null")
    @FieldEqualTo("userId")
    private Long userId;

    @FieldEqualTo("status")
    private OrderStatus status;
}
