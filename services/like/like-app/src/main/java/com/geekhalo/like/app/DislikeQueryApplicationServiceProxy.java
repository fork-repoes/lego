package com.geekhalo.like.app;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;

@QueryServiceDefinition(
        repositoryClass = DislikeActionRepository.class,
        domainClass = DislikeAction.class
)
public interface DislikeQueryApplicationServiceProxy extends DislikeQueryApplicationService{

}
