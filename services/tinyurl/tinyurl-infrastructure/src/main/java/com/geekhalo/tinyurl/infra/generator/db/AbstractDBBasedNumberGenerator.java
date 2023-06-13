package com.geekhalo.tinyurl.infra.generator.db;

import com.geekhalo.tinyurl.infra.generator.db.gen.NumberGenRepository;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter(AccessLevel.PROTECTED)
abstract class AbstractDBBasedNumberGenerator {
    @Autowired
    private NumberGenRepository numberGenRepository;
}
