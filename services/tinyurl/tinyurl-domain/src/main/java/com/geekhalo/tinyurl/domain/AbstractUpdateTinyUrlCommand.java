package com.geekhalo.tinyurl.domain;

import com.geekhalo.lego.core.command.CommandForUpdateById;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public abstract class AbstractUpdateTinyUrlCommand implements CommandForUpdateById<Long> {
    @NotNull
    private Long id;

    @Override
    public Long getId() {
        return id;
    }
}
