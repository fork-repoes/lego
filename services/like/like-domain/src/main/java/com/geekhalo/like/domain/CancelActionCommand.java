package com.geekhalo.like.domain;


import com.geekhalo.lego.core.command.CommandForUpdateByKey;

public abstract class CancelActionCommand extends AbstractActionCommand
        implements CommandForUpdateByKey<ActionKey> {

    @Override
    public ActionKey getKey(){
        return ActionKey.apply(getUserId(), getTargetType(), getTargetId());
    }
}
