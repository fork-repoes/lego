package com.geekhalo.relation.domain.group.disable;

import com.geekhalo.lego.core.command.CommandForUpdateById;
import java.lang.Long;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DisableRelationGroupCommand implements CommandForUpdateById<Long> {
    @NotNull
    private Long id;

    public DisableRelationGroupCommand(Long id){
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public static DisableRelationGroupCommand apply(Long id){
        DisableRelationGroupCommand command = new DisableRelationGroupCommand(id); 
        return command;
    }
}
