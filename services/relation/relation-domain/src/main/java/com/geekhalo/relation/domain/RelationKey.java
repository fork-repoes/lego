package com.geekhalo.relation.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class RelationKey {
    @Column(name = "owner_id", updatable = false, nullable = false)
    private Long owner;
    @Column(name = "recipient_id", updatable = false, nullable = false)
    private Long recipient;
}
