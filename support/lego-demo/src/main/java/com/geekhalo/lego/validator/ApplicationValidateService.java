package com.geekhalo.lego.validator;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by taoli on 2022/9/10.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Validated
public interface ApplicationValidateService {
    /**
     * 简单参数
     * @param id
     */
    void singleValidate(@NotNull Long id);

    /**
     * 1. 简单参数
     * 2. Bean 中的简单属性
     * @param singleForm
     */
    void singleValidate(@Valid @NotNull SingleForm singleForm);

    void customSingleValidate(@Valid @NotNull CustomSingleForm customSingleForm);

    void validateForm(@NotNull @Valid UserValidateForm userValidateForm);
}
