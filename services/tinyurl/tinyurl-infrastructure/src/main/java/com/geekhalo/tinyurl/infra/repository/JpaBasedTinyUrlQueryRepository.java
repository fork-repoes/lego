package com.geekhalo.tinyurl.infra.repository;

import com.geekhalo.tinyurl.domain.TinyUrl;
import com.geekhalo.tinyurl.domain.TinyUrlQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedTinyUrlQueryRepository
        extends TinyUrlQueryRepository,
        JpaRepository<TinyUrl, Long> {
}
