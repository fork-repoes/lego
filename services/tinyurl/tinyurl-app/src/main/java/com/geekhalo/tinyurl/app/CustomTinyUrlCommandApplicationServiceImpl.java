package com.geekhalo.tinyurl.app;

import com.geekhalo.tinyurl.domain.IncrAccessCountCommand;
import com.geekhalo.tinyurl.domain.TinyUrlCommandRepository;
import com.geekhalo.tinyurl.domain.TinyUrlQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomTinyUrlCommandApplicationServiceImpl implements CustomTinyUrlCommandApplicationService{
    @Autowired
    private TinyUrlCommandRepository tinyUrlCommandRepository;
    @Autowired
    private TinyUrlQueryRepository tinyUrlQueryRepository;

    @Override
    public void incrAccessCount(IncrAccessCountCommand command) {
        this.tinyUrlCommandRepository.incrAccessCount(command.getId(), command.getIncrCount());

        this.tinyUrlQueryRepository.incrAccessCount(command.getId(), command.getIncrCount());
    }
}
