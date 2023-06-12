package com.geekhalo.tinyurl.app;

import com.geekhalo.lego.core.command.CommandServiceDefinition;
import com.geekhalo.tinyurl.domain.*;

@CommandServiceDefinition(
        repositoryClass = TinyUrlCommandRepository.class,
        domainClass = TinyUrl.class
)
public interface TinyUrlCommandApplicationService {

    TinyUrl createTinyUrl(CreateTinyUrlCommand command);

    TinyUrl createExpireTimeTinyUrl(CreateExpireTimeTinyUrlCommand command);

    TinyUrl createLimitTimeTinyUrl(CreateLimitTimeTinyUrlCommand command);

    void incrAccessCount(IncrAccessCountCommand command);

    void disableTinyUrl(DisableTinyUrlCommand command);

}
