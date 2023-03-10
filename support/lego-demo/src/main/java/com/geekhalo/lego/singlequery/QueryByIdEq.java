package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class QueryByIdEq {
    @FieldEqualTo("id")
    @NotNull
    private Long id;
}
