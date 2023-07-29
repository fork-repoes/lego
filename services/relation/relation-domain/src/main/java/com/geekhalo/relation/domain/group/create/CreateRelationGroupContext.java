package com.geekhalo.relation.domain.group.create;

import com.geekhalo.relation.domain.group.create.CreateRelationGroupCommand; 
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateRelationGroupContext{
    private CreateRelationGroupCommand command;

    private CreateRelationGroupContext(CreateRelationGroupCommand command){
         this.command = command;
    }

    public static CreateRelationGroupContext apply(CreateRelationGroupCommand command) {
        CreateRelationGroupContext context = new CreateRelationGroupContext(command);
        return context;
    }
}
