package com.geekhalo.relation.domain.relation.receiveRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReceiveRequestContext{
    private ReceiveRequestCommand command;

    private ReceiveRequestContext(ReceiveRequestCommand command){
         this.command = command;
    }

    public static ReceiveRequestContext apply (ReceiveRequestCommand command) {
        ReceiveRequestContext context = new ReceiveRequestContext(command);
        return context;
    }
}
