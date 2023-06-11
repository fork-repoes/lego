package com.geekhalo.tinyurl.domain;

import com.geekhalo.lego.core.command.CommandForUpdateById;

import javax.validation.constraints.NotNull;

public class DisableTinyUrlCommand implements CommandForUpdateById<Long> {
    @NotNull
    private Long id;

    @Override
    public Long getId() {
        return id;
    }
}
