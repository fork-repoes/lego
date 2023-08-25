package com.geekhalo.feed.domain.feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedIndex {
    private Long feedId;
    private Long score;
}
