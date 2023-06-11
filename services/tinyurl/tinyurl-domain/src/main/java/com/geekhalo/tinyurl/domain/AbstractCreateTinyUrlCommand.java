package com.geekhalo.tinyurl.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
abstract class AbstractCreateTinyUrlCommand {
    @NotEmpty
    private String url;
}
