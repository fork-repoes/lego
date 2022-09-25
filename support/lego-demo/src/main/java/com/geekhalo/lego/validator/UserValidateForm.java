package com.geekhalo.lego.validator;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.common.validator.Validateable;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Data
public class UserValidateForm implements Validateable {
    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @Override
    public void validate(ValidateErrorHandler validateErrorHandler) {
        if (getName().equals(getPassword())){
            validateErrorHandler.handleError("user", "1", "用户名密码不能相同");
        }
    }
}
