package com.geekhalo.tinyurl.domain;

import lombok.AccessLevel;
import lombok.Setter;

@Setter(AccessLevel.PROTECTED)
abstract class AbstractCreateTinyUrlContext<CMD extends AbstractCreateTinyUrlCommand> {
    private CMD command;

    public CMD getCommand(){
        return command;
    }

    public Long nextId() {
        return null;
    }
}
