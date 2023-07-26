package com.geekhalo.relation.app;

import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.relation.domain.Relation;
import com.geekhalo.relation.domain.RelationCommandRepository;
import com.geekhalo.relation.domain.applyFriend.ApplyFriendCommand;

@CommandServiceDefinition(
        domainClass = Relation.class,
        repositoryClass = RelationCommandRepository.class
)
public interface RelationCommandApplication {

    void applyFriend(ApplyFriendCommand command);
}
