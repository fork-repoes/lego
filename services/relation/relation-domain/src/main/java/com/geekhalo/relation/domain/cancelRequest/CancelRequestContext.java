package com.geekhalo.relation.domain.cancelRequest;

import lombok.Data;

@Data
public class CancelRequestContext{
    private final CancelRequestCommand command;

    private CancelRequestContext(CancelRequestCommand command){
         this.command = command;
    }

    public static CancelRequestContext apply (CancelRequestCommand command) {
        CancelRequestContext context = new CancelRequestContext(command);
        return context;
    }
}
