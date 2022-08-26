package com.geekhalo.lego.singlequery;

import com.geekhalo.lego.annotation.singlequery.FieldEqualTo;
import com.geekhalo.lego.annotation.singlequery.FieldGreaterThan;
import lombok.Data;

import java.util.Date;

/**
 * Created by taoli on 2022/8/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class QueryByStatusAndMobile {

    @FieldGreaterThan("birthAt")
    private Date birthAfter;

    @FieldEqualTo("status")
    private Integer status;

    @FieldEqualTo("mobile")
    private String mobile;
}
