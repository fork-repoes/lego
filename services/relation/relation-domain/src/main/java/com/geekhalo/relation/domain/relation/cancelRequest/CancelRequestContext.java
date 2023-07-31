package com.geekhalo.relation.domain.relation.cancelRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CancelRequestContext{
    private CancelRequestCommand command;

    private CancelRequestContext(CancelRequestCommand command){
         this.command = command;
    }

    public static CancelRequestContext apply (CancelRequestCommand command) {
        CancelRequestContext context = new CancelRequestContext(command);
        return context;
    }
}
