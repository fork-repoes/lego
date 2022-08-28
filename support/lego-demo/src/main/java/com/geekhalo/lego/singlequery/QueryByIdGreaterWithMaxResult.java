package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.annotation.singlequery.FieldGreaterThan;
import com.geekhalo.lego.annotation.singlequery.MaxResult;
import com.geekhalo.lego.annotation.singlequery.MaxResultCheckStrategy;
import lombok.Data;

/**
 * Created by taoli on 2022/8/28.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
@MaxResult(max = 10, strategy = MaxResultCheckStrategy.SET_LIMIT)
public class QueryByIdGreaterWithMaxResult {
    @FieldGreaterThan(value = "id")
    private Long startUserId;
}
