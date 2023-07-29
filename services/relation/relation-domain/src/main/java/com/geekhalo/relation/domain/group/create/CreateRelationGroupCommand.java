package com.geekhalo.relation.domain.group.create;

import com.geekhalo.lego.core.command.CommandForCreate;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRelationGroupCommand implements CommandForCreate {
    @NotNull
    private Long user;

    @NotEmpty
    private String name;

    private String descr;

}
