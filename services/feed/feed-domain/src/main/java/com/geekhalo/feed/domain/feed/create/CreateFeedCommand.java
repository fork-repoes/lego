package com.geekhalo.feed.domain.feed.create;

import com.geekhalo.feed.domain.feed.FeedData;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.lego.core.command.CommandForCreate;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFeedCommand implements CommandForCreate {
    @NotNull
    private FeedOwner owner;
    @NotNull
    private FeedData data;

}
