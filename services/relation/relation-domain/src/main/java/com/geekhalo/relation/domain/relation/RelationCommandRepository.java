package com.geekhalo.relation.domain.relation;


import com.geekhalo.lego.core.command.CommandRepository;
import com.geekhalo.lego.core.command.CommandWithKeyRepository;

public interface RelationCommandRepository
        extends CommandRepository<Relation, Long>, CommandWithKeyRepository<Relation, Long, RelationKey> {



}
