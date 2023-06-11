package com.geekhalo.like.api;

import com.geekhalo.lego.common.validator.ValidateErrorHandler;
import com.geekhalo.lego.common.validator.Verifiable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ActionCommandParam implements Verifiable {
    @NotNull(message = "userId 不能为 null")
    private Long userId;
    @NotNull(message = "targetType 不能为 null")
    private String targetType;
    @NotNull(message = "targetId 不能为 null")
    private Long targetId;

    @Override
    public void validate(ValidateErrorHandler validateErrorHandler) {

    }
}
