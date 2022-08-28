package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.annotation.singlequery.FieldNotIn;
import lombok.Data;

import java.util.List;

/**
 * Created by taoli on 2022/8/23.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class QueryByIdNotIn {
    @FieldNotIn(value = "id")
    private List<Long> ids;
}
