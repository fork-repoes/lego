package com.geekhalo.relation.domain.sendRequest;

import lombok.Data;

@Data
public class SendRequestContext{
    private final SendRequestCommand command;

    private SendRequestContext(SendRequestCommand command){
         this.command = command;
    }

    public static SendRequestContext apply (SendRequestCommand command) {
        SendRequestContext context = new SendRequestContext(command);
        return context;
    }
}
