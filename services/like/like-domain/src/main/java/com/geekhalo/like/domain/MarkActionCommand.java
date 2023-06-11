package com.geekhalo.like.domain;

import com.geekhalo.lego.core.command.CommandForSync;

public abstract class MarkActionCommand
        extends AbstractActionCommand
        implements CommandForSync<ActionKey> {

    @Override
    public ActionKey getKey(){
        return ActionKey.apply(getUserId(), getTargetType(), getTargetId());
    }
}
