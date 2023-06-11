package com.geekhalo.lego.core.command.support.handler.aggsyncer;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.CommandRepository;

public class CommandRepositoryBasedAggSyncer<AGG extends AggRoot<?>>
    implements AggSyncer<AGG> {
    private final CommandRepository<AGG, ?> repository;

    public CommandRepositoryBasedAggSyncer(CommandRepository<AGG, ?> repository) {
        this.repository = repository;
    }

    @Override
    public void sync(AGG agg) {
        this.repository.sync(agg);
    }
}
