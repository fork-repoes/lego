package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.annotation.singlequery.FieldLessThan;
import lombok.Data;

/**
 * Created by taoli on 2022/8/23.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class QueryByIdLess {
    @FieldLessThan("id")
    private Long maxId;
}
