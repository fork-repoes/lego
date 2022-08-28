package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.annotation.singlequery.FieldIn;
import lombok.Data;

import java.util.List;

/**
 * Created by taoli on 2022/8/22.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class QueryByIdIn {
    @FieldIn(value = "id", fieldType = Long.class)
    private List<Long> ids;
}
