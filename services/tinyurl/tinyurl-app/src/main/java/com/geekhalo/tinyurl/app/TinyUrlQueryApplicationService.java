package com.geekhalo.tinyurl.app;

import com.geekhalo.tinyurl.domain.TinyUrl;

import java.util.Optional;

public interface TinyUrlQueryApplicationService {

    Optional<TinyUrl> findById(Long id);

    Optional<TinyUrl> findByCode(String code);

    TinyUrl accessById(Long id);

    TinyUrl accessByCode(String code);
}
