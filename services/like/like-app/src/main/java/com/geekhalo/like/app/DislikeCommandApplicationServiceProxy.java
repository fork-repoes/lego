package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;

@CommandServiceDefinition(
        domainClass = DislikeAction.class,
        repositoryClass = DislikeActionRepository.class)
public interface DislikeCommandApplicationServiceProxy extends DislikeCommandApplicationService{

}
