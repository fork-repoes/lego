package com.geekhalo.lego.validator.pwd;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by taoli on 2022/9/17.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class PasswordValidator implements ConstraintValidator<PasswordConsistency, Password> {

    @Override
    public boolean isValid(Password password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null){
            return true;
        }
        if (password.getInput1() == null){
            return true;
        }
        if (password.getInput1().equals(password.getInput2())){
            return true;
        }
        return false;
    }
}
