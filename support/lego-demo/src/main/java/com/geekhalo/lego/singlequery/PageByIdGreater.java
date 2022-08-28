package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.annotation.singlequery.FieldGreaterThan;
import com.geekhalo.lego.core.singlequery.Pageable;
import com.geekhalo.lego.core.singlequery.Sort;
import lombok.Data;

/**
 * Created by taoli on 2022/8/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class PageByIdGreater {
    @FieldGreaterThan("id")
    private Long startId;

    private Pageable pageable;

    private Sort sort;
}
