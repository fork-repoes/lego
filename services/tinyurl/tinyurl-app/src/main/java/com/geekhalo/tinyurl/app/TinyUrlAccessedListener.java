package com.geekhalo.tinyurl.app;

import com.geekhalo.tinyurl.domain.IncrAccessCountCommand;
import com.geekhalo.tinyurl.domain.TinyUrlCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TinyUrlAccessedListener {
    @Autowired
    private TinyUrlCommandApplicationService commandApplicationService;

    @EventListener
    public void handleEvent(TinyUrlAccessedEvent event){
        IncrAccessCountCommand incrAccessCountCommand = new IncrAccessCountCommand();
        incrAccessCountCommand.setId(event.getId());
        incrAccessCountCommand.setIncrCount(1);

        this.commandApplicationService.incrAccessCount(incrAccessCountCommand);
    }
}
