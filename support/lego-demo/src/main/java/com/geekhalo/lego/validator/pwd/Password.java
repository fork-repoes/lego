package com.geekhalo.lego.validator.pwd;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by taoli on 2022/9/17.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class Password {
    @NotEmpty(message = "密码不能为空")
    private String input1;

    @NotEmpty(message = "确认密码不能为空")
    private String input2;
}
