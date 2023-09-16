package com.geekhalo.feed.domain.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FeedOwner {
    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private OwnerType ownerType;

    @Column(updatable = false)
    private Long ownerId;
}
