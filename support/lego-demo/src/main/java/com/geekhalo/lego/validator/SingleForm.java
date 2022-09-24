package com.geekhalo.lego.validator;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class SingleForm {
    @NotNull(message = "id不能为null")
    private Long id;

    @NotEmpty(message = "name不能为空")
    private String name;
}
