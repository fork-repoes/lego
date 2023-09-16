package com.geekhalo.feed.domain.feed.disable;

import com.geekhalo.lego.core.command.CommandForUpdateById;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisableFeedCommand implements CommandForUpdateById<Long> {

    @NotNull
    private Long id;

    @Override
    public Long getId() {
        return this.id;
    }
}
