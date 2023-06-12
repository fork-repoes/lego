package com.geekhalo.tinyurl.domain;


import java.util.Optional;

public interface TinyUrlQueryRepository{
    Optional<TinyUrl> findById(Long id);
}
