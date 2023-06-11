package com.geekhalo.tinyurl.domain;

import com.geekhalo.lego.core.query.QueryRepository;

import java.util.Optional;

public interface TinyUrlQueryRepository extends QueryRepository<TinyUrl, Long> {
    Optional<TinyUrl> findById(Long id);
}
