package com.geekhalo.lego.core.command.support;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.DomainEvent;
import com.google.common.base.Preconditions;

import java.util.Date;

public abstract class AbstractDomainEvent<ID, AGG extends AggRoot<ID>> implements DomainEvent<ID, AGG> {
    private final AGG source;
    private final Date createTime;

    public AbstractDomainEvent(AGG source){
        Preconditions.checkArgument(source != null);
        this.source = source;
        this.createTime = new Date();
    }

    @Override
    public Date createAt() {
        return this.createTime;
    }

    @Override
    public AGG source() {
        return this.source;
    }
}
