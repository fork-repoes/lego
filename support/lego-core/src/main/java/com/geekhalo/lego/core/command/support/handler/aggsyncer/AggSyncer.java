package com.geekhalo.lego.core.command.support.handler.aggsyncer;

import com.geekhalo.lego.core.command.AggRoot;

public interface AggSyncer<AGG extends AggRoot> {
    void sync(AGG agg);
}
