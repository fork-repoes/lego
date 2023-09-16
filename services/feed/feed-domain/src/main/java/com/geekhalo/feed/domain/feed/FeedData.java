package com.geekhalo.feed.domain.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
public class FeedData {
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private FeedDataType type;

    @Column(updatable = false)
    private String content;
}
