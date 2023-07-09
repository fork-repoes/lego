package com.geekhalo.tinyurl.domain;

import com.geekhalo.lego.core.command.support.AbstractDomainEvent;

public abstract class AbstractTinyUrlEvent extends AbstractDomainEvent<Long, TinyUrl> {
    public AbstractTinyUrlEvent(TinyUrl source) {
        super(source);
    }
}
