package com.geekhalo.tinyurl.infra.repository;

import com.geekhalo.tinyurl.domain.AbstractTinyUrlEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class QuerySyncer {
    @Autowired
    private TinyUrlQueryRepositoryImpl queryRepository;

    @EventListener
    public void handleEvent(AbstractTinyUrlEvent event){
        this.queryRepository.clean(event.getSource().getId());
    }
}
