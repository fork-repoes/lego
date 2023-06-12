package com.geekhalo.tinyurl.domain;

import com.geekhalo.lego.core.command.CommandForCreate;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
abstract class AbstractCreateTinyUrlCommand implements CommandForCreate {
    @NotEmpty
    private String url;
}
