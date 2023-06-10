package com.geekhalo.lego.core.command.support.handler.aggloader;

import com.geekhalo.lego.core.command.CommandWithKeyRepository;

import java.util.Optional;
import java.util.function.Function;

public class KeyBasedAggLoader
        extends AbstractSmartLoader
        implements SmartAggLoader{
    private final CommandWithKeyRepository commandRepository;
    private final Function keyFetcher;

    private KeyBasedAggLoader(Class cmdClass, Class aggClass, CommandWithKeyRepository commandRepository, Function keyFetcher) {
        super(cmdClass, aggClass);
        this.commandRepository = commandRepository;
        this.keyFetcher = keyFetcher;
    }

    @Override
    public Optional load(Object o) {
        Object key = this.keyFetcher.apply(o);
        return commandRepository.findByKey(key);
    }


    public static KeyBasedAggLoader apply(Class cmdClass, Class aggClass, CommandWithKeyRepository repository, Function keyFetcher){
        return new KeyBasedAggLoader(cmdClass, aggClass, repository, keyFetcher);
    }
}
