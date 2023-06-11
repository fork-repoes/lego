package com.geekhalo.tinyurl.domain;

import lombok.Data;

@Data
public class CreateTinyUrlContext extends AbstractCreateTinyUrlContext<CreateTinyUrlCommand>{


    public static CreateTinyUrlContext create(CreateTinyUrlCommand command){
        CreateTinyUrlContext context = new CreateTinyUrlContext();
        context.setCommand(command);
        return context;
    }
}
