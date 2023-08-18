package com.geekhalo.feed.domain.feed.enable;

import com.geekhalo.lego.core.command.CommandForCreate;
import com.geekhalo.lego.core.command.CommandForUpdateById;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnableFeedCommand implements CommandForUpdateById<Long> {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }
}
