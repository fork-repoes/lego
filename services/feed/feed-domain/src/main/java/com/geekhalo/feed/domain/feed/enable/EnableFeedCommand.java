package com.geekhalo.feed.domain.feed.enable;

import com.geekhalo.lego.core.command.CommandForUpdateById;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
