package com.geekhalo.lego.core.command.support.handler.aggsyncer;

import com.geekhalo.lego.core.command.AggRoot;
import com.geekhalo.lego.core.command.CommandRepository;
import com.google.common.base.Preconditions;

public class SmartCommandRepositoryBasedAggSyncer<AGG extends AggRoot<?>>
    implements SmartAggSyncer<AGG> {
    private final CommandRepository<AGG, ?> repository;
    private final Class aggClass;

    public SmartCommandRepositoryBasedAggSyncer(CommandRepository<AGG, ?> repository,
                                                Class aggClass) {
        Preconditions.checkArgument(repository != null);
        Preconditions.checkArgument(aggClass != null);

        this.repository = repository;
        this.aggClass = aggClass;
    }

    @Override
    public void sync(AGG agg) {
        this.repository.sync(agg);
    }

    @Override
    public boolean apply(Class aggClass) {
        return this.aggClass.equals(aggClass);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.aggClass)
                .append("\t")
                .append(this.repository.getClass())
                .append(".sync(").append(this.aggClass).append(")");
        return stringBuilder.toString();
    }
}
