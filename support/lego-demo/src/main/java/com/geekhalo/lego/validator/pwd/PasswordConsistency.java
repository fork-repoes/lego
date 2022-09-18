package com.geekhalo.lego.validator.pwd;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by taoli on 2022/9/17.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = PasswordValidator.class
)
public @interface PasswordConsistency {
    String message() default "{javax.validation.constraints.password.consistency.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
