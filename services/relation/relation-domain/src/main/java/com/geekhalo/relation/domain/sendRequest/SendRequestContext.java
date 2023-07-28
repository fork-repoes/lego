package com.geekhalo.relation.domain.sendRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendRequestContext{
    private SendRequestCommand command;

    private SendRequestContext(SendRequestCommand command){
         this.command = command;
    }

    public static SendRequestContext apply (SendRequestCommand command) {
        SendRequestContext context = new SendRequestContext(command);
        return context;
    }
}
