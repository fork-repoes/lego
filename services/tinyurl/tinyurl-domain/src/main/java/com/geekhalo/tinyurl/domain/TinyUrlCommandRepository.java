package com.geekhalo.tinyurl.domain;

import com.geekhalo.lego.core.command.CommandRepository;

public interface TinyUrlCommandRepository extends CommandRepository<TinyUrl, Long>{
    void incrAccessCount(Long id, Integer incrCount);
}
