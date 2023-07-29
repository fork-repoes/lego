package com.geekhalo.relation.domain.relation.acceptRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AcceptRequestContext{
    private AcceptRequestCommand command;

    private AcceptRequestContext(AcceptRequestCommand command){
         this.command = command;
    }

    public static AcceptRequestContext apply (AcceptRequestCommand command) {
        AcceptRequestContext context = new AcceptRequestContext(command);
        return context;
    }
}
