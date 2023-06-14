package com.geekhalo.tinyurl.infra.repository;

import com.geekhalo.tinyurl.domain.TinyUrl;
import com.geekhalo.tinyurl.domain.TinyUrlCommandRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedTinyUrlCommandRepository
        extends TinyUrlCommandRepository,
        JpaRepository<TinyUrl, Long> {

    @Override
    default TinyUrl sync(TinyUrl tinyUrl){
        return save(tinyUrl);
    }
}
