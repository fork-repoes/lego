package com.geekhalo.relation.domain.group.enable;

import com.geekhalo.lego.core.command.CommandForUpdateById;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EnableRelationGroupCommand implements CommandForUpdateById<Long> {
    @NotNull
    private Long id;

    public EnableRelationGroupCommand(Long id){
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public static EnableRelationGroupCommand apply(Long id){
        EnableRelationGroupCommand command = new EnableRelationGroupCommand(id); 
        return command;
    }
}
