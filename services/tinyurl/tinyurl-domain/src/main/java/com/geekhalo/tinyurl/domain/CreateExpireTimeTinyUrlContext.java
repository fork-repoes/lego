package com.geekhalo.tinyurl.domain;

public class CreateExpireTimeTinyUrlContext
        extends AbstractCreateTinyUrlContext<CreateExpireTimeTinyUrlCommand>{

    public static CreateExpireTimeTinyUrlContext create(CreateExpireTimeTinyUrlCommand command){
        CreateExpireTimeTinyUrlContext context = new CreateExpireTimeTinyUrlContext();
        context.setCommand(command);
        return context;
    }
}
