package com.geekhalo.relation.app.group;

import com.geekhalo.lego.annotation.web.AutoRegisterWebController;
import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.relation.domain.group.RelationGroup;
import com.geekhalo.relation.domain.group.RelationGroupCommandRepository;
import com.geekhalo.relation.domain.group.create.CreateRelationGroupCommand;
import com.geekhalo.relation.domain.group.disable.DisableRelationGroupCommand;
import com.geekhalo.relation.domain.group.enable.EnableRelationGroupCommand;

@CommandServiceDefinition(
        domainClass = RelationGroup.class,
        repositoryClass = RelationGroupCommandRepository.class
)
@AutoRegisterWebController(name = "relationGroup")
public interface RelationGroupCommandApplication {

    RelationGroup create(CreateRelationGroupCommand command);

    void enable(EnableRelationGroupCommand command);

    void disable(DisableRelationGroupCommand command);
}
