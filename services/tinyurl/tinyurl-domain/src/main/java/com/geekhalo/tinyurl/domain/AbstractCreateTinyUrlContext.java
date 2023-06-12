package com.geekhalo.tinyurl.domain;

import com.geekhalo.lego.core.command.CommandForCreate;
import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public abstract class AbstractCreateTinyUrlContext<CMD extends AbstractCreateTinyUrlCommand> {
    @Autowired
    private NumberGenerator numberGenerator;

    private CMD command;

    public CMD getCommand(){
        return command;
    }

    public Long nextId() {
        return this.numberGenerator.nextNumber();
    }
}
