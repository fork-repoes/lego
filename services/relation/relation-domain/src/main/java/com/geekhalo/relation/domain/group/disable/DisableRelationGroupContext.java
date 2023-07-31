package com.geekhalo.relation.domain.group.disable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DisableRelationGroupContext{
    private DisableRelationGroupCommand command;

    private DisableRelationGroupContext(DisableRelationGroupCommand command){
         this.command = command;
    }

    public static DisableRelationGroupContext apply(DisableRelationGroupCommand command) {
        DisableRelationGroupContext context = new DisableRelationGroupContext(command);
        return context;
    }
}
