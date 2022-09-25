package com.geekhalo.lego.query;

import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;
import com.geekhalo.lego.core.singlequery.Pageable;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class PageByUserId {
    @NotNull(message = "user id 不能为 null")
    @FieldEqualTo("userId")
    private Long userId;

    private Pageable pageable;
}
