package com.geekhalo.relation.domain.receiveRequest;

import lombok.Data;

@Data
public class ReceiveRequestContext{
    private final ReceiveRequestCommand command;

    private ReceiveRequestContext(ReceiveRequestCommand command){
         this.command = command;
    }

    public static ReceiveRequestContext apply (ReceiveRequestCommand command) {
        ReceiveRequestContext context = new ReceiveRequestContext(command);
        return context;
    }
}
