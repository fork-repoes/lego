package com.geekhalo.tinyurl.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateLimitTimeTinyUrlCommand extends AbstractCreateTinyUrlCommand{
    @NotNull
    private Integer maxCount;

}
