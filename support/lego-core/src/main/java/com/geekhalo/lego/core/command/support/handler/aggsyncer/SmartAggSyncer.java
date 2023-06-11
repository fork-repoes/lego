package com.geekhalo.lego.core.command.support.handler.aggsyncer;

import com.geekhalo.lego.core.command.AggRoot;

public interface SmartAggSyncer <AGG extends AggRoot> extends AggSyncer<AGG> {
    boolean apply(Class aggClass);
}
