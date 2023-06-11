package com.geekhalo.like.app;

import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionCommand;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.geekhalo.like.domain.dislike.UndislikeActionCommand;

@CommandServiceDefinition(
        domainClass = DislikeAction.class,
        repositoryClass = DislikeActionRepository.class)
public interface DislikeCommandApplicationServiceProxy extends DislikeCommandApplicationService{

}
