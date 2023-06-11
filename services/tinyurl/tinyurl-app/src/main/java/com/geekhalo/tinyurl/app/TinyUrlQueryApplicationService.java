package com.geekhalo.tinyurl.app;

import com.geekhalo.lego.core.query.QueryServiceDefinition;
import com.geekhalo.tinyurl.domain.TinyUrl;
import com.geekhalo.tinyurl.domain.TinyUrlQueryRepository;

import java.util.Optional;

@QueryServiceDefinition(
        repositoryClass = TinyUrlQueryRepository.class,
        domainClass = TinyUrl.class
)
public interface TinyUrlQueryApplicationService {

    Optional<TinyUrl> findById(Long id);
}
